package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.repositories.PollRepository;
import com.pik.hotpoll.services.interfaces.PollService;
import com.pik.hotpoll.services.interfaces.QuestionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Map;

@Service
public class DefaultPollService implements PollService {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultPollService.class);
    private final PollRepository pollRepository;
    private QuestionService questionService;

    @Autowired
    public DefaultPollService(final PollRepository pollRepository,
                              DefaultQuestionService questionService){
        this.pollRepository = pollRepository;
        this.questionService = questionService;
    }

    @Override
    @Transactional
    public Poll find(Integer id) throws EntityNotFoundException {
        return findPollChecked(id);
    }

    @Override
    @Transactional
    public Poll create(Poll poll) throws ConstraintsViolationException {
        Poll pollNew;
        try {
            pollNew = pollRepository.save(poll);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return pollNew;
    }

    @Override
    @Transactional
    public void delete(Integer id) throws EntityNotFoundException, ConstraintsViolationException {
        Poll poll = findPollChecked(id);
        pollRepository.delete(poll);
    }

    @Override
    @Transactional
    public void addQuestion(Integer questionId, Integer pollId) throws ConstraintsViolationException, EntityNotFoundException {
        Poll poll = find(pollId);
        Question question = questionService.find(questionId);
        if (poll.getQuestions().size() > Poll.MAX_QUESTION_AMOUNT) {
            throw new ConstraintsViolationException("Too many questions");
        }
        pollAddQuestion(poll, question);
        create(poll);
    }

    @Override
    @Transactional
    public void deleteQuestion(Integer questionId, Integer pollId) throws ConstraintsViolationException, EntityNotFoundException {
        Poll poll = find(pollId);
        Question question = questionService.find(questionId);
        if (poll.getQuestions() == null) {
            throw new ConstraintsViolationException("No questions");
        }
        Map<Integer, Question> questions = poll.getQuestions();
        if (!questions.containsKey(questionId)) {
            throw new ConstraintsViolationException("This question does not belong to the poll");
        }
        pollRemoveQuestion(poll, question);
        create(poll);
    }

    @Override
    @Transactional
    public Iterable<Poll> findAll() {
        return pollRepository.findAll();
    }

    private Poll findPollChecked(Integer id) throws EntityNotFoundException {
        Poll poll = pollRepository.findPollById(id);
        if (poll == null) {
            throw new EntityNotFoundException("Could not find car with this licensePlate");
        }
        return poll;
    }

    private void pollAddQuestion(Poll poll, Question question){
        Map<Integer, Question> questions = poll.getQuestions();
        questions.put(question.getId(),question);
        poll.setQuestions(questions);
    }

    private void pollRemoveQuestion(Poll poll, Question question){
        Map<Integer, Question> questions = poll.getQuestions();
        questions.remove(question.getId(),question);
        poll.setQuestions(questions);
    }
}

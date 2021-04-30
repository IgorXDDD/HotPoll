package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Answer;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.repositories.QuestionRepository;
import com.pik.hotpoll.services.interfaces.AnswerService;
import com.pik.hotpoll.services.interfaces.QuestionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Map;

@Service
public class DefaultQuestionService implements QuestionService {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultPollService.class);
    private final QuestionRepository questionRepository;
    private AnswerService answerService;

    @Autowired
    public DefaultQuestionService(final QuestionRepository questionRepository,
                                  DefaultAnswerService answerService){
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    @Override
    @Transactional
    public Question find(Integer questionId) throws EntityNotFoundException{
        return findQuestionChecked(questionId);
    }

    @Override
    @Transactional
    public Question create(Question question) throws ConstraintsViolationException {
        Question questionNew;
        try {
            questionNew = questionRepository.save(question);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return questionNew;
    }

    @Override
    @Transactional
    public void delete(Integer id) throws EntityNotFoundException, ConstraintsViolationException {
        Question question = findQuestionChecked(id);
        questionRepository.delete(question);
    }

    @Override
    @Transactional
    public void addAnswer(Integer answerId, Integer questionId) throws ConstraintsViolationException, EntityNotFoundException {
        Question question = find(questionId);
        Answer answer = answerService.find(answerId);
        if (question.getAnswers().size() > Question.MAX_ANSWER_AMOUNT) {
            throw new ConstraintsViolationException("Too many questions");
        }
        questionAddAnswer(question, answer);
        create(question);
    }

    @Override
    @Transactional
    public void deleteAnswer(Integer answerId, Integer questionId) throws ConstraintsViolationException, EntityNotFoundException {
        Question question = find(questionId);
        Answer answer = answerService.find(answerId);
        if (question.getAnswers() == null) {
            throw new ConstraintsViolationException("No questions");
        }
        Map<Integer, Answer> answers = question.getAnswers();
        if (!answers.containsKey(questionId)) {
            throw new ConstraintsViolationException("This question does not belong to the poll");
        }
        questionRemoveAnswer(question, answer);
        create(question);
    }

    @Override
    @Transactional
    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    private Question findQuestionChecked(Integer id) throws EntityNotFoundException {
        Question question = questionRepository.findQuestionById(id);
        if (question == null) {
            throw new EntityNotFoundException("Could not find car with this licensePlate");
        }
        return question;
    }

    private void questionAddAnswer(Question question, Answer answer){
        Map<Integer, Answer> answers = question.getAnswers();
        answers.put(answer.getId(),answer);
        question.setAnswers(answers);
    }

    private void questionRemoveAnswer(Question question, Answer answer){
        Map<Integer, Answer> answers = question.getAnswers();
        answers.remove(answer.getId(),answer);
        question.setAnswers(answers);
    }
}

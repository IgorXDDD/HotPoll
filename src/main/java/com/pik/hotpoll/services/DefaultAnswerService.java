package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Answer;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.interfaces.AnswerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class DefaultAnswerService implements AnswerService {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultPollService.class);
    private final AnswerRepository answerRepository;

    @Autowired
    public DefaultAnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Answer find(Integer answerId) throws EntityNotFoundException {
        return findAnswerChecked(answerId);
    }

    @Override
    public Answer create(Answer answer) throws ConstraintsViolationException {
        Answer answerNew;
        try {
            answerNew = answerRepository.save(answer);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return answerNew;
    }

    @Override
    public void delete(Integer id) throws EntityNotFoundException, ConstraintsViolationException {
        Answer answer = findAnswerChecked(id);
        answerRepository.delete(answer);
    }

    @Override
    public Iterable<Answer> findAll() {
        return answerRepository.findAll();
    }

    private Answer findAnswerChecked(Integer id) throws EntityNotFoundException {
        Answer answer = answerRepository.findAnswerById(id);
        if (answer == null) {
            throw new EntityNotFoundException("Could not find car with this licensePlate");
        }
        return answer;
    }
}

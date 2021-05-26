package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Vote;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.repositories.PollRepository;
import com.pik.hotpoll.services.interfaces.PollService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DefaultPollService implements PollService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultPollService.class);
    private final PollRepository pollRepository;


    @Autowired
    public DefaultPollService(final PollRepository pollRepository){
        this.pollRepository = pollRepository;
        pollRepository.deleteAll();
    }

    public Poll find(String id) throws EntityNotFoundException {
        return findPollChecked(id);
    }


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

    public void delete(String id) throws EntityNotFoundException, ConstraintsViolationException {
        Poll poll = findPollChecked(id);
        pollRepository.delete(poll);
    }


    public Iterable<Poll> findAll() {
        return pollRepository.findAll();
    }

    private Poll findPollChecked(String id) throws EntityNotFoundException {
        Optional<Poll> poll = pollRepository.findById(id);
        if (!poll.isPresent()) {
            throw new EntityNotFoundException("poll not found");
        }else{
            return poll.get();
        }

    }

    public Poll addVote(Vote vote) throws EntityNotFoundException{
        Optional<Poll> p = pollRepository.findById(vote.getPollID());
        if (!p.isPresent()) {
            throw new EntityNotFoundException("poll not found");
        }else{
            Poll poll = p.get();
            try {
                poll.getQuestions().get(vote.getQuestionID()).getAnswers().get(vote.getAnswerID()).addVote();
                pollRepository.save(poll);
            }catch (Exception e){
                throw new EntityNotFoundException("wrong question/answerID");
            }
            return poll;
        }
    }

}

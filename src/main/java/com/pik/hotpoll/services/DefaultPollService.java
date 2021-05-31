package com.pik.hotpoll.services;

import com.google.common.collect.Lists;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Vote;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.repositories.PollRepository;
import com.pik.hotpoll.services.interfaces.PollService;
import com.querydsl.core.types.Predicate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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


    public Iterable<Poll> findAll(int page, int size, Boolean newest) {
        Pageable paging;
        if(newest){
            paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
            return pollRepository.findAll(paging).getContent();
        }
        paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timesFilled"));
        return pollRepository.findAll(paging).getContent();
    }

    public Iterable<Poll> findByTags(List<String> tags, int page, int size, Boolean newest){
        Pageable paging;
        if(newest){
            paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
            return pollRepository.findByTags(tags, paging);
        }
        paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timesFilled"));
        return pollRepository.findByTags(tags, paging);
    }

    public Iterable<Poll> findByName(String name, int page, int size, Boolean newest){
        Pageable paging;
        if(newest){
            paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
            return pollRepository.findByTitleLike(name, paging);
        }
        paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timesFilled"));
        return pollRepository.findByTitleLike(name, paging);
    }

    public List<Poll> search(Predicate p) {
        return Lists.newArrayList(pollRepository.findAll(p));
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
                poll.addVote(vote);
                pollRepository.save(poll);
            }catch (Exception e){
                throw new EntityNotFoundException("wrong question/answerID");
            }
            return poll;
        }
    }

    public Long getPollsNum(){
        return pollRepository.count();
    }


}

package com.pik.hotpoll.services;

import com.google.common.collect.Lists;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.Vote;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.repositories.PollRepository;
import com.pik.hotpoll.repositories.UserRepository;
import com.pik.hotpoll.services.interfaces.PollService;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultPollService implements PollService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultPollService.class);

    private final PollRepository pollRepository;
    private final UserRepository userRepository;


    @Autowired
    public DefaultPollService(final PollRepository pollRepository,
                              final UserRepository userRepository){
        this.pollRepository = pollRepository;
        pollRepository.deleteAll();
        this.userRepository = userRepository;
    }

    public Poll find(String id) throws EntityNotFoundException {
        return findPollChecked(id);
    }


    public Poll create(Poll poll, User user) throws ConstraintsViolationException {
        Poll pollNew;

        Poll toSave = Poll.builder().id(poll.getId()).date(poll.getDate()).tags(poll.getTags())
                .questions(poll.getQuestions()).timesFilled(poll.getTimesFilled()).title(poll.getTitle()).author(user).build();
        try {
            pollNew = pollRepository.save(toSave);
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
            return pollRepository.findByTitleLikeIgnoreCase(name, paging);
        }
        paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timesFilled"));
        return pollRepository.findByTitleLikeIgnoreCase(name, paging);
    }

    public List<Poll> search(Predicate p) {
        return Lists.newArrayList(pollRepository.findAll(p));
    }

    @Override
    public Iterable<Poll> findByUsername(String username, int page, int size, Boolean newest) {
        List<User> userList = userRepository.findByNickname(username);

        if(userList.isEmpty()){
            return new ArrayList<Poll>();
        }
        User user = userList.get(0);

        Pageable paging;
        if(newest){
            paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
            return pollRepository.findByAuthor(user, paging);
        }
        paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timesFilled"));
        return pollRepository.findByAuthor(user, paging);
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

}

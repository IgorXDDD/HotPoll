package com.pik.hotpoll.controllers;

import com.google.inject.internal.util.Lists;
import com.pik.hotpoll.controllers.mappers.PollMapper;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.PollDTO;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.exceptions.NoAuthorizationException;
import com.pik.hotpoll.services.interfaces.AuthorizationService;
import com.pik.hotpoll.services.DefaultPollService;
import com.pik.hotpoll.services.interfaces.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/poll")
public class PollController {

    private PollService pollService;
    private AuthorizationService authorizationService;

    @Autowired
    public void PollController(DefaultPollService pollService, AuthorizationService authorizationService){

        this.pollService = pollService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("")
    public List<PollDTO> getPolls() throws ConstraintsViolationException, EntityNotFoundException {

        List<Poll> polls = new ArrayList<>(Lists.newArrayList(pollService.findAll()));

        return PollMapper.makePollDTOList(polls);
    }

    @GetMapping("/{pollID}")
    public PollDTO getPoll(@Validated @PathVariable Integer pollID) throws EntityNotFoundException{
        return PollMapper.makePollDTO(pollService.find(pollID));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PollDTO postPoll(@Validated @RequestBody PollDTO pollDTO, @AuthenticationPrincipal User user)
            throws ConstraintsViolationException, NoAuthorizationException {
        if(!authorizationService.canCreatePoll(user)){
            throw new NoAuthorizationException();
        }
        Poll poll = PollMapper.makePoll(pollDTO);
        return PollMapper.makePollDTO(pollService.create(poll));
    }
}

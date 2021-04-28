package com.pik.hotpoll.controllers;

import com.google.inject.internal.util.Lists;
import com.pik.hotpoll.controllers.mappers.PollMapper;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.PollDTO;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.DefaultPollService;
import com.pik.hotpoll.services.PollService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/poll")
public class PollController {

    private PollService pollService;

    @Autowired
    public void PollController(DefaultPollService pollService){
        this.pollService = pollService;
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
    public PollDTO postPoll(@Validated @RequestBody PollDTO pollDTO)
            throws ConstraintsViolationException {
        Poll poll = PollMapper.makePoll(pollDTO);
        return PollMapper.makePollDTO(pollService.create(poll));
    }
}

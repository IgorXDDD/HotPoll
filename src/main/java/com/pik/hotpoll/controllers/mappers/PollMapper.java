package com.pik.hotpoll.controllers.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.PollDTO;
import com.pik.hotpoll.domain.Question;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PollMapper {
    private ObjectMapper objectMapper;
    PollMapper() {
        objectMapper = new ObjectMapper();
    }


//    public Poll makePoll(PollDTO pollDTO){
//        return Poll(pollDTO.getTitle(), pollDTO.getAuthorId());
//    }
//
//    public List<PollDTO> makePollDTOList(Collection<Poll> polls){
//        return polls.stream().map(PollMapper::makePollDTO).collect(Collectors.toList());
//    }
}

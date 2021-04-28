package com.pik.hotpoll.controllers.mappers;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.PollDTO;
import com.pik.hotpoll.domain.Question;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PollMapper {
    public static PollDTO makePollDTO(Poll poll){
        PollDTO.PollDTOBuilder pollDTOBuilder = new PollDTO.PollDTOBuilder()
                .setAuthorId(poll.getAuthorId())
                .setDate(poll.getDate())
                .setId(poll.getId())
                .setNumOfQuestions(poll.getNumOfQuestions())
                .setTag1(poll.getTag1())
                .setTag2(poll.getTag2())
                .setTag3(poll.getTag3())
                .setTimesCompleted(poll.getTimesCompleted())
                .setTitle(poll.getTitle());

        if(poll.getQuestions().size() > Poll.MAX_QUESTION_AMOUNT){
            pollDTOBuilder.setQuestions(poll.getQuestions());
        }
        return pollDTOBuilder.build();
    }

    public static Poll makePoll(PollDTO pollDTO){
        return new Poll(pollDTO.getTitle(), pollDTO.getAuthorId());
    }

    public static List<PollDTO> makePollDTOList(Collection<Poll> polls){
        return polls.stream().map(PollMapper::makePollDTO).collect(Collectors.toList());
    }
}

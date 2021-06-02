package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.Vote;

import java.security.Principal;
import java.util.List;

public interface StatisticsService {
    boolean userVoted(Vote vote, Principal principal);

    boolean hasUserVotedOnPoll(String pollId, Principal principal);

    Long getPollsNum();

    Long getCountByTags( List<String> tags);

    Long getCountByName(String title);
}

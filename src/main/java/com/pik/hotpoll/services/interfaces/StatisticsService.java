package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.Vote;

public interface StatisticsService {
    boolean userVoted(Vote vote, String username);

    boolean hasUserVotedOnPoll(String pollId, String name);
}

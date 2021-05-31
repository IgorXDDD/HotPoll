package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.Vote;

import java.util.List;

public interface StatisticsService {
    boolean userVoted(Vote vote, String username);

    boolean hasUserVotedOnPoll(String pollId, String name);

    Long getPollsNum();

    Long getCountByTags( List<String> tags);

    Long getCountByName(String title);
}

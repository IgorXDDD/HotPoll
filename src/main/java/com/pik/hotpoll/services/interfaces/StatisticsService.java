package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.Vote;

import java.security.Principal;
import java.util.List;

public interface StatisticsService {
    boolean userVoted(Vote vote, User user);

    boolean hasUserVotedOnPoll(String pollId, User user);

    Long getPollsNum();

    Long getCountByTags( List<String> tags);

    Long getCountByName(String title);
}

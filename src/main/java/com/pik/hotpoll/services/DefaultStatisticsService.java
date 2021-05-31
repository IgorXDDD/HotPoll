package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.UserVote;
import com.pik.hotpoll.domain.Vote;
import com.pik.hotpoll.repositories.PollRepository;
import com.pik.hotpoll.repositories.UserRepository;
import com.pik.hotpoll.repositories.UserVoteRepository;

import com.pik.hotpoll.services.interfaces.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultStatisticsService implements StatisticsService {

    private final UserRepository userRepository;
    private final PollRepository pollRepository;
    private final UserVoteRepository userVoteRepository;

    @Autowired
    public DefaultStatisticsService(UserVoteRepository userVoteRepository,
                                    UserRepository userRepository,
                                    PollRepository pollRepository){
        this.userVoteRepository = userVoteRepository;
        this.userRepository = userRepository;
        this.pollRepository = pollRepository;
    }

    @Override
    public boolean userVoted(Vote vote, String username) {
        List<User> userList = userRepository.findByNickname(username);
        if(userList.isEmpty()){
            return false;
        }

        Optional<Poll> poll = pollRepository.findById(vote.getPollID());
        if(!poll.isPresent()){
            return false;
        }
        userVoteRepository.save(UserVote.builder().poll(poll.get()).user(userList.get(0)).build());
        return true;
    }

    @Override
    public boolean hasUserVotedOnPoll(String pollId, String name) {
        List<User> userList = userRepository.findByNickname(name);
        if(userList.isEmpty()){
            return false;
        }
        List<UserVote> userVotes = userVoteRepository.findByUser(userList.get(0));
        for (UserVote vote:
             userVotes) {
            if(vote.getPoll().getId() == pollId){
                return true;
            }
        }
        return false;
    }
}

package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.UserVote;
import com.pik.hotpoll.domain.Vote;
import com.pik.hotpoll.repositories.PollRepository;
import com.pik.hotpoll.repositories.UserRepository;
import com.pik.hotpoll.repositories.UserVoteRepository;

import com.pik.hotpoll.services.interfaces.StatisticsService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    public boolean userVoted(Vote vote, Principal principal) {
        List<User> userList = userRepository.findByNickname(principal.getName());
        User user;

        if(userList.isEmpty()){
            String username = StringUtils.substringBetween(principal.toString(), "name=", ",");
            userList = userRepository.findByNickname(username);


            if(userList.isEmpty()){
                String email = StringUtils.substringBetween(principal.toString(), "email=", "}");
                String password = StringUtils.substringBetween(principal.toString(), "sub=", ",");


                user = User.builder().nickname(username).email(email).password(password).build();
                userRepository.save(user);
            }else {
                user = userList.get(0);
            }
        }else {
            user = userList.get(0);
        }

        Optional<Poll> poll = pollRepository.findById(vote.getPollID());
        if(!poll.isPresent()){
            return false;
        }
        userVoteRepository.save(UserVote.builder().poll(poll.get()).user(user).build());
        return true;
    }

    @Override
    public boolean hasUserVotedOnPoll(String pollId, Principal principal) {
        List<User> userList = userRepository.findByNickname(principal.getName());

        if(userList.isEmpty()) {
            String username = StringUtils.substringBetween(principal.toString(), "name=", ",");
            userList = userRepository.findByNickname(username);

        }

        if(userList.isEmpty()){
            return false;
        }

            List<UserVote> userVotes = userVoteRepository.findByUser(userList.get(0));
        for (UserVote vote:
             userVotes) {
            if(vote.getPoll() != null) {
                if (vote.getPoll().getId().equals(pollId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Long getPollsNum(){
        return pollRepository.count();
    }

    @Override
    public Long getCountByTags( List<String> tags) {
        return pollRepository.countByTags(tags);
    }

    @Override
    public Long getCountByName(String title) {
        return pollRepository.countByTitleLike(title);
    }


}

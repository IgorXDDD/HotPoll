package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.User;
import org.springframework.stereotype.Service;


public interface AuthorizationService {
//    public static boolean canAddPoll(User user) {
//        return user.getId().equals(article.getUserId());
//    }
//
//    public static boolean canAnswerPoll(User user) {
//        return user.getId().equals(article.getUserId()) || user.getId().equals(comment.getUserId());
//    }

    boolean canCreatePoll(User user);
}

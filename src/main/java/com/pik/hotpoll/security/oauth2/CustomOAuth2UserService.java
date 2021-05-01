package com.pik.hotpoll.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.hotpoll.domain.AuthProvider;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.UserDTO;
import com.pik.hotpoll.exceptions.OAuth2AuthenticationProcessingException;
import com.pik.hotpoll.repositories.UserRepository;
import com.pik.hotpoll.security.UserPrincipal;
import com.pik.hotpoll.security.oauth2.user.OAuth2UserInfo;
import com.pik.hotpoll.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<Object> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO user;
        if(userOptional.isPresent()) {
            user = objectMapper.convertValue(userOptional.get(), UserDTO.class);
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private UserDTO registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        UserDTO user = new UserDTO();

        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        ObjectMapper objectMapper = new ObjectMapper();
        User saveUser = objectMapper.convertValue(user, User.class);
        return objectMapper.convertValue(userRepository.save(saveUser), UserDTO.class) ;
    }

    private UserDTO updateExistingUser(UserDTO existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setUsername(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        ObjectMapper objectMapper = new ObjectMapper();
        User saveUser = objectMapper.convertValue(existingUser, User.class);
        return objectMapper.convertValue(userRepository.save(saveUser), UserDTO.class);
    }

}
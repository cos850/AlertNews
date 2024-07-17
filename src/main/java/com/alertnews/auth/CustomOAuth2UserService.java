package com.alertnews.auth;

import com.alertnews.user.User;
import com.alertnews.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Autowired
    public CustomOAuth2UserService(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> OAuthUserAttributes = oAuth2User.getAttributes();
        Map<String, String> profiles = (Map<String, String>) OAuthUserAttributes.get("properties");
        long id = Long.parseLong(oAuth2User.getName()); // getName == attributes.get("id") and Never null

        User user = new User(id, profiles.get("nickname"), null, null);
        userService.saveUser(user);

        return oAuth2User;
    }
}

package ua.dp.skillsup.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.dp.skillsup.spring.homework.Post;

import java.util.Arrays;
import java.util.List;

@Component()
public class FacebookApi implements ServiceApi{
    @Autowired
    @Value("${facebook_apiKey}")
    private String apiKey;

    @Autowired
    @Value("${facebook_secret}")
    private String secret;

    @Override
    public List<Post> getPosts() {
        System.out.println("Reading posts from Facebook with following credentials: " + apiKey + "/" + secret);
        //some fancy implementation here
        return Arrays.asList(
                new Post("I’m Living Quite the Life", "http://facebook.com/bragging_photo.jpg"),
                new Post("je suis git", ""),
                new Post("Dogs are awesome", "http://facebook.com/goodboy.jpg")
        );
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}

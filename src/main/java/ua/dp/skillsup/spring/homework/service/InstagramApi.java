package ua.dp.skillsup.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.dp.skillsup.spring.homework.Post;

import java.util.Arrays;
import java.util.List;

@Component()
public class InstagramApi implements ServiceApi{
    @Autowired
    @Value("${instagram_appName}")
    private String appName;

    @Autowired
    @Value("${instagram_appSecret}")
    private String appSecret;

    @Override
    public List<Post> getPosts() {
        System.out.println("Reading posts from Instagram with following credentials: " + appName + "/" + appSecret);
        //some fancy implementation here
        return Arrays.asList(
                new Post("What a beautiful sunset! #sunset #evening #photo #potato #iphone #camera", "http://instagram.com/sunset.jpg"),
                new Post("Look at my dog!", "http://instagram.com/verygoodboy.jpg"),
                new Post("I'm such a hot chick! #selfie #kfc", "http://instagram.com/selfie.jpg")
        );
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}

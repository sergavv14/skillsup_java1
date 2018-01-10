package ua.dp.skillsup.spring.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.dp.skillsup.spring.homework.service.FacebookApi;
import ua.dp.skillsup.spring.homework.service.InstagramApi;
import ua.dp.skillsup.spring.homework.service.TwitterApi;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SocialServiceApp {

    @Autowired
    List<PostProvider> providers;

    @Bean
    @Autowired
    public PostProvider postProvider_facebook(@Value("${globalKeyWord}") String keyWord, FacebookApi facebookApi, PostFilter postFilter) {
        PostProvider postProvider = new PostProvider();
        postProvider.setServiceApi(facebookApi);
        postProvider.setPostFilter(postFilter);
        postProvider.setKeyWord(keyWord);
        return  postProvider;
    }

    @Bean
    @Autowired
    public PostProvider postProvider_instagram(@Value("${globalKeyWord}") String keyWord, InstagramApi instagramApi, PostFilter postFilter) {
        PostProvider postProvider = new PostProvider();
        postProvider.setServiceApi(instagramApi);
        postProvider.setPostFilter(postFilter);
        postProvider.setKeyWord(keyWord);
        return  postProvider;
    }

    @Bean
    @Autowired
    public PostProvider postProvider_twitter(@Value("${globalKeyWord}") String keyWord, TwitterApi twitterApi, PostFilter postFilter) {
        PostProvider postProvider = new PostProvider();
        postProvider.setServiceApi(twitterApi);
        postProvider.setPostFilter(postFilter);
        postProvider.setKeyWord(keyWord);
        return  postProvider;
    }


    public static void main(String[] args) {
        System.out.println("Start");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("socialApplicationContext.xml");
        System.out.println("Context initialised");
        SocialServiceApp app = (SocialServiceApp)context.getBean("socialServiceApp");
        app.run();
    }

    public void run(){
        List<Post> posts = new ArrayList<>();
        for (PostProvider provider : providers) {
            posts.addAll(provider.getPosts());
        }
        System.out.println("Filtered posts:");
        for (Post post : posts) {
            System.out.println(post);
        }
    }

    public List<PostProvider> getProviders() {
        return providers;
    }

    public void setProviders(List<PostProvider> providers) {
        this.providers = providers;
    }
}

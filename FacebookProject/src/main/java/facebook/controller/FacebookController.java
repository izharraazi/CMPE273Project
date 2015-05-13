package facebook.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.restfb.FacebookClient;
import com.restfb.types.User;

import facebook.Messaging.FacebookEmailService;
import facebook.model.UserHashTag;
//import facebook.model.UserComment;
import facebook.model.UserHomeFeed;
//import facebook.repository.UserCommentRepo;
import facebook.repository.UserHomeFeedRepo;
import facebook.service.FacebookConnection;
import facebook.service.HomeFeed;
import facebook.serviceImpl.FacebookConnectionService;
import facebook.serviceImpl.HashTagService;
import facebook.serviceImpl.HomeFeedService;
@RestController
public class FacebookController {
		@Autowired
	    UserHomeFeedRepo homeFeedRepo;
	    
		private static String redirectURL = "http://54.67.29.91:8080/facebookApp";
		
		private static FacebookClient fbClient;
		private static User user;
		private FacebookConnection connection = new FacebookConnectionService();
		private HomeFeed homeFeedService = new HomeFeedService();
		private static final String KAFKA_ZOOKEEPER_URL  = "localhost:9092";
	
        @RequestMapping(name = "facebookApp", method = RequestMethod.GET)
        public ModelAndView facebookLogin(@RequestParam(value="code") String profileCode, ModelAndView model) throws IOException{
        	try{
            fbClient = connection.doFacebookLogin(profileCode, redirectURL);
            user = connection.getCurrentUser(fbClient);
            List<UserHomeFeed> homeFeeds = homeFeedService.fetchPostsByType(user.getId(), "photo");
            System.out.println("Inside here!!"+homeFeeds);
            model.setViewName("userHomeFeed.jsp");
            model.addObject("user",user);
            System.out.println("inside controller pics "+homeFeeds);
            model.addObject("feeds", homeFeeds);
            return model;
        	}catch(IOException IOException){
        		IOException.printStackTrace();
        		return null;
        	}catch(Exception exception){
        		exception.printStackTrace();
        		return null;
        	}
        }
        
        @RequestMapping(value = "/photo", method = RequestMethod.GET)
        public ModelAndView photo(ModelAndView model) throws IOException{
        	try{
            List<UserHomeFeed> homeFeeds = homeFeedService.fetchPostsByType(user.getId(), "photo");
            System.out.println("Inside here!!"+homeFeeds);
            model.setViewName("userHomeFeed.jsp");
            model.addObject("user",user);
            model.addObject("feeds", homeFeeds);
            return model;
        	}catch(Exception exception){
        		exception.printStackTrace();
        		return null;
        	}
        }
        
        @RequestMapping(value = "/video", method = RequestMethod.GET)
        public ModelAndView video(ModelAndView model) throws IOException{
        	try{
            List<UserHomeFeed> homeFeeds = homeFeedService.fetchPostsByType(user.getId(), "video");
            System.out.println("Inside here!!"+homeFeeds);
            model.setViewName("video.jsp");
            model.addObject("user",user);
            model.addObject("feeds", homeFeeds);
            return model;
        	}catch(Exception exception){
        		exception.printStackTrace();
        		return null;
        	}
        }
        @RequestMapping(value = "/status", method = RequestMethod.GET)
        public ModelAndView status(ModelAndView model) throws IOException{
        	try{
            List<UserHomeFeed> homeFeeds = homeFeedService.fetchPostsByType(user.getId(), "status");
            System.out.println("Inside here!!"+homeFeeds);
            model.setViewName("status.jsp");
            model.addObject("user",user);
            model.addObject("feeds", homeFeeds);
            return model;
        	}catch(Exception exception){
        		exception.printStackTrace();
        		return null;
        	}
        }
        @RequestMapping(value = "/event", method = RequestMethod.GET)
        public ModelAndView event(ModelAndView model) throws IOException{
        	try{
            List<UserHomeFeed> homeFeeds = homeFeedService.fetchPostsByType(user.getId(), "event");
            System.out.println("Inside here!!"+homeFeeds);
            model.setViewName("event.jsp");
            model.addObject("user",user);
            model.addObject("feeds", homeFeeds);
            return model;
        	}catch(Exception exception){
        		exception.printStackTrace();
        		return null;
        	}
        }

	@RequestMapping(value = "/hashtag", method = RequestMethod.GET)
        public ModelAndView hashTags(ModelAndView model) throws IOException{
        	try{
        		HashTagService ht = new HashTagService();
        		List<String> hashtag = null;
                hashtag = ht.findTrendingHashTags(fbClient, user.getId());
                
                System.out.println("Inside hastag!!"+hashtag.size());
                model.setViewName("hashtag.jsp");
                model.addObject("user",user);
                model.addObject("hash", hashtag);
            return model;
        	}catch(Exception exception){
        		exception.printStackTrace();
        		return null;
        	}
        }
		
	@Scheduled(fixedRate = 10000)
    	public void sendEvent(){
    		if(fbClient!=null && user!=null){
    			String events = homeFeedService.searchEvents(fbClient,user.getId());
    			System.out.println("FacebookController.sendEvent() --- Sending Email");
    			
    			FacebookEmailService email = new FacebookEmailService();
    			email.sendMail(user.getEmail(), events);
	            
    		}else{
    			System.out.println("User not yet logged in");
    		}
    	}	
    	@Scheduled(fixedRate = 5000)
    	public void fetchUserDetails(){
    		if(fbClient!=null && user!=null){
    			System.out.println("Start Scheduler");
    			List<UserHomeFeed> homeFeeds = homeFeedService.findFavoritePosts(fbClient,user.getId());
	            for(UserHomeFeed homeFeed:homeFeeds){
	      		    homeFeedRepo.save(homeFeed);
	      		}
	            System.out.println("End Scheduler");
	            
    		}else{
    			System.out.println("User not yet logged in");
    		}
    	}	
    }

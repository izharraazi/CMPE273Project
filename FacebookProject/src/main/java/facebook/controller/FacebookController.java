package facebook.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.restfb.FacebookClient;
import com.restfb.types.User;

import facebook.model.UserComment;
import facebook.model.UserHomeFeed;
import facebook.repository.UserCommentRepo;
import facebook.repository.UserHomeFeedRepo;
import facebook.service.FacebookConnection;
import facebook.service.HomeFeed;
import facebook.serviceImpl.FacebookConnectionService;
import facebook.serviceImpl.HomeFeedService;

@RestController
public class FacebookController {
		@Autowired
	    UserHomeFeedRepo homeFeedRepo;
	    
	    @Autowired
	    UserCommentRepo userCommentRepo;
	    
		private static String redirectURL = "http://localhost:8080/facebookApp";
		
		private static FacebookClient fbClient;
		private static User user;
		
		private FacebookConnection connection = new FacebookConnectionService();
		private HomeFeed homeFeed = new HomeFeedService();
	
        @RequestMapping(name = "facebookApp", method = RequestMethod.GET)
        public ModelAndView facebookLogin(@RequestParam(value="code") String profileCode, Model model) throws IOException{
       // public String facebookLogin(@RequestParam(value="code") String profileCode){
        	try{
            fbClient = connection.doFacebookLogin(profileCode, redirectURL);
            user = connection.getCurrentUser(fbClient);
            System.out.println("Inside here!!");
            //return "Data added into database";
            return new ModelAndView("search.jsp");
        	}catch(IOException IOException){
        		IOException.printStackTrace();
        		return null;
        	}catch(Exception exception){
        		exception.printStackTrace();
        		return null;
        	}
        }
        
        public void saveComments(List<UserComment> comments){
    		for(UserComment comment:comments){
    			userCommentRepo.save(comment);
    		}
    	}
    	
    	@Scheduled(fixedRate = 5000)
    	public void Test(){
    		if(fbClient!=null && user!=null){
    			System.out.println("Start Scheduler");
    			List<UserHomeFeed> homeFeeds = homeFeed.findFavoritePosts(fbClient,user.getId());
    			System.out.println("homeFeeds .. "+homeFeeds.size());
	            for(UserHomeFeed homeFeed:homeFeeds){
	      			//homeFeedRepo.save(homeFeed);
	      			//saveComments(homeFeed.getComments());
	      		}
	            System.out.println("End Scheduler");
    		}else{
    			System.out.println("User not yet logged in");
    		}
    	}
}

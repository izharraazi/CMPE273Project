package facebook.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		
		private FacebookClient fbClient;
		private User user;
		
		private FacebookConnection connection = new FacebookConnectionService();
		private HomeFeed homeFeed = new HomeFeedService();
	
        @RequestMapping(name = "facebookApp", method = RequestMethod.GET)
        //public ModelAndView facebookLogin(@RequestParam(value="code") String profileCode, Model model) throws IOException{
        public String facebookLogin(@RequestParam(value="code") String profileCode){
        	try{
            fbClient = connection.doFacebookLogin(profileCode, redirectURL);
            user = connection.getCurrentUser(fbClient);
            List<UserHomeFeed> homeFeeds = homeFeed.findFavoritePosts(fbClient,user.getId());
            for(UserHomeFeed homeFeed:homeFeeds){
    			homeFeedRepo.save(homeFeed);
    			saveComments(homeFeed.getComments());
    		}
            return "Data added into database";
            //return new ModelAndView("search.jsp");
        	}catch(IOException ioException){
        		ioException.printStackTrace();
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
}

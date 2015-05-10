package facebook.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.restfb.FacebookClient;
import com.restfb.types.User;


//import facebook.model.UserComment;
import facebook.model.UserHomeFeed;
//import facebook.repository.UserCommentRepo;
import facebook.repository.UserHomeFeedRepo;
import facebook.service.FacebookConnection;
import facebook.service.HomeFeed;
import facebook.serviceImpl.FacebookConnectionService;
import facebook.serviceImpl.HomeFeedService;


@RestController
public class FacebookController {
		@Autowired
	    UserHomeFeedRepo homeFeedRepo;
		
	    
	   // @Autowired
	   // UserCommentRepo userCommentRepo;
	    
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
        
       public void test1()
       {
    	   try {
    	    MongoClientURI uri  = new MongoClientURI("mongodb://Team273:Team273@ds061621.mongolab.com:61621/team273"); 
    	    MongoClient client = new MongoClient(uri);
    	    DB db = client.getDB(uri.getDatabase());
    	    
    	   
    		BasicDBObject searchQuery = new BasicDBObject();
    		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
    		obj.add(new BasicDBObject("userid","897341676989726"));
    		obj.add(new BasicDBObject("type", "status"));
    		searchQuery.put("$and", obj);
    		DBCollection table = db.getCollection("userHomeFeed");
    		DBCursor cursor = table.find(searchQuery);
    		//List<UserHomeFeed> homeFeeds = new ArrayList<UserHomeFeed>();
    		while (cursor.hasNext()) {
    			DBObject a = cursor.next();
    			System.out.println("Id: "+a.get("id"));
    			System.out.println("like: "+a.get("like"));
    			System.out.println("link: "+a.get("link"));
    			System.out.println("message: "+a.get("message"));
    			System.out.println("type: "+a.get("type"));
    			System.out.println("userid: "+a.get("userid"));
    				
    		} 
    		cursor.close();
    	   }
    		catch (UnknownHostException e) {
    		e.printStackTrace();
    	    } catch (MongoException e) {
    		e.printStackTrace();
    	    }
       }
         
       
    	@Scheduled(fixedRate = 5000)
    	public void Test(){
    		if(fbClient!=null && user!=null){
    			System.out.println("Start Scheduler");
    			List<UserHomeFeed> homeFeeds = homeFeed.findFavoritePosts(fbClient,user.getId());
    			System.out.println("homeFeeds .. "+homeFeeds.size());
	            for(UserHomeFeed homeFeed:homeFeeds){
	            
	      		    homeFeedRepo.save(homeFeed);
	      	
	      			//saveComments(homeFeed.getComments());
	      		}
	            System.out.println("End Scheduler");
	           test1();//897341676989726_906320709425156
	            
    		}else{
    			System.out.println("User not yet logged in");
    		}
    	}
    	
    	
    	
    }

package facebook.serviceImpl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Post;
import com.restfb.types.Post.Comments;
import com.restfb.types.Post.Likes;

import facebook.Utility.HomeFeedUtility;
import facebook.model.UserHomeFeed;
import facebook.service.HomeFeed;

@Component
public class HomeFeedService implements HomeFeed {
	
	private HomeFeedUtility feedUtility = new HomeFeedUtility();
	
	@Override
	public List<UserHomeFeed> findFavoritePosts(FacebookClient fbClient,String userId) {

	int flag = 0;
	List<UserHomeFeed> finalFeeds = new ArrayList<UserHomeFeed>();
	Connection<Post> homeFeed = fbClient.fetchConnection("me/home", Post.class, Parameter.with("fields","id,link,type,description,from,likes,comments,message,created_time,picture"),Parameter.with("limit", 10));
	do{
		System.out.println("Page: "+flag );
	    System.out.println(homeFeed.getData().size()+" userId -- "+userId);
	    Map<Post,Comments> commentMap = new HashMap<Post, Post.Comments>();
	    Map<Post,Likes> likeMap = new HashMap<Post, Likes>();
	    for (Post post: homeFeed.getData()){
		    	Comments comment = post.getComments();
		    	if(comment!=null && !commentMap.containsKey(post)){
		    		commentMap.put(post, comment);
		    	}
		    	Likes like = post.getLikes();
		    	if(like!=null && !likeMap.containsKey(post)){
		    		likeMap.put(post, like);
		    	}
	    }
	    List<UserHomeFeed> userHomeFeeds = feedUtility.createHomeFeedData(commentMap, likeMap, userId, fbClient);
	    finalFeeds.addAll(userHomeFeeds);
	    int i = 1;
	    for(UserHomeFeed feed:userHomeFeeds){
	    	System.out.println(" -DATA- "+i);
	    	i++;
	    	System.out.println(" -- "+feed.getLike());
	    	System.out.println(" -- "+feed.getId());
	    	System.out.println(" -- "+feed.getCreated_date());
	    	System.out.println(" -- "+feed.getLink());
	    	System.out.println(" -- "+feed.getMessage());
	    	System.out.println(" -- "+feed.getType());
	    	System.out.println(" -- "+feed.getPicture());
	    	System.out.println(" -- "+feed.getComments());
	    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	    }
	    homeFeed = fbClient.fetchConnectionPage(homeFeed.getNextPageUrl(), Post.class);
	    flag++;
	}while(flag<5);
	    System.out.println("finalFeeds--" +finalFeeds.size());
	    return finalFeeds;
	}

	@Override
	public List<UserHomeFeed> fetchPostsOnType(String userId, String postType) {
		List<UserHomeFeed> homeFeeds = new ArrayList<UserHomeFeed>();
 	   try {
   	    MongoClientURI uri  = new MongoClientURI("mongodb://Team273:Team273@ds061621.mongolab.com:61621/team273"); 
   	    MongoClient client = new MongoClient(uri);
   	    DB db = client.getDB(uri.getDatabase());
   		BasicDBObject searchQuery = new BasicDBObject();
   		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
   		obj.add(new BasicDBObject("userid",userId));
   		obj.add(new BasicDBObject("type",postType));
   		searchQuery.put("$and", obj);
   		DBCollection table = db.getCollection("userHomeFeed");
   		DBCursor cursor = table.find(searchQuery);
   		while (cursor.hasNext()) {
   			DBObject a = cursor.next();
   			UserHomeFeed feed = new UserHomeFeed();
   			feed.setId((String)a.get("_id"));
   			feed.setLike((Boolean)a.get("like"));
   			feed.setLink((String)a.get("link"));
   			feed.setMessage((String)a.get("message"));
   			feed.setType((String)a.get("type"));
   			feed.setUserid((String)a.get("userid"));
   			feed.setPicture((String)a.get("picture"));
   			homeFeeds.add(feed);
   			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
   			//System.out.println(cursor.next());
   			
   			/*System.out.println("Id: "+a.get("_id"));
   			System.out.println("like: "+a.get("like"));
   			System.out.println("link: "+a.get("link"));
   			System.out.println("message: "+a.get("comments"));
   			System.out.println("type: "+a.get("type"));
   			System.out.println("userid: "+a.get("userid"));
   			System.out.println("Picture: "+a.get("picture"));*/
   				
   		} 
   		cursor.close();
   	   }
   		catch (UnknownHostException e) {
   		e.printStackTrace();
   	    } catch (MongoException e) {
   		e.printStackTrace();
   	    }
	return homeFeeds;
      }

}

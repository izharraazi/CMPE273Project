package facebook.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import com.restfb.types.Post.Comments;
import com.restfb.types.Post.Likes;

import facebook.Utility.HomeFeedUtility;
import facebook.model.UserHomeFeed;
import facebook.service.HomeFeed;

public class HomeFeedService implements HomeFeed {
	
	private HomeFeedUtility feedUtility = new HomeFeedUtility();
	
	@Override
	public List<UserHomeFeed> findFavoritePosts(FacebookClient fbClient,String userId) {
		 ArrayList<String> postArray = new ArrayList<String>();
		    Connection<Post> homeFeed = fbClient.fetchConnection("me/home", Post.class);
		    for (Post posts: homeFeed.getData()){
			  	  postArray.add(posts.getMessage());
		    }
		 Calendar cal = Calendar.getInstance();
   		 cal.setTime(new Date());
   		 int decrement = -1;
   		 cal.add(Calendar.DATE, decrement);
   		 System.out.println("Date : "+cal.getTime());
   		  outerloop:
		    while (homeFeed.hasNext()) {
		    	Connection<Post> nextPageFeed = fbClient.fetchConnectionPage(homeFeed.getNextPageUrl(),Post.class);
		    	 for (Post posts: nextPageFeed.getData()){
		    		 if(posts.getCreatedTime().before(cal.getTime())){
		    			 break outerloop;
		    		 }
		    		 if(posts.getCreatedTime().after(cal.getTime()))
		         	  postArray.add(posts.getMessage());
		    	 }
		    }
		    System.out.println(postArray.size()+" userId -- "+userId);
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
		    List<UserHomeFeed> userHomeFeeds = feedUtility.createHomeFeedData(commentMap, likeMap, userId);
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
		    	System.out.println(" -- "+feed.getCommentlist());
		    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		    }
		    return userHomeFeeds;
	}

}

package facebook.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

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
	Connection<Post> homeFeed = fbClient.fetchConnection("me/home", Post.class, Parameter.with("fields","id,link,type,description,from,likes,comments,message,created_time"),Parameter.with("limit", 10));
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
	    	//System.out.println(" -- "+feed.getCommentlist());
	    	System.out.println(" -- "+feed.getComments());
	    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	    }
	    homeFeed = fbClient.fetchConnectionPage(homeFeed.getNextPageUrl(), Post.class);
	    flag++;
	}while(flag<5);
	    System.out.println("finalFeeds--" +finalFeeds.size());
	    return finalFeeds;
	}

}

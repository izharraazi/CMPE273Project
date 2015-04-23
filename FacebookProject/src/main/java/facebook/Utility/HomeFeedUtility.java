package facebook.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.restfb.types.Comment;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post;
import com.restfb.types.Post.Comments;
import com.restfb.types.Post.Likes;

import facebook.model.UserComment;
import facebook.model.UserHomeFeed;

public class HomeFeedUtility {
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	List<UserHomeFeed> userHomeFeed;
	
	@SuppressWarnings("rawtypes")
	public List<UserHomeFeed> createHomeFeedData(Map<Post,Comments> commentMap, Map<Post,Likes> likeMap, String userId){
		userHomeFeed = new ArrayList<UserHomeFeed>();
		List<UserComment> userComments = new ArrayList<UserComment>();
		ArrayList<String> commentId = new ArrayList<String>();
		
		Iterator commentIterator = commentMap.entrySet().iterator();
	    Iterator likeIterator = likeMap.entrySet().iterator();
	    while (likeIterator.hasNext()) {
	        Map.Entry mapEntry = (Map.Entry)likeIterator.next();
	        Likes postLikes = (Likes) mapEntry.getValue();
	        if(postLikes.getData()!=null && !postLikes.getData().isEmpty()){
	        	List<NamedFacebookType> likes = postLikes.getData();
		        for(NamedFacebookType like:likes){
		        	if(like.getId().equals(userId)){
		        		Post post = (Post) mapEntry.getKey();
		        		UserHomeFeed homeFeed = getEquvivalentHomeFeed(post.getId(), userHomeFeed);
		        		addFeedData(homeFeed, post);
		        		homeFeed.setLike(true);
		        	}
		        }
	        }
	    }
	    while (commentIterator.hasNext()) {
	        Map.Entry mapEntry = (Map.Entry)commentIterator.next();
	        Comments postComment = (Comments) mapEntry.getValue();
	        if(postComment.getData()!=null && !postComment.getData().isEmpty()){
	        	List<Comment> comments = postComment.getData();
		        for(Comment comment:comments){
		        	if(comment.getFrom().getId().equals(userId)){
		        		Post post = (Post) mapEntry.getKey();
		        		
		        		UserComment comment2 = new UserComment();
	        			comment2.setId(comment.getId());
	        			comment2.setCommentmessage(comment.getMessage());
	        			commentId.add(comment.getId());
	        			userComments.add(comment2);
	        			
	        			UserHomeFeed homeFeed = getEquvivalentHomeFeed(post.getId(), userHomeFeed);
	        			addFeedData(homeFeed, post);
		        		homeFeed.setCommentlist(commentId);
		        		homeFeed.setComments(userComments);
		        	}
		        }
	        }
	    }
	    return userHomeFeed;
	}
	
	public UserHomeFeed getEquvivalentHomeFeed(String postId,List<UserHomeFeed> userHomeFeed){
        for(UserHomeFeed feed : userHomeFeed)
        {
            if(feed.getId().equals(postId))
            return feed;
        }
        UserHomeFeed homeFeed = new UserHomeFeed();
        userHomeFeed.add(homeFeed);
        return homeFeed;
	}
	
	public UserHomeFeed addFeedData(UserHomeFeed homeFeed, Post post){
		homeFeed.setId(post.getId());
		//homeFeed.setLike(false);
		homeFeed.setCreated_date(formater.format(post.getCreatedTime()));
		if(post.getLink()!=null && !post.getLink().isEmpty())
		homeFeed.setLink(post.getLink());
		if(post.getType()!=null && !post.getType().isEmpty())
		homeFeed.setType(post.getType());
		if(post.getMessage()!=null && !post.getMessage().isEmpty()){
		homeFeed.setMessage(post.getMessage());
		}else{
		homeFeed.setMessage("No Message");
		}
		//ArrayList<String> cc = new ArrayList<String>();
		//cc.add("asd");
		//homeFeed.setCommentlist(cc);
		//homeFeed.setComments(new ArrayList<UserComment>());
		return homeFeed;
	}
}

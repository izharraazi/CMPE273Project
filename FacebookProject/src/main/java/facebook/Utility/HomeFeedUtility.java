package facebook.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Comment;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post;
import com.restfb.types.Post.Comments;
import com.restfb.types.Post.Likes;

import facebook.model.UserComment;
import facebook.model.UserHomeFeed;

public class HomeFeedUtility {
	Date date = new Date();
   private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
   List<UserHomeFeed> userHomeFeed;
   
   @SuppressWarnings("rawtypes")
   public List<UserHomeFeed> createHomeFeedData(Map<Post,Comments> commentMap, Map<Post,Likes> likeMap, String userId, FacebookClient fbClient){
      userHomeFeed = new ArrayList<UserHomeFeed>();
      Set<UserComment> userComments = new HashSet<UserComment>();
      ArrayList<String> commentId = new ArrayList<String>();
      
      Iterator commentIterator = commentMap.entrySet().iterator();
      Iterator likeIterator = likeMap.entrySet().iterator();
       
       int i = 1;
       Post likePost = null;
       while (likeIterator.hasNext()) {
           Map.Entry mapEntry = (Map.Entry)likeIterator.next();
           Likes postLikes = (Likes) mapEntry.getValue();
           likePost = (Post) mapEntry.getKey();
           if(postLikes.getData()!=null && !postLikes.getData().isEmpty()){
            List<NamedFacebookType> likes = postLikes.getData();
              for(NamedFacebookType like:likes){
               if(like.getId().equals(userId)){
            	   System.out.println("--"+i+" == "+likePost.getId());
                  UserHomeFeed homeFeed = getEquvivalentHomeFeed(likePost.getId(), userHomeFeed);
                  addFeedData(homeFeed, likePost, userId);
                  homeFeed.setLike(true);
               }
              }
           }
           Connection<Post.Likes> likes = fbClient.fetchConnection(likePost.getId()+"/likes", Post.Likes.class);
           if(likes.getNextPageUrl()!=null && !likes.getNextPageUrl().isEmpty()){
            Connection<NamedFacebookType> nextLikes = fbClient.fetchConnectionPage(likes.getNextPageUrl(), NamedFacebookType.class);
            outerloop:
            do{
               List<NamedFacebookType> nextLikesData = nextLikes.getData();
                 for(NamedFacebookType nextLikeData:nextLikesData){
                     if(nextLikeData.getId().equals(userId)){
                    	 System.out.println("--"+i+" =~~~~~= "+likePost.getId());
                        UserHomeFeed homeFeed = getEquvivalentHomeFeed(likePost.getId(), userHomeFeed);
                        addFeedData(homeFeed, likePost, userId);
                        homeFeed.setLike(true);
                     }
                 }
                    if(nextLikes.getNextPageUrl()!=null && !nextLikes.getNextPageUrl().isEmpty()){
                    nextLikes = fbClient.fetchConnectionPage(nextLikes.getNextPageUrl(), NamedFacebookType.class);
                    }else{
                    	break outerloop;
                    }
               }while(!nextLikes.hasNext());
           }
        i++;
       }
       
       int j = 1;
       Post commentPost = null;
       while (commentIterator.hasNext()) {
           Map.Entry mapEntry = (Map.Entry)commentIterator.next();
           Comments postComment = (Comments) mapEntry.getValue();
           commentPost = (Post) mapEntry.getKey();
           if(postComment.getData()!=null && !postComment.getData().isEmpty()){
            List<Comment> comments = postComment.getData();
              for(Comment comment:comments){
               if(comment.getFrom().getId().equals(userId)){
            	   System.out.println("INSIDE COMMENT.. ");
                  //Post post = (Post) mapEntry.getKey();
                  
                  UserComment comment2 = new UserComment();
                  comment2.setId(comment.getId());
                  comment2.setCommentmessage(comment.getMessage());
                  commentId.add(comment.getId());
                  userComments.add(comment2);
                  
                  UserHomeFeed homeFeed = getEquvivalentHomeFeed(commentPost.getId(), userHomeFeed);
                  addFeedData(homeFeed, commentPost, userId);
                 // homeFeed.setCommentlist(commentId);
                 // if(homeFeed.getComments()!=null && !homeFeed.getComments().isEmpty() && !homeFeed.getComments().contains(comment2.getId()))
                  homeFeed.setComments(userComments);
               }
              }
           }
           Connection<Post.Comments> comments = fbClient.fetchConnection(commentPost.getId()+"/comments", Post.Comments.class);
           if(comments.getNextPageUrl()!=null && !comments.getNextPageUrl().isEmpty()){
            Connection<Comment> nextComments = fbClient.fetchConnectionPage(comments.getNextPageUrl(), Comment.class);
            outerloop:
            do{
               List<Comment> nextCommentsData = nextComments.getData();
	             for(Comment nextCommentData:nextCommentsData){
                 if(nextCommentData.getId().equals(userId)){
                	 System.out.println("--"+i+" =~~~~~= "+commentPost.getId());
                	 UserComment comment2 = new UserComment();
                     comment2.setId(nextCommentData.getId());
                     comment2.setCommentmessage(nextCommentData.getMessage());
                     commentId.add(nextCommentData.getId());
                     userComments.add(comment2);
                     
                     UserHomeFeed homeFeed = getEquvivalentHomeFeed(commentPost.getId(), userHomeFeed);
                     addFeedData(homeFeed, commentPost, userId);
                     //homeFeed.setCommentlist(commentId);
                     homeFeed.setComments(userComments);;
                 }
	             }
                 if(nextComments.getNextPageUrl()!=null && !nextComments.getNextPageUrl().isEmpty()){
                	nextComments = fbClient.fetchConnectionPage(nextComments.getNextPageUrl(), Comment.class);
                }else{
                	break outerloop;
                }
               }while(!nextComments.hasNext());
           }
        j++;
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
   
   public UserHomeFeed addFeedData(UserHomeFeed homeFeed, Post post, String userid){
	  homeFeed.setUserid(userid);
      homeFeed.setId(post.getId());
      homeFeed.setCreated_date(formater.format(post.getCreatedTime()));
      homeFeed.setSaved_date(formater.format(new Date()));
      
      if(post.getLink()!=null && !post.getLink().isEmpty())
      homeFeed.setLink(post.getLink());
      if(post.getType()!=null && !post.getType().isEmpty())
      homeFeed.setType(post.getType());
      if(post.getMessage()!=null && !post.getMessage().isEmpty()){
      homeFeed.setMessage(post.getMessage());
      }else{
      homeFeed.setMessage("No Message");
      }
      return homeFeed;
   }
}

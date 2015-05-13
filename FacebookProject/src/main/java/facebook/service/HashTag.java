package facebook.service;

import java.util.List;

import com.restfb.FacebookClient;
import com.restfb.types.Post;

import facebook.model.UserHashTag;
import facebook.model.UserHomeFeed;
/*
 * Interface to define all the methods related to HashTag 
 */
public interface HashTag {
	public List<String> findTrendingHashTags(FacebookClient fbClient,String userId);
}

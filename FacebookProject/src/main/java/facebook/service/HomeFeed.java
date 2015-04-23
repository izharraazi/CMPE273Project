package facebook.service;

import java.util.List;

import com.restfb.FacebookClient;

import facebook.model.UserHomeFeed;

public interface HomeFeed {
	public List<UserHomeFeed> findFavoritePosts(FacebookClient fbClient,String userId);
}

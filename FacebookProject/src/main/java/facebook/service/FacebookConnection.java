package facebook.service;

import java.io.IOException;

import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.User;

public interface FacebookConnection {
	public AccessToken getAccessToken(String profileCode, String redirectURL) throws IOException;
	public FacebookClient doFacebookLogin(String profileCode, String redirectURL) throws IOException;
	public User getCurrentUser(FacebookClient fbClient) throws IOException;
}

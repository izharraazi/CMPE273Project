package facebook.serviceImpl;

import java.io.IOException;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.WebRequestor;
import com.restfb.WebRequestor.Response;
import com.restfb.types.User;

import facebook.service.FacebookConnection;

public class FacebookConnectionService implements FacebookConnection{
	
    private static String clientId = "1444903262469722";
    private static String secretId = "942e1d8a05ed487f67c405143e3c5215";
    private FacebookClient fbClient;

	@Override
	public AccessToken getAccessToken(String profileCode, String redirectURL) throws IOException {
        WebRequestor requester = new DefaultWebRequestor();
        Response accessTokenResponse = requester.executeGet("https://graph.facebook.com/oauth/access_token?client_id=" + clientId + "&redirect_uri=" + redirectURL + "&client_secret=" + secretId + "&code=" + profileCode);
        return AccessToken.fromQueryString(accessTokenResponse.getBody());
    }

	@Override
	public FacebookClient doFacebookLogin(String profileCode, String redirectURL)throws IOException {
	AccessToken accessToken = getAccessToken(profileCode, redirectURL);
    String token = accessToken.getAccessToken();
    System.out.println("access Token" +accessToken);
    fbClient = new DefaultFacebookClient(token);
    return fbClient;
	}

	@Override
	public User getCurrentUser(FacebookClient fbClient)throws IOException {
	    User user = fbClient.fetchObject("me",com.restfb.types.User.class);;
		return user;
	}
}

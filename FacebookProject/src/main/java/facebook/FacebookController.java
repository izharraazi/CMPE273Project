package facebook;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.WebRequestor;
import com.restfb.types.Post;
import com.restfb.types.User;

@RestController
public class FacebookController {
		private static String redirectURL   = "http://localhost:8080/facebookApp";
        private static String clientId = "1444903262469722";
        private static String secretId = "942e1d8a05ed487f67c405143e3c5215";
        
        private FacebookClient fbClient;
        private User user;
        
        private AccessToken getAccessToken(String profileCode, String redirectURL) throws IOException {
            WebRequestor requester = new DefaultWebRequestor();
            WebRequestor.Response accessTokenResponse = requester.executeGet("https://graph.facebook.com/oauth/access_token?client_id=" + clientId + "&redirect_uri=" + redirectURL + "&client_secret=" + secretId + "&code=" + profileCode);
            return AccessToken.fromQueryString(accessTokenResponse.getBody());
        }

        @RequestMapping(name = "facebookApp", method = RequestMethod.GET)
        //public ModelAndView facebookLogin(@RequestParam(value="code") String profileCode) throws IOException{
        public ArrayList<String> facebookLogin(@RequestParam(value="code") String profileCode) throws IOException{
            AccessToken accessToken = getAccessToken(profileCode, redirectURL);
            String token = accessToken.getAccessToken();
            fbClient = new DefaultFacebookClient(token);
            user = fbClient.fetchObject("me",com.restfb.types.User.class);
            ArrayList<String> postArray = new ArrayList<String>();
            Connection<Post> userPosts = fbClient.fetchConnection("me/posts", Post.class);
            for (Post posts: userPosts.getData()){
            	postArray.add(posts.getMessage());
            }
            return postArray;
            //return new ModelAndView("search.jsp");
        }
}

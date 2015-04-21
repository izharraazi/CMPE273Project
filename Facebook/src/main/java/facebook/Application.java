package facebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        /*FacebookClient fbClient = new DefaultFacebookClient("CAACEdEose0cBAIckK9r7355XDPgyetocsqKZCkyQURHMj8g9ZACtBpkPsLqoAt2VIc7V5S6jROqjhfsRl3yoDH9naXfiZBv4N6TSl8r4DaDQnURoM5gwGDJ10dR1ai30mC6LgUtRZB56gbi8aeO0H6m6BSGcr9ueZBSSi0nNpoXvCSXalMl4iVPMaeQNqZBwxkCKVnxhllvO3vRw6oycZACUeVeIcwwXeUZD");
        User me = fbClient.fetchObject("749936265121703",com.restfb.types.User.class);
        FriendList fList = fbClient.fetchObject("749936265121703",com.restfb.types.FriendList.class);
        System.out.println(me.getEducation());
        System.out.println();*/
    }
}
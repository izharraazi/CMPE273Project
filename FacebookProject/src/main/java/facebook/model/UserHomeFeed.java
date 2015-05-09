package facebook.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

public class UserHomeFeed {

	
	@Id
	@NotNull
	private String id;
	
	@Null
	private String created_date;
	
	@Null
	private String link;
	
	@Null
	private String type;
	
	@Null
	private Boolean like;
	
	@Null
	private String message;
	
	@Null
	private ArrayList<String> commentlist;
	
	@Transient
	private List<UserComment> comments = new ArrayList<UserComment>();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<String> getCommentlist() {
		return commentlist;
	}
	public void setCommentlist(ArrayList<String> commentlist) {
		this.commentlist = commentlist;
	}
	public Boolean getLike() {
		return like;
	}
	public void setLike(Boolean like) {
		this.like = like;
	}
	public List<UserComment> getComments() {
		return comments;
	}
	public void setComments(List<UserComment> comments) {
		this.comments = comments;
	}
	

}
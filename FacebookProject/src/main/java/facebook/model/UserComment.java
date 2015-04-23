package facebook.model;

import javax.validation.constraints.Null;


public class UserComment {
	
	@Null
	private String id;
	
	@Null
	private String commentmessages;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommentmessage() {
		return commentmessages;
	}
	public void setCommentmessage(String commentmessage) {
		this.commentmessages = commentmessage;
	}
}

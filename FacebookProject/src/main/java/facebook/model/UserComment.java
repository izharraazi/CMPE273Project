
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
	@Override
	public String toString() {
		return "UserComment [commentmessages=" + commentmessages + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserComment other = (UserComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

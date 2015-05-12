package facebook.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

public class UserHashTag {

	
	@Id
	@NotNull
	private String id;
	
	
	@NotNull
	private String createdBy;
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Transient
	private List<String> hashTag = new ArrayList<String>();
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getHashTag() {
		return hashTag;
	}
	public void setHashTag(List<String> hashTag) {
		this.hashTag = hashTag;
	}
	

}

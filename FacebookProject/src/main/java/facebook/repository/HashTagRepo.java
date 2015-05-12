package facebook.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import facebook.model.UserComment;
import facebook.model.UserHashTag;

public interface HashTagRepo extends MongoRepository<UserHashTag,String> {

	public UserComment findById(String id);

}

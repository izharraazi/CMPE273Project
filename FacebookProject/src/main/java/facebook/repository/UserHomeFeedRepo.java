package facebook.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import facebook.model.UserHomeFeed;

public interface UserHomeFeedRepo extends MongoRepository<UserHomeFeed,String> {

	public UserHomeFeed findById(String id);

}

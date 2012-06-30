package org.socialsignin.provider.strategy.connectionrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

public class SpecifiedUserIdConnectionRepositoryStrategy implements
		ConnectionRepositoryStrategy {

	private String userId;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	public SpecifiedUserIdConnectionRepositoryStrategy()
	{
		
	}
	
	public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
		this.usersConnectionRepository = usersConnectionRepository;
	}

	public SpecifiedUserIdConnectionRepositoryStrategy(UsersConnectionRepository usersConnectionRepository,String userId)
	{
		this.usersConnectionRepository  = usersConnectionRepository;
		this.userId = userId;
	}
	
	@Override
	public ConnectionRepository getAuthenticatedConnectionRepository() {
		return usersConnectionRepository.createConnectionRepository(userId);
	}

	

}

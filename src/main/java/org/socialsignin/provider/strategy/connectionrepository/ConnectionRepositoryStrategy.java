package org.socialsignin.provider.strategy.connectionrepository;

import org.springframework.social.connect.ConnectionRepository;

public interface ConnectionRepositoryStrategy {

	ConnectionRepository getAuthenticatedConnectionRepository();

	
}

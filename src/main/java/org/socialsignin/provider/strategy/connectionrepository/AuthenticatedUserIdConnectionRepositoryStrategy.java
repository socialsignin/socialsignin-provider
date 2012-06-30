package org.socialsignin.provider.strategy.connectionrepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUserIdConnectionRepositoryStrategy 
extends SpecifiedUserIdConnectionRepositoryStrategy
{

	
	public AuthenticatedUserIdConnectionRepositoryStrategy()
	{
		this.setUserId(getAuthenticatedUserName());
	}
	
	private String getAuthenticatedUserName() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication == null || authentication.getName().equals("anonymousUser") ? null : authentication.getName();
	}


}

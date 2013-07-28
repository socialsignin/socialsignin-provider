package org.socialsignin.provider;

import org.springframework.social.ExpiredAuthorizationException;

@SuppressWarnings("serial")
public class ExpiredProviderConnectionAuthorizationException extends
		ExpiredAuthorizationException {

	
	public ExpiredProviderConnectionAuthorizationException(String providerId)
	{
		super(providerId);
	}

	
}

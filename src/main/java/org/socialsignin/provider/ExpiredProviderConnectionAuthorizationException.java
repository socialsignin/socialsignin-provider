package org.socialsignin.provider;

import org.springframework.social.ExpiredAuthorizationException;

@SuppressWarnings("serial")
public class ExpiredProviderConnectionAuthorizationException extends
		ExpiredAuthorizationException {

	private String providerId;
	
	public ExpiredProviderConnectionAuthorizationException(String providerId)
	{
		this.providerId = providerId;
	}

	public String getProviderId() {
		return providerId;
	}
	
	
	
	
}

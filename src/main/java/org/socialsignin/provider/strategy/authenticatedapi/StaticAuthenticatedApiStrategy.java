package org.socialsignin.provider.strategy.authenticatedapi;


public class StaticAuthenticatedApiStrategy<S> implements
		AuthenticatedApiStrategy<S> {

	private S authenticatedApi;
	
	public StaticAuthenticatedApiStrategy(S authenticatedApi)
	{
		this.authenticatedApi = authenticatedApi;
	}


	@Override
	public S getAuthenticatedApi() {
		return authenticatedApi;
	}

	
}


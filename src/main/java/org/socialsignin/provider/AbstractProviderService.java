package org.socialsignin.provider;



import javax.annotation.PostConstruct;

import org.socialsignin.provider.strategy.authenticatedapi.AuthenticatedApiStrategy;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractProviderService<S,C extends AbstractProviderConfig<S>> implements ProviderService<S>{
	
	
	private boolean registered;
	
	protected C providerConfig;
	
	@Autowired
	protected void setProviderConfig(C providerConfig)
	{
		this.providerConfig = providerConfig;
	}

	@PostConstruct
	public void register()
	{
		if (!registered)
		{
			providerConfig.register();
			this.registered=true;
		}
	}
	
	
	public AbstractProviderService()
	{
		
	}
		
	public AbstractProviderService(C providerConfig)
	{
		this.providerConfig = providerConfig;
		register();
	}
	
	
	public S getAuthenticatedApi()
	{
		return providerConfig.getAuthenticatedApiStrategy().getAuthenticatedApi();
	}
	
	public S getAuthenticatedApi(String userId)
	{
		AuthenticatedApiStrategy<S> usersAuthenticatedApiStrategy = providerConfig.getAuthenticatedApiStrategy(userId);
		if (usersAuthenticatedApiStrategy != null)
		{
			return usersAuthenticatedApiStrategy.getAuthenticatedApi();
		}
		else
		{
			return null;
		}

	}
	
	
	public abstract S getUnauthenticatedApi();

	public S getApi()
	{
		S authenticatedApi = getAuthenticatedApi();
		if (authenticatedApi != null)
		{
			return authenticatedApi;
		}
		else
		{
			return getUnauthenticatedApi();
		}
	}

}

/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.socialsignin.provider;

import org.socialsignin.provider.strategy.authenticatedapi.AuthenticatedApiStrategy;
import org.socialsignin.provider.strategy.authenticatedapi.ConnectionRepositoryAuthenticatedApiStrategy;
import org.socialsignin.provider.strategy.authenticatedapi.StaticAuthenticatedApiStrategy;
import org.socialsignin.provider.strategy.connectionrepository.ConnectionRepositoryStrategy;
import org.socialsignin.provider.strategy.connectionrepository.SpecifiedConnectionRepositoryStrategy;
import org.socialsignin.provider.strategy.connectionrepository.SpecifiedUserIdConnectionRepositoryStrategy;
import org.socialsignin.springsocial.security.ConnectInterceptorList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectInterceptor;

/** 
* @author Michael Lavelle
*/
public abstract class AbstractProviderConfig<S> {

	
	@Autowired(required=false)
	private ConnectionRepositoryStrategy connectionRepositoryStrategy;
	
	@Autowired(required=false)
	private UsersConnectionRepository usersConnectionRepository;
	
	@Autowired(required=false)
	private ConnectionRepository specifiedConnectionRepository;

	public abstract Class<S> getApiClass();
	

	protected ConnectionRepositoryStrategy getConnectionRepositoryStrategy()
	{
		if (connectionRepositoryStrategy != null)
		{
			return connectionRepositoryStrategy;
		}
		else
		{
			if (specifiedConnectionRepository != null)
			{
				return new SpecifiedConnectionRepositoryStrategy(specifiedConnectionRepository);
			}
			
			return null;
		}
	}
	
	private AuthenticatedApiStrategy<S> specifiedAuthenticatedApiStrategy;
	
	public AuthenticatedApiStrategy<S> getAuthenticatedApiStrategy() {

		if (specifiedAuthenticatedApiStrategy != null)
		{
			return specifiedAuthenticatedApiStrategy;
		}
		else
		{	
			if (getConnectionRepositoryStrategy() != null)
			{
				return new ConnectionRepositoryAuthenticatedApiStrategy<S>
				(getConnectionRepositoryStrategy().getAuthenticatedConnectionRepository(),getApiClass());
			}
			else
			{
				return null;
			}
		}
		
	}
	
	public AuthenticatedApiStrategy<S> getAuthenticatedApiStrategy(String userId) {

		if (usersConnectionRepository != null)
		{
			return new ConnectionRepositoryAuthenticatedApiStrategy<S>
			(usersConnectionRepository.createConnectionRepository(userId),getApiClass());
		}
		else
		{
			return null;
		}
		
	}
	
	
	
	
	public void setAuthenticatedApiStrategy(
			AuthenticatedApiStrategy<S> authenticatedApiStrategy) {
		this.specifiedAuthenticatedApiStrategy = authenticatedApiStrategy;
	}
	
	public AbstractProviderConfig(StaticAuthenticatedApiStrategy<S> authenticatedApiStrategy)
	{
		this.specifiedAuthenticatedApiStrategy = authenticatedApiStrategy;
	}
	
	public AbstractProviderConfig(S authenticatedApi)
	{
		this.specifiedAuthenticatedApiStrategy = new StaticAuthenticatedApiStrategy<S>(authenticatedApi);
	}
	

	public AbstractProviderConfig(ConnectionRepositoryAuthenticatedApiStrategy<S> authenticatedApiStrategy)
	{
		this.specifiedAuthenticatedApiStrategy = authenticatedApiStrategy;
	}
	
	public AbstractProviderConfig(ConnectionRepository connectionRepository)
	{
		this(new SpecifiedConnectionRepositoryStrategy(connectionRepository));
	}
	
	public AbstractProviderConfig(ConnectionRepository connectionRepository,UsersConnectionRepository usersConnectionRepository)
	{
		this(new SpecifiedConnectionRepositoryStrategy(connectionRepository),usersConnectionRepository);
	}
	
	public AbstractProviderConfig(ConnectionRepositoryStrategy connectionRepositoryStrategy,UsersConnectionRepository usersConnectionRepository)
	{
		this.connectionRepositoryStrategy = connectionRepositoryStrategy;
		this.usersConnectionRepository = usersConnectionRepository;
	}
	
	
	public AbstractProviderConfig(String userId,UsersConnectionRepository usersConnectionRepository)
	{
		this.connectionRepositoryStrategy = new SpecifiedUserIdConnectionRepositoryStrategy(usersConnectionRepository,userId);
		this.usersConnectionRepository = usersConnectionRepository;
	}
	
	public AbstractProviderConfig(ConnectionRepositoryStrategy connectionRepositoryStrategy)
	{
		this.connectionRepositoryStrategy = connectionRepositoryStrategy;
	}
	
	public AbstractProviderConfig(UsersConnectionRepository usersConnectionRepository,ConnectionRepositoryAuthenticatedApiStrategy<S> authenticatedApiStrategy)
	{
		this.specifiedAuthenticatedApiStrategy = authenticatedApiStrategy;
		this.usersConnectionRepository = usersConnectionRepository;
	}
	
	public AbstractProviderConfig()
	{
	}
	


	@Autowired(required=false)
	@Qualifier("connectInterceptorList")
	private ConnectInterceptorList connectInterceptorList;
	
	protected abstract ConnectInterceptor<S> getConnectInterceptor();
	

	public void register()
	{
		if (connectInterceptorList != null)
		{
			connectInterceptorList.add(getConnectInterceptor());
		}
	}
}

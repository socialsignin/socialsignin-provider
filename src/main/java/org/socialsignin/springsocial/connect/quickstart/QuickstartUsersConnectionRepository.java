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
package org.socialsignin.springsocial.connect.quickstart;

/*
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.extension.connect.inmemory.InMemoryConnectionRepository;
import org.springframework.social.extension.connect.inmemory.InMemoryUsersConnectionRepository;

/**
 * {@link UsersConnectionRepository} that stores Connection data in a simple
 * in-memory map structure
 * 
 * @author Michael Lavelle
 */
public class QuickstartUsersConnectionRepository extends InMemoryUsersConnectionRepository
{

	public QuickstartUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {
		super(connectionFactoryLocator);
		this.connectionRepositoriesByUserId = new TreeMap<String, InMemoryConnectionRepository>();

	}
	
	public SortedMap<String, InMemoryConnectionRepository> getConnectionRepositoriesByUserId()
	{
		return connectionRepositoriesByUserId;
	}
	
	public void addConnectionData(String userId, ConnectionData connectionData,
			int rank) {
		createQuickstartConnectionRepository(userId).addConnectionData(
				connectionData, rank);
	}
	
	
	public QuickstartUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator,Map<String,QuickstartConnectionRepository> connectionRepositories)  {
		super(connectionFactoryLocator);
		this.connectionRepositoriesByUserId = new TreeMap<String,InMemoryConnectionRepository>();
		for (Map.Entry<String,QuickstartConnectionRepository> connectionRepositoryEntry : connectionRepositories.entrySet())
		{
			connectionRepositoriesByUserId.put(connectionRepositoryEntry.getKey(), connectionRepositoryEntry.getValue());
		}
	}
	
	
	
	public QuickstartUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator,String userId,QuickstartConnectionRepository connectionRepository)  {
		super(connectionFactoryLocator);
		this.connectionRepositoriesByUserId = new TreeMap<String,InMemoryConnectionRepository>();
		{
			connectionRepositoriesByUserId.put(userId, connectionRepository);
		}
	}
	
	public QuickstartUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator,String userId,String providerId,List<QuickstartConnectionData> quickstartConnectionDataList)  {
		super(connectionFactoryLocator);
		this.connectionRepositoriesByUserId = new TreeMap<String,InMemoryConnectionRepository>();
			connectionRepositoriesByUserId.put(userId, new QuickstartConnectionRepository(userId,connectionFactoryLocator,providerId,
					new QuickstartProviderConnectionRepository(userId,providerId,quickstartConnectionDataList)));
	}
	
	public QuickstartUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator,String userId,String providerId,String sessionKey,int maxAgeMinutes)  {
		super(connectionFactoryLocator);
		this.connectionRepositoriesByUserId = new TreeMap<String,InMemoryConnectionRepository>();
			connectionRepositoriesByUserId.put(userId, new QuickstartConnectionRepository(userId,connectionFactoryLocator,providerId,
					new QuickstartProviderConnectionRepository(userId,providerId,Arrays.asList(new QuickstartConnectionData(providerId,sessionKey,maxAgeMinutes)))));
	}
	
	

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}
		return createQuickstartConnectionRepository(userId);
	}

	protected QuickstartConnectionRepository createQuickstartConnectionRepository(
			String userId) {
		QuickstartConnectionRepository connectionRepository = (QuickstartConnectionRepository)connectionRepositoriesByUserId
				.get(userId);
		if (connectionRepository == null) {
			connectionRepository = new QuickstartConnectionRepository(userId,
					connectionFactoryLocator);
			connectionRepositoriesByUserId.put(userId, connectionRepository);
		}

		return connectionRepository;
	}

}
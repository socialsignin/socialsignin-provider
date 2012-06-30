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

import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.extension.connect.inmemory.InMemoryConnectionRepository;
import org.springframework.social.extension.connect.inmemory.InMemoryProviderConnectionRepository;

/**
 * @author Michael Lavelle
 */
public class QuickstartConnectionRepository extends InMemoryConnectionRepository {


	
	public QuickstartProviderConnectionRepository getQuickstartProviderConnectionRepository(
			String providerId) {
		QuickstartProviderConnectionRepository repository = (QuickstartProviderConnectionRepository)providerRepositories
				.get(providerId);
		if (repository == null) {
			repository = new QuickstartProviderConnectionRepository(userId,
					providerId);
			providerRepositories.put(providerId, repository);
		}
		return repository;
	}
	
	
	
	@Override
	public InMemoryProviderConnectionRepository getInMemoryProviderConnectionRepository(
			String providerId) {
		return getQuickstartProviderConnectionRepository(providerId);
	}



	public SortedMap<String, InMemoryProviderConnectionRepository> getProviderRepositories()
	{
		return providerRepositories;
	}
	
	public QuickstartConnectionRepository(String userId,
			ConnectionFactoryLocator connectionFactoryLocator,String providerId,QuickstartProviderConnectionRepository quickstartProviderConnectionRepository) {
		super(userId,connectionFactoryLocator);
		this.providerRepositories = new TreeMap<String,InMemoryProviderConnectionRepository>();
		providerRepositories.put(providerId, quickstartProviderConnectionRepository);
	
	}
	

	public QuickstartConnectionRepository(String userId,
			ConnectionFactoryLocator connectionFactoryLocator,String providerId,String sessionKey,int maxAgeMinutes) {
		super(userId,connectionFactoryLocator);
		this.providerRepositories = new TreeMap<String,InMemoryProviderConnectionRepository>();
		providerRepositories.put(providerId, new QuickstartProviderConnectionRepository(userId,providerId,new QuickstartConnectionData(providerId,sessionKey,maxAgeMinutes)));
		
	}
	

	public QuickstartConnectionRepository(String userId,
			ConnectionFactoryLocator connectionFactoryLocator) {
		super(userId,connectionFactoryLocator);
	}
	

	

}
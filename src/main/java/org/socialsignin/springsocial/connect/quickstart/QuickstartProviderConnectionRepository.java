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

import java.util.List;
import java.util.SortedMap;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.extension.connect.inmemory.InMemoryProviderConnectionRepository;

/**
 * @author Michael Lavelle
 */
public class QuickstartProviderConnectionRepository extends InMemoryProviderConnectionRepository {


	public QuickstartProviderConnectionRepository(String userId, String providerId) {
		super(userId,providerId);
	}
	
	public QuickstartProviderConnectionRepository(String userId,String providerId,List<QuickstartConnectionData> connectionDataList) {
		super(userId,providerId);
		int rank = 1;
		for (ConnectionData connectionData : connectionDataList)
		{
			add(connectionData, rank++);
		}
	}
	
	public QuickstartProviderConnectionRepository(String userId,String providerId,QuickstartConnectionData connectionData) {
		super(userId,providerId);
		int rank = 1;
		add(connectionData, rank++);
	}
	
	public SortedMap<Integer, ConnectionData> getConnectionDataByRank()
	{
		return connectionDataByRank;
	}

	

}

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

import java.util.Map;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.extension.connect.inmemory.InMemoryConnectionRepository;
import org.springframework.social.extension.connect.inmemory.InMemoryProviderConnectionRepository;
import org.springframework.social.extension.connect.jdbc.AbstractUsersConnectionRepositoryTest;

/**
 * @author Michael Lavelle
 */
public class QuickstartUsersConnectionRepositoryTest
		extends
		AbstractUsersConnectionRepositoryTest<QuickstartUsersConnectionRepository> {

	@Override
	protected QuickstartUsersConnectionRepository createUsersConnectionRepository() {
		return new QuickstartUsersConnectionRepository(connectionFactoryRegistry);
	}

	@Override
	protected void setConnectionSignUpOnUsersConnectionRepository(
			QuickstartUsersConnectionRepository usersConnectionRepository,
			ConnectionSignUp connectionSignUp) {
		usersConnectionRepository.setConnectionSignUp(connectionSignUp);

	}

	@Override
	protected void insertConnection(String userId, String providerId,
			String providerUserId, int rank, String displayName,
			String profileUrl, String imageUrl, String accessToken,
			String secret, String refreshToken, Long expireTime) {
		ConnectionData connectionData = new ConnectionData(providerId,
				providerUserId, displayName, profileUrl, imageUrl, accessToken,
				secret, refreshToken, expireTime);
		usersConnectionRepository.createQuickstartConnectionRepository(userId)
				.getInMemoryProviderConnectionRepository(providerId)
				.add(connectionData, rank);
	}

	@Override
	protected Boolean checkIfProviderConnectionsExist(String providerId) {

		for (InMemoryConnectionRepository inMemoryConnectionRepository : ((QuickstartUsersConnectionRepository)usersConnectionRepository).getConnectionRepositoriesByUserId()
				.values()) {
			QuickstartConnectionRepository quickStartConnectionRepository = (QuickstartConnectionRepository)inMemoryConnectionRepository;
			for (Map.Entry<String, InMemoryProviderConnectionRepository> providerConnectionRepo : quickStartConnectionRepository.getProviderRepositories()
					.entrySet()) {
				if (providerConnectionRepo.getKey().equals(providerId)) {
					return ((QuickstartProviderConnectionRepository)providerConnectionRepo.getValue()).getConnectionDataByRank()
							.size() > 0;
				}
			}
		}
		return false;
	}

}

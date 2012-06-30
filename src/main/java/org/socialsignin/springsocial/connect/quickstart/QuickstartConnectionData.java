package org.socialsignin.springsocial.connect.quickstart;

import java.util.Date;
import java.util.UUID;

import org.springframework.social.connect.ConnectionData;

public class QuickstartConnectionData extends ConnectionData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuickstartConnectionData(String providerId, String providerUserId,
			String displayName, String profileUrl, String imageUrl,
			String accessToken, String secret, String refreshToken,
			Long expireTime) {
		super(providerId, providerUserId, displayName, profileUrl, imageUrl,
				accessToken, secret, refreshToken, expireTime);
	}
	
	public QuickstartConnectionData(String providerId,String accessToken,int maxAgeMinutes) {
		super(providerId, UUID.randomUUID().toString(), null, null, null,
				accessToken, null, null, new Date().getTime() + (maxAgeMinutes * 60 * 1000));
	}
	
	public QuickstartConnectionData(String providerId,String accessToken,String secret,int maxAgeMinutes) {
		super(providerId, UUID.randomUUID().toString(), null, null, null,
				accessToken, secret, null, new Date().getTime() + (maxAgeMinutes * 60 * 1000));
	}
	
	


}

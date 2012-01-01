package org.socialsignin.provider;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.connect.UsersConnectionRepository;


public abstract class AbstractProviderService<S> {
	
	public abstract Class<S> getApiClass();
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	private String getAuthenticatedUserName() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication == null ? null : authentication.getName();
	}
	
	
	private <T >Connection<T> getConnection(Class<T> clazz)
	{
		try
		{
			if ( getAuthenticatedUserName() != null)
			{
				List<Connection<T>> connections = usersConnectionRepository.createConnectionRepository( getAuthenticatedUserName()).findConnections(clazz);
				if (connections != null && connections.size() > 0) return connections.get(0);
			}
			return null;
		}
		catch (NotConnectedException exception)
		{
			return null;
		}
	}
	
	
	public S getAuthenticatedApi()
	{

		Connection<S> connection = getConnection(getApiClass());
		if (connection != null)
		{
			return connection.getApi();
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

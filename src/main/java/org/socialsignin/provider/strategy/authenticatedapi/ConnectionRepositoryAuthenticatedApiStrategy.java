package org.socialsignin.provider.strategy.authenticatedapi;

import java.util.List;

import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NotConnectedException;

public class ConnectionRepositoryAuthenticatedApiStrategy<S> implements
		AuthenticatedApiStrategy<S> {
	
	private Class<S> apiClass;
	
	
	private ConnectionRepository connectionRepository;
	

	
	public ConnectionRepositoryAuthenticatedApiStrategy(ConnectionRepository connectionRepository,Class<S> apiClass)
	{
		this.connectionRepository = connectionRepository;
		this.apiClass = apiClass;

	}

	
	private <T >Connection<T> getConnection(Class<T> clazz)
	{
		try
		{
			ConnectionRepository authenticatedConnectionRepository = connectionRepository;

			if (authenticatedConnectionRepository != null)
			{
				List<Connection<T>> connections = authenticatedConnectionRepository.findConnections(clazz);
				if (connections != null && connections.size() > 0) 
				{
					Connection<T> connection = connections.get(0);
 					authenticatedConnectionRepository.updateConnection(connection);
					return connection;
				}
				return null;
			}
			else
			{
				return null;
			}
		}
		catch (NotConnectedException exception)
		{
			return null;
		}
	}
	
	
	
	public S getAuthenticatedApi()
	{

		Connection<S> connection = getConnection(apiClass);
		if (connection != null)
		{
			try
			{
				if (!connection.hasExpired())
				{
					return connection.getApi();
				}
				else
				{
					
					// Attempt to refresh
					connection.refresh();
				}
			}
			catch(Exception e)
			{
				throw new ExpiredAuthorizationException(connection.createData().getProviderId());
			}
			if (connection.hasExpired())
			{
				throw new ExpiredAuthorizationException(connection.createData().getProviderId());
			}
			connectionRepository.updateConnection(connection);
			return connection.getApi();
		}
		else
		{
			return null;
		}
	}
	
	
	

	
}

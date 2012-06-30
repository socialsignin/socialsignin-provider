package org.socialsignin.provider;

public interface ProviderService<S> {

	public S getAuthenticatedApi();
	public S getAuthenticatedApi(String userId);
	public S getUnauthenticatedApi();
	public S getApi();



}

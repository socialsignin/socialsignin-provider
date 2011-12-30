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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectInterceptor;

/** 
* @author Michael Lavelle
*/
public abstract class AbstractProviderConfig<S> {

	@Autowired
	private ConnectionFactoryRegistry registry;
	
	@Autowired
	@Qualifier("connectInterceptorList")
	private ConnectInterceptorList connectInterceptorList;
	
	protected abstract ConnectionFactory<S> createConnectionFactory();
	protected abstract ConnectInterceptor<S> getConnectInterceptor();
	

	@PostConstruct
	public void register()
	{
		registry.addConnectionFactory(createConnectionFactory());
		connectInterceptorList.add(getConnectInterceptor());

	}
}

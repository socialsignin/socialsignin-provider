SocialSignin Provider Modules
=============================

SocialSignin Provider Modules are thin wrappers around the corresponding Spring Social Modules which allow
applications easy access to their chosen API clients for a number of common use-cases:

- Obtaining an API client for general non-user authorised use.
- Obtaining an authenticated API client acting on behalf of the currently authenticated user
- Obtaining an API client for authenticated api use on behalf of a given specified user.

eg.

```
/**
* This component is provided by the SocialSignin Twitter module, and will be available for injection into
* your components by enabling component scanning for SocialSignIn modules
*/
@Service
public class TwitterProviderService implements ProviderService<Twitter> {  				
		
		public Twitter getAuthenticatedApi() {...}
		public Twitter getAuthenticatedApi(String userId) {...}
		public Twitter getUnauthenticatedApi() {...}
		public Twitter getApi(); {...}
	}

```

This reduces the need to work with the lower-level connection apis of spring social when an 
application simply wants to to deal with API Clients directly rather than connections to APIs. 

The modules also enable configuration of provider-specific components from Spring-Social and Spring-Social-Security
to to registered easlily and via component scanning.
eg.
```
			<context:component-scan
				base-package="org.socialsignin.provider" />
```
This component scan:

- Creates and auto-registers ConnectionFactory beans with ConnectionFactoryRegistry for any socialsignin-provider modules on the classpath - configured
              by properties set in the applications property file.
- If your application uses Spring-Social-Security, this component scan also creates SpringSocialSecurityConnectInterceptors
            for any SocialSignin Provider modules on the classpath and makes a list of these interceptors available
            as a bean for injection into the ConnectController
- Creates ProviderService beans for all SocialSignin providers on the classpath, available for injection
            into your components, giving access to the API Clients for the common use-cases.
      


See <a href="https://github.com/socialsignin/socialsignin-showcase">SocialSignIn Showcase</a> or <a href="https://github.com/socialsignin/socialsignin-roo-showcase">SocialSignIn Roo Showcase</a>
for simple "hello world" applications using these modules.

Modules currently available
---------------------------

* <a href="https://github.com/socialsignin/socialsignin-twitter" >SocialSignin Twitter</a>
* <a href="https://github.com/socialsignin/socialsignin-facebook" >SocialSignin Facebook</a>
* <a href="https://github.com/socialsignin/socialsignin-tumblr" >SocialSignin Tumblr</a>
* <a href="https://github.com/socialsignin/socialsignin-linkedin" >SocialSignin LinkedIn</a>
* <a href="https://github.com/socialsignin/socialsignin-lastfm" >SocialSignin LastFm</a>
* <a href="https://github.com/socialsignin/socialsignin-soundcloud" >SocialSignin SoundCloud</a>
* <a href="https://github.com/socialsignin/socialsignin-mixcloud" >SocialSignin MixCloud</a>

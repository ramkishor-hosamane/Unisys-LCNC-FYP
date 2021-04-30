package api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.logging.Level;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skyve.CORE;
import org.skyve.EXT;
import org.skyve.domain.Bean;
import org.skyve.impl.bind.BindUtil;
import org.skyve.impl.domain.messages.SecurityException;
import org.skyve.impl.persistence.AbstractPersistence;
import org.skyve.impl.util.SQLMetaDataUtil;
import org.skyve.impl.util.UtilImpl;
import org.skyve.metadata.MetaDataException;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.metadata.user.User;
import org.skyve.persistence.DocumentFilter;
import org.skyve.persistence.DocumentQuery;
import org.skyve.persistence.Persistence;
import org.skyve.util.Util;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


import modules.user.domain.UserLogin;

public class Oauth2Filter extends AbstractRestFilter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException {
		if (
			super.doUnsecuredFilter(request, response, chain)) {
			return;
		}
		
		
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		
		
		System.out.println("request is "+httpRequest.getRequestURI());
		
		
		//If request is to authenticate and get Token OR 
		if(httpRequest.getRequestURI().equals("/ecommerce/api/authenticate")) {
			//System.out.println("Say authenticated");
			
	        AbstractPersistence persistence;
	        persistence = AbstractPersistence.get();
			persistence.evictAllCached();
			persistence.begin();

			User user = CORE.getRepository().retrieveUser("setup");
			persistence.setUser(user);
			
			chain.doFilter(httpRequest, httpResponse);
		
		}
		else if(httpRequest.getHeader("Authorization").startsWith("Bearer"))
		{
			
			String authorizationHeader = httpRequest.getHeader("Authorization");
			JwtUtil jwtUtil=  new JwtUtil();
	        String token = null;
	        String emailid = null;
	        
	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            token = authorizationHeader.substring(7);
	            emailid = jwtUtil.extractUsername(token);
	            System.out.println("Got emaiid  "+emailid);
	        }
	        

	        if (emailid != null )
	        {//&& SecurityContextHolder.getContext().getAuthentication() == null) {
	        	
    	        AbstractPersistence persistence;
    	        persistence = AbstractPersistence.get();
    			persistence.evictAllCached();
    			persistence.begin();

    			User user = CORE.getRepository().retrieveUser("setup");
    			persistence.setUser(user);
	        	
	        	
	        	
    			UserLogin userDetails =  getUser(emailid);
	            System.out.println("Got Object also  "+userDetails);


				
	            
	        	
	            if (jwtUtil.validateToken(token, userDetails)) {
	            	System.out.println("Token validated");
//	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//	                usernamePasswordAuthenticationToken
//	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
//	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

	    			chain.doFilter(httpRequest, httpResponse);

	            	

	            }
	            else {
	    	        System.out.println("Invalid Token");
	    	        error(null, httpResponse, HttpServletResponse.SC_UNAUTHORIZED, realm, "Token expired");  
	            	
	            }
	        }
	        
	    }	
			
			
		
		//if request is from skyve to skyve
		else if(httpRequest.getHeader("Authorization").startsWith("Basic")) {
			
			// check the request is authenticated
			final String authorization = httpRequest.getHeader("Authorization");
			if ((authorization == null) || (! authorization.startsWith("Basic"))) {
				error(null, httpResponse, HttpServletResponse.SC_UNAUTHORIZED, realm, "No credentials");
				return;
			}
			
			// Authorization: Basic base64credentials
			final String base64Credentials = authorization.substring("Basic".length()).trim();
			String credentials = new String(Base64.getMimeDecoder().decode(base64Credentials), Util.UTF8);

			// credentials = username:password or customer/username:password
			final String[] values = credentials.split(":", 2);
			final String username = UtilImpl.processStringValue(values[0]);
			final String password = UtilImpl.processStringValue(values[1]);

			if ((username == null) || (password == null)) {
				error(null, httpResponse, HttpServletResponse.SC_UNAUTHORIZED, realm, "Unable to authenticate with the provided credentials");
				return;
			}
			
			if (UtilImpl.COMMAND_TRACE) UtilImpl.LOGGER.info(String.format("Basic Auth for username: %s URI: %s", username, httpRequest.getRequestURI()));

			AbstractPersistence persistence = null;
			try {
				try {
					persistence = AbstractPersistence.get();
					persistence.evictAllCached();
					persistence.begin();

					User user = CORE.getRepository().retrieveUser(username);
					if (user != null) {
						persistence.setUser(user);
						validateUserCredentials(persistence, username, password);
						chain.doFilter(httpRequest, httpResponse);
					}
					else {
						error(persistence, httpResponse, HttpServletResponse.SC_FORBIDDEN, realm, "Unable to authenticate with the provided credentials");
					}
				}
				catch (InvocationTargetException e) {
					throw e.getTargetException();
				}
			}
			catch (@SuppressWarnings("unused") SecurityException e) {
				error(persistence, httpResponse, HttpServletResponse.SC_FORBIDDEN, realm, "Unable to authenticate with the provided credentials");
			}
			catch (@SuppressWarnings("unused") MetaDataException e) {
				error(persistence, httpResponse, HttpServletResponse.SC_FORBIDDEN, realm, "Unable to authenticate with the provided credentials");
			}
			catch (Throwable t) {
				t.printStackTrace();
				UtilImpl.LOGGER.log(Level.SEVERE, t.getLocalizedMessage(), t);
				error(persistence, httpResponse, t.getLocalizedMessage());
			}
			finally {
				if (persistence != null) {
					persistence.commit(true);
				}
			}
			
			
			
			
			
		}
		
		
		
		
		
		
		
	}
	


	private static UserLogin getUser(String emailid){
        Persistence p =  null;

    	p = CORE.getPersistence();
    	User u = p.getUser();

		Customer c = u.getCustomer();

		Module m = c.getModule("user");

		Document d = m.getDocument(c, "UserLogin");

		

		DocumentQuery q = p.newDocumentQuery(d);
		System.out.println("DocumentQuery passed");

		
		q.getFilter().addEquals(UserLogin.emailidPropertyName, emailid);
		System.out.println("Got bean");
		return q.beanResult();
	}








	private static void validateUserCredentials(Persistence p, String username, String password)
	throws Exception {
		DocumentQuery q = p.newDocumentQuery(SQLMetaDataUtil.ADMIN_MODULE_NAME, SQLMetaDataUtil.USER_DOCUMENT_NAME);
		DocumentFilter f = q.getFilter();
		final String[] customerAndUser = username.split("/");
		if (customerAndUser.length == 1) {
			if (UtilImpl.CUSTOMER == null) { // multi-tenant
				throw new SecurityException("Invalid username/password", password);
			}
			f.addEquals(SQLMetaDataUtil.USER_NAME_PROPERTY_NAME, username);
		}
		else {
			if (UtilImpl.CUSTOMER == null) { // multi-tenant
				f.addEquals(Bean.CUSTOMER_NAME, customerAndUser[0]);
			}
			f.addEquals(SQLMetaDataUtil.USER_NAME_PROPERTY_NAME, customerAndUser[1]);
		}
		Bean user = q.beanResult();
		if (user == null) {
			throw new SecurityException("Invalid username/password", password);
		}
		if (! EXT.checkPassword(password, (String) BindUtil.get(user, SQLMetaDataUtil.PASSWORD_PROPERTY_NAME))) {
			throw new SecurityException("Invalid username/password", password);
		}
	}
}

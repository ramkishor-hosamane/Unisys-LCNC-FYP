package api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.skyve.CORE;
import org.skyve.EXT;
import org.skyve.content.AttachmentContent;
import org.skyve.content.ContentManager;
import org.skyve.content.MimeType;
import org.skyve.domain.Bean;
import org.skyve.domain.PersistentBean;
import org.skyve.domain.messages.NoResultsException;
import org.skyve.impl.bind.BindUtil;
import org.skyve.impl.domain.messages.SecurityException;
import org.skyve.impl.util.UtilImpl;
import org.skyve.impl.web.filter.rest.AbstractRestFilter;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.metadata.module.query.MetaDataQueryDefinition;
import org.skyve.metadata.user.User;
import org.skyve.metadata.view.model.list.DocumentQueryListModel;
import org.skyve.persistence.DocumentFilter;
import org.skyve.persistence.DocumentQuery;
import org.skyve.persistence.Persistence;
import org.skyve.util.Binder;
import org.skyve.util.JSON;
import org.skyve.util.Util;

import com.google.gson.Gson;

import modules.order.domain.OrderHeader;
import modules.order.domain.OrderItem;
import modules.product.domain.Product;
import modules.product.domain.ProductCategoryMember;
import modules.user.domain.UserLogin;

import org.json.*;

@Path("/")
@RequestScoped
public class AuthService {

	JwtUtil jwt;
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	@POST
	@Path("/authenticate")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String generateToken(String json) throws Exception {
		System.out.println(json);
		String result=null;		

		JSONObject jsonobj = new JSONObject(json);
		jwt=  new JwtUtil();
		String emailid = jsonobj.getString("emailid");
		String password = jsonobj.getString("password");
		System.out.println("emai id is "+emailid);
		System.out.println("password is "+password);
		
		UserLogin user = getUser(emailid);
		System.out.println("User object is "+user);
		
		if(user==null) {
			result = "{\"msg\":\"Invalid emailid\"}";
			return result;
			//throw new Exception("Invalid emailid");
			
		}
		
		if(!user.getPassword().equals(password)) {
			result = "{\"msg\":\"Invalid password\"}";
			return result;

			//throw new Exception("Invalid password");
		}
		
		
		String user_str = JSON.marshall(CORE.getUser().getCustomer(), user);
		
		//String result = "[{\"msg\":\"Restful example: Hello all\"}]";
		// return Response.status(200).entity(result).build();
		
		try {
			String token =jwt.generateToken(emailid);
			Date tokenexpr = jwt.extractExpiration(token);
			System.out.println("Token is "+token);
			result = "{\"msg\":\"success\",\"userid\":\""+user.getBizId()+"\",\"token\":\""+token+"\",\"token expiration\":\""+tokenexpr+"\"}";
			
			
		} catch (Exception e) {
			throw new Exception("Error generating token");
		
		}
		
		return result;
		
		
		
		
	}
	
	
	
	
	
	
	
	private static UserLogin getUser(String emailid){
        Persistence p =  null;

    	p = CORE.getPersistence();
    	User u = p.getUser();

		Customer c = u.getCustomer();

		Module m = c.getModule("user");

		Document d = m.getDocument(c, "UserLogin");

		

		DocumentQuery q = p.newDocumentQuery(d);
		//System.out.println("DocumentQuery passed");

		
		q.getFilter().addEquals(UserLogin.emailidPropertyName, emailid);
		System.out.println("Got bean");
		return q.beanResult();
	}

}

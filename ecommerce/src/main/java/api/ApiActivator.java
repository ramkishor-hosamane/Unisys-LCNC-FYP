package api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


//import org.jboss.resteasy.plugins.interceptors.CorsFilter;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@ApplicationPath("api")
public class ApiActivator extends Application {
	
//	   private Set<Object> singletons = new HashSet<Object>();
	   
   public ApiActivator(){
	   System.out.println("Rest is created");
	   
//       CorsFilter corsFilter = new CorsFilter();
//       corsFilter.getAllowedOrigins().add("/api/*");
//       corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
//       corsFilter.setCorsMaxAge(86400);
//       singletons.add(ApiService.class);
//
//       singletons.add(corsFilter);
   }
   

   
   
//   
//   @Override
//   public Set<Object> getSingletons() {
//       return singletons;
//   }

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<>();
		classes.add(ApiService.class);
		classes.add(WishlistApi.class);

		return classes;
	}
}










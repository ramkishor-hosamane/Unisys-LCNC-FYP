package api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
@ApplicationPath("api")
public class ApiActivator extends Application {
   public ApiActivator(){
	   System.out.println("Rest is created");
   }

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<>();
		classes.add(ApiService.class);
		return classes;
	}
}










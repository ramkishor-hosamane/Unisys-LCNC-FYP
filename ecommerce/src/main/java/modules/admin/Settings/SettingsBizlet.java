package modules.admin.Settings;


import java.util.List;

import javax.ws.rs.core.MediaType;

import org.skyve.CORE;
import org.skyve.domain.Bean;
import org.skyve.impl.domain.messages.SecurityException;
import org.skyve.metadata.controller.ImplicitActionName;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.document.Bizlet;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.metadata.user.User;
import org.skyve.persistence.DocumentFilter;
import org.skyve.persistence.DocumentQuery;
import org.skyve.persistence.Persistence;
import org.skyve.web.WebContext;

import modules.admin.domain.JobSchedule;
import modules.admin.domain.Settings;
public class SettingsBizlet extends Bizlet<Settings> {
	
	@Override
	public Settings newInstance(Settings bean) throws Exception {
		

		Persistence p = null;
		p = CORE.getPersistence();

			User u = p.getUser();
//			System.out.println("User passed");

			Customer c = u.getCustomer();
//			System.out.println("Customer passed");

			Module m = c.getModule("admin");
//			System.out.println("Module passed");

			Document d = m.getDocument(c, "Settings");
//			System.out.println("Document passed");
			
			if (! u.canReadDocument(d)) {
				throw new SecurityException("read this data", u.getName());
			}
			
			//Perform sql query on Product Document
			// select * from Product
	    	DocumentQuery q = p.newDocumentQuery(d);
//			System.out.println("DocumentQuery passed");

	    	System.out.println("Came here");
    		List<Settings> mybeans  = q.beanResults();
    		//bean.setBizId("setsettings");
    		if(mybeans.size()==0) {
    			bean.setWishlist(true);
    		}
    		else {
    			bean.setWishlist(mybeans.get(mybeans.size()-1).getWishlist());
    			p.delete(mybeans.get(mybeans.size()-1));
    		}

		return bean;
	}
	
}

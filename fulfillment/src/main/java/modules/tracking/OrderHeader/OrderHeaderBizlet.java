package modules.tracking.OrderHeader;

import java.net.http.HttpResponse;
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

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import modules.admin.domain.JobSchedule;
import modules.tracking.domain.OrderHeader;

public class OrderHeaderBizlet extends Bizlet<OrderHeader> {

	@Override
	public OrderHeader newInstance(OrderHeader bean) throws Exception {

		return bean;
	}

	public static void updateOrderStatus(OrderHeader bean, String status) {

		Persistence p = CORE.getPersistence();
		User u = p.getUser();
		Customer c = u.getCustomer();
		Module m = c.getModule("tracking");
		Document d = m.getDocument(c, "OrderHeader");

		bean.setOrderstatus(status);
		bean = p.save(bean);

		Unirest.setTimeouts(0, 0);
		try {
			
			  Unirest.setTimeouts(0, 0);
			    Unirest.post("http://localhost:8080/ecommerce/api/json/updateOrderStatus/" + bean.getOrderid() + "/" + status)
			      .header("Content-Type", "application/json")
			      .header("Authorization", "Basic c2V0dXA6c2V0dXA=")
			      .asString();
			
			
				} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

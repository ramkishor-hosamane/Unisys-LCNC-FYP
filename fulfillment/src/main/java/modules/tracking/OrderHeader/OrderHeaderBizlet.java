package modules.tracking.OrderHeader;


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
import modules.tracking.domain.OrderHeader;

public class OrderHeaderBizlet extends Bizlet<OrderHeader> {
	
	@Override
	public OrderHeader newInstance(OrderHeader bean) throws Exception {
		

		

		return bean;
	}
	
}

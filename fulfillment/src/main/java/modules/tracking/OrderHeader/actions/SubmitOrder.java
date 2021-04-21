package modules.tracking.OrderHeader.actions;

import java.lang.Override;

import modules.tracking.OrderHeader.OrderHeaderBizlet;
import modules.tracking.domain.OrderHeader;
import modules.tracking.domain.Picking;

import org.skyve.CORE;
import org.skyve.domain.messages.MessageSeverity;
import org.skyve.metadata.controller.ServerSideAction;
import org.skyve.metadata.controller.ServerSideActionResult;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.metadata.user.User;
import org.skyve.persistence.Persistence;
import org.skyve.web.WebContext;

public class SubmitOrder implements ServerSideAction<OrderHeader> {
	@Override
	public ServerSideActionResult<OrderHeader> execute(OrderHeader bean, WebContext webContext) {
		
		 	Persistence p = CORE.getPersistence();
		    User u = p.getUser();
		    

			Customer c = u.getCustomer();

			Module m = c.getModule("tracking");

			Document d = m.getDocument(c, "Picking");
			
			Picking pick = new Picking();
			pick.setChecked(false);
			pick.setOrderid(bean);
			pick.setBizCustomer(c.getName());
			pick.setBizUserId(u.getName());
			pick = p.save(d,pick);
			
			
			
			OrderHeaderBizlet.updateOrderStatus(bean,"ordered");
		
			webContext.message(MessageSeverity.info,"Order passed to picking successfully");
		
		
		
		
		
		
		return new ServerSideActionResult<>(bean);
	}
}

package modules.tracking.Picking.actions;


import java.lang.Override;

import modules.tracking.OrderHeader.OrderHeaderBizlet;
import modules.tracking.domain.Packing;
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

public class SubmitPicking implements ServerSideAction<Picking> {
	@Override
	public ServerSideActionResult<Picking> execute(Picking bean, WebContext webContext) {
			

		Submit(bean);
		Update(bean);
		
		webContext.message(MessageSeverity.info,"Order passed to packing successfully");		
		return new ServerSideActionResult<>(bean);
	}
	
	
	
	public void Submit(Picking bean) {
		
		
	 	Persistence p = CORE.getPersistence();
	    User u = p.getUser();
		Customer c = u.getCustomer();
		Module m = c.getModule("tracking");
		Document d = m.getDocument(c, "Packing");
		
		Packing pack = new Packing();
		pack.setChecked(false);
		pack.setOrderid(bean.getOrderid());
		pack.setBizCustomer(c.getName());
		pack.setBizUserId(u.getName());
		pack = p.save(d,pack);
		
	}


	
	public void Update(Picking bean) {
		
		
	 	Persistence p = CORE.getPersistence();
	    User u = p.getUser();
		Customer c = u.getCustomer();
		Module m = c.getModule("tracking");
		Document d = m.getDocument(c, "Picking");
		
		bean.setChecked(true);
		bean = p.save(bean);
		
		OrderHeaderBizlet.updateOrderStatus(bean.getOrderid(), "picked");
		
	}


}

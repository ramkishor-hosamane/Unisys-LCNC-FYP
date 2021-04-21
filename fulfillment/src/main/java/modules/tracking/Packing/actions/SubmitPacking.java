package modules.tracking.Packing.actions;



import java.lang.Override;

import modules.tracking.OrderHeader.OrderHeaderBizlet;
import modules.tracking.domain.Packing;
import modules.tracking.domain.Picking;
import modules.tracking.domain.Shipping;

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

public class SubmitPacking implements ServerSideAction<Packing> {
	@Override
	public ServerSideActionResult<Packing> execute(Packing bean, WebContext webContext) {
			

		Submit(bean);
		Update(bean);
		
		webContext.message(MessageSeverity.info,"Order passed to shipping successfully");		
		return new ServerSideActionResult<>(bean);
	}
	
	
	
	public void Submit(Packing bean) {
		
		
	 	Persistence p = CORE.getPersistence();
	    User u = p.getUser();
		Customer c = u.getCustomer();
		Module m = c.getModule("tracking");
		Document d = m.getDocument(c, "Shipping");
		
		Shipping ship = new Shipping();
		ship.setChecked(false);
		ship.setOrderid(bean.getOrderid());
		ship.setBizCustomer(c.getName());
		ship.setBizUserId(u.getName());
		ship = p.save(d,ship);
		
	}


	
	public void Update(Packing bean) {
		
		
	 	Persistence p = CORE.getPersistence();
	    User u = p.getUser();
		Customer c = u.getCustomer();
		Module m = c.getModule("tracking");
		Document d = m.getDocument(c, "Packing");
		
		bean.setChecked(true);
		bean = p.save(bean);
		
		OrderHeaderBizlet.updateOrderStatus(bean.getOrderid(), "packed");
		
	}


}

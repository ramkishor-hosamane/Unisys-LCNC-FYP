package modules.tracking.OrderHeader.actions;

import java.lang.Override;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import helper.OrderHelper;
import helper.OrderItemHelper;

import org.json.JSONArray;

import modules.tracking.domain.OrderHeader;
import modules.tracking.domain.OrderItem;
import net.sf.jasperreports.engine.export.ooxml.DocxBorderHelper;

import org.ehcache.config.ResourceType.Core;
import org.skyve.CORE;
import org.skyve.domain.PersistentBean;
import org.skyve.domain.messages.MessageSeverity;
import org.skyve.domain.types.DateTime;
import org.skyve.metadata.controller.ServerSideAction;
import org.skyve.metadata.controller.ServerSideActionResult;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.metadata.user.User;
import org.skyve.persistence.DocumentQuery;
import org.skyve.persistence.Persistence;
import org.skyve.util.JSON;
import org.skyve.web.WebContext;
import org.supercsv.cellprocessor.ParseInt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.nimbusds.jose.shaded.json.JSONObject;

public class GetOrders implements ServerSideAction<OrderHeader> {
	@Override
	public ServerSideActionResult<OrderHeader> execute(OrderHeader bean, WebContext webContext) throws UnirestException {

//		Unirest.setTimeouts(0, 0);
//	    HttpResponse<String> response = Unirest.get("http://localhost:8080/ecommerce/api/json/getNewOrders")
//	      .header("Content-Type", "application/json")
//	      .header("Authorization", "Basic c2V0dXA6c2V0dXA=")
//	      .asString();
//	    
//	    //System.out.println(response.getBody());
//		Persistence p = null;
//		try {
//	        Gson gson = new Gson();
//		    p = CORE.getPersistence();
//		    User u = p.getUser();
//		    
//
//			Customer c = u.getCustomer();
//
//			Module m = c.getModule("tracking");
//
//			Document d = m.getDocument(c, "OrderHeader");
//		    System.out.println("Entering");
//			//System.out.println(JSON.unmarshall(u, response.getBody()));
//
//		    String string_json = response.getBody();
//	        Type familyType = new TypeToken<ArrayList<OrderHelper>>() {}.getType();
//	        ArrayList<OrderHelper> mybeans = gson.fromJson(string_json, familyType);
//	        int count = 0;
//		    for(OrderHelper o:mybeans) {
//				DocumentQuery q = p.newDocumentQuery(d);
//				q.getFilter().addLike(OrderHeader.orderidPropertyName, o.getOrderid());
//		    	if(q.beanResults().size()==0)
//		    	{
//			    	OrderHeader oh =  SaveOrderHeaderBean(o, c, u, d, p);
//			    	oh = GetOrderItems(o,oh);
//			    	oh = p.save(d,oh);
//			    	count++;
//		    	}
//		    }
//		    
//		    if(count>0) {
//			    webContext.growl(MessageSeverity.info, "Got "+count+" Orders");
//
//		    }
//		    System.out.println("Exiting");
//
//			
//		} catch (Exception e) {
//				System.out.println("Exception"+e);
//		
//		}
//		
//	    System.out.println("Finally");
	    webContext.growl(MessageSeverity.info, "Got "+1+" Orders");
	    System.out.println("Finally");
		return new ServerSideActionResult<>(bean);
	
	}
	
	public OrderHeader GetOrderItems(OrderHelper o,OrderHeader oh) throws UnirestException, ParseException {
		
		
		Unirest.setTimeouts(0, 0);
	    HttpResponse<String> response = Unirest.get("http://localhost:8080/ecommerce/api/json/getOrderItemsByOrderHeaderForTracking/"+o.getOrderid())
	      .header("Content-Type", "application/json")
	      .header("Authorization", "Basic c2V0dXA6c2V0dXA=")
	      .asString();
	    
	    
        Gson gson = new Gson();
        Persistence p=null;
	    p = CORE.getPersistence();
	    User u = p.getUser();
	    

		Customer c = u.getCustomer();

		Module m = c.getModule("tracking");

		Document d = m.getDocument(c, "OrderItem");
		
	    System.out.println("Entering");
		//System.out.println(JSON.unmarshall(u, response.getBody()));

	    String string_json = response.getBody();
        Type familyType = new TypeToken<ArrayList<OrderItemHelper>>() {}.getType();
        ArrayList<OrderItemHelper> mybeans = gson.fromJson(string_json, familyType);
	    
	    for(OrderItemHelper oi:mybeans) {

	      OrderItem bean_saved = 	SaveOrderItemBean(oi, c, u, d, p);
	      oh.addOrdersElement(bean_saved);
	      
	      System.out.println("Order item "+oi.getProductid());
	    }
	    System.out.println("Exiting");
	    return oh;
	}

	
	
	public OrderHeader SaveOrderHeaderBean(OrderHelper o,Customer c,User u,Document d,Persistence p) throws ParseException {
		OrderHeader bean_to_save = new OrderHeader();
    	bean_to_save.setAddressid(o.getAddressid());
    	bean_to_save.setCreatedstamp(new DateTime());
    	bean_to_save.setUpdatedstamp(new DateTime());
    	bean_to_save.setGrandtotal(new Integer(o.getGrandtotal()));
    	bean_to_save.setSubtotal(new Integer(o.getSubtotal()));
    	bean_to_save.setOrderdate(new DateTime(o.getOrderdate()));
    	bean_to_save.setPaymentmethod(o.getPaymentmethod());
    	bean_to_save.setOrderid(o.getOrderid());
    	bean_to_save.setOrderstatus(o.getOrderstatus());
    	bean_to_save.setUserloginid(o.getUserloginid());
    	bean_to_save.setBizCustomer(c.getName());
    	bean_to_save.setBizUserId(u.getName());
    	//bean_to_save = p.save(d, bean_to_save);
    	return bean_to_save;
		
	}
	
	
	
	public OrderItem SaveOrderItemBean(OrderItemHelper o, Customer c,User u,Document d,Persistence p) throws ParseException {
		OrderItem bean_to_save = new OrderItem();
		bean_to_save.setOrderitemid(o.getOrderitemid());
    	bean_to_save.setCreatedstamp(new DateTime());
    	bean_to_save.setUpdatedstamp(new DateTime());
    	bean_to_save.setProductid(o.getProductid());
    	bean_to_save.setQuantity(new Integer(o.getQuantity()));
    	bean_to_save.setUnitprice(new Integer(o.getUnitprice()));
    	bean_to_save.setBizCustomer(c.getName());
    	bean_to_save.setBizUserId(u.getName());
    	bean_to_save = p.save(d, bean_to_save);
    	return bean_to_save;
		
	}
	
}

package api;

import java.util.ArrayList;
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
import org.skyve.domain.types.DateTime;
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
import com.nimbusds.jose.shaded.json.JSONObject;

import modules.order.domain.OrderHeader;
import modules.order.domain.OrderItem;
import modules.product.domain.Product;
import modules.product.domain.ProductCategoryMember;

@Path("/")
@RequestScoped
public class OrdersApi {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	
	
	
	
	
	@GET
	@Path("/json/getOrderItemsByOrderHeader/{OrderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String retrieveOrderItemByOrderHeader(@PathParam("OrderId") String OrderId, @QueryParam("start") int start,
			@QueryParam("end") int end) {
		String result = null;

		Persistence p = null;
		try {
			response.setContentType(MediaType.APPLICATION_JSON);

			p = CORE.getPersistence();

			User u = p.getUser();

			Customer c = u.getCustomer();

			Module m = c.getModule("order");

			Document d = m.getDocument(c, "OrderItem");

			if (!u.canReadDocument(d)) {
				throw new SecurityException("read this data", u.getName());
			}

			DocumentQuery q = p.newDocumentQuery(d);
			System.out.println("DocumentQuery passed");

			
			Document d2 = m.getDocument(c, "OrderHeader");
			DocumentQuery q2 = p.newDocumentQuery(d2);
			q2.getFilter().addLike("orderid", OrderId);
			List<OrderHeader> oh = q2.beanResults();
			
			System.out.println(oh.get(0));
			
			
			
			q.setFirstResult(start);
			q.setMaxResults(end - start - 1);

			System.out.println("Came here");
			DocumentFilter f = q.newDocumentFilter();
			
			//q.getFilter().addEquals(, OrderId);
			q.getFilter().addIn("orderid",oh.get(0));
			//q.getFilter().addLike("orderid",oh.get(0));
			List<OrderItem> order_beans = q.beanResults();
			result = JSON.marshall(CORE.getUser().getCustomer(), order_beans);

		} catch (Throwable t) {
			System.out.println("Error" + t);
			t.printStackTrace();
			AbstractRestFilter.error(p, response, t.getLocalizedMessage());
		}

		return result;
	}
	
	
	
	
	
	@GET
	@Path("/json/getNewOrders")
	@Produces(MediaType.APPLICATION_JSON)
	public String retrieveNewOrders(@QueryParam("start") int start,
			@QueryParam("end") int end) {
		String result = null;

		Persistence p = null;
		try {
			response.setContentType(MediaType.APPLICATION_JSON);

			p = CORE.getPersistence();

			User u = p.getUser();

			Customer c = u.getCustomer();

			Module m = c.getModule("order");

			Document d = m.getDocument(c, "OrderHeader");

			if (!u.canReadDocument(d)) {
				throw new SecurityException("read this data", u.getName());
			}

			DocumentQuery q = p.newDocumentQuery(d);
			System.out.println("DocumentQuery passed");

			q.setFirstResult(start);
			q.setMaxResults(end - start - 1);

			System.out.println("Came here");
			DocumentFilter f = q.newDocumentFilter();
			
			q.getFilter().addIn(OrderHeader.orderstatusPropertyName,"created");
			//q.getFilter().addLike("orderid",oh.get(0));
			List<OrderHeader> order_beans = q.beanResults();
			ArrayList res = new ArrayList();
			JSONObject json;
			for(OrderHeader b:order_beans) {
				json = new JSONObject();
//				json.put("bizId", b.getBizId());
//				json.put("bizCustomer", "skyve");
//				json.put("bizDataGroupId", b.getBizDataGroupId());
//				json.put("bizDocument", b.getBizDocument());
//				json.put("module", "tracking");
				json.put("orderid", b.getOrderid());
				json.put("addressid", b.getAddressid().getAddressid());
				json.put("orderstatus", b.getOrderstatus());
				json.put("paymentmethod", b.getPaymentmethod());
				json.put("grandtotal", b.getGrandtotal());
				json.put("subtotal", b.getSubtotal());
				json.put("orderdate", b.getOrderdate());
				json.put("userloginid", b.getUserloginid().getUserloginid());
				json.put("createdstamp", b.getCreatedstamp());
				json.put("updatedstamp", b.getUpdatedstamp());
				
				
				res.add(json);

			}
			
			result = JSON.marshall(CORE.getUser().getCustomer(), res);

		} catch (Throwable t) {
			System.out.println("Error" + t);
			t.printStackTrace();
			AbstractRestFilter.error(p, response, t.getLocalizedMessage());
		}

		return result;
	}
	
	
	
	
	
	
	
	@GET
	@Path("/json/getOrderItemsByOrderHeaderForTracking/{OrderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrderItemsByOrderHeaderForTracking(@PathParam("OrderId") String OrderId, @QueryParam("start") int start,
			@QueryParam("end") int end) {
		String result = null;

		Persistence p = null;
		try {
			response.setContentType(MediaType.APPLICATION_JSON);

			p = CORE.getPersistence();

			User u = p.getUser();

			Customer c = u.getCustomer();

			Module m = c.getModule("order");

			Document d = m.getDocument(c, "OrderItem");

			if (!u.canReadDocument(d)) {
				throw new SecurityException("read this data", u.getName());
			}

			DocumentQuery q = p.newDocumentQuery(d);
			System.out.println("DocumentQuery passed");

			
			Document d2 = m.getDocument(c, "OrderHeader");
			DocumentQuery q2 = p.newDocumentQuery(d2);
			q2.getFilter().addLike("orderid", OrderId);
			List<OrderHeader> oh = q2.beanResults();
			
			System.out.println(oh.get(0));
			
			
			
			q.setFirstResult(start);
			q.setMaxResults(end - start - 1);

			System.out.println("Came here");
			DocumentFilter f = q.newDocumentFilter();
			
			//q.getFilter().addEquals(, OrderId);
			q.getFilter().addIn("orderid",oh.get(0));
			//q.getFilter().addLike("orderid",oh.get(0));
			List<OrderItem> order_beans = q.beanResults();
			
			
			ArrayList res = new ArrayList();
			JSONObject json;
			for(OrderItem b:order_beans) {
				json = new JSONObject();
//				json.put("bizId", b.getBizId());
//				json.put("bizCustomer", "skyve");
//				json.put("bizDataGroupId", b.getBizDataGroupId());
//				json.put("bizDocument", b.getBizDocument());
//				json.put("module", "tracking");
				json.put("orderid", b.getOrderid().getOrderid());
				json.put("orderitemid", b.getOrderitemid());
				json.put("productid", b.getProductid().getProductid());
				json.put("quantity", b.getQuantity());
				json.put("unitprice", b.getUnitprice());
				json.put("createdstamp", b.getCreatedstamp());
				json.put("updatedstamp", b.getUpdatedstamp());
				res.add(json);

			}

			result = JSON.marshall(CORE.getUser().getCustomer(), res);

		} catch (Throwable t) {
			System.out.println("Error" + t);
			t.printStackTrace();
			AbstractRestFilter.error(p, response, t.getLocalizedMessage());
		}

		return result;
	}
	
	

	@POST
	@Path("/json/updateOrderStatus/{OrderId}/{OrderStatus}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateOrderStatus(@PathParam("OrderId") String OrderId,@PathParam("OrderStatus") String OrderStatus, @QueryParam("start") int start,
			@QueryParam("end") int end) {
		String result = null;

		Persistence p = null;
		try {
			response.setContentType(MediaType.APPLICATION_JSON);

			p = CORE.getPersistence();

			User u = p.getUser();

			Customer c = u.getCustomer();

			Module m = c.getModule("order");

			Document d = m.getDocument(c, "OrderHeader");

			if (!u.canReadDocument(d)) {
				throw new SecurityException("read this data", u.getName());
			}

			DocumentQuery q = p.newDocumentQuery(d);
			System.out.println("DocumentQuery passed");

	
			
			q.setFirstResult(start);
			q.setMaxResults(end - start - 1);

			System.out.println("Came here");
			q.getFilter().addEquals(OrderHeader.orderidPropertyName, OrderId);
			OrderHeader o = q.beanResult();
			o.setOrderstatus(OrderStatus);			
			o.setUpdatedstamp(new DateTime());

			o = p.save(o);

			result = JSON.marshall(CORE.getUser().getCustomer(), o);

		} catch (Throwable t) {
			System.out.println("Error" + t);
			t.printStackTrace();
			AbstractRestFilter.error(p, response, t.getLocalizedMessage());
		}

		return result;
	}
	

	
	
	
	
	


}

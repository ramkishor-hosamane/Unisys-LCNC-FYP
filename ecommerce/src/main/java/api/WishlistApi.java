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

import modules.order.domain.OrderHeader;
import modules.order.domain.OrderItem;
import modules.product.domain.Product;
import modules.product.domain.ProductCategoryMember;
import modules.user.domain.UserLogin;
import modules.wishlist.domain.ShoppingListItem;

@Path("/")
@RequestScoped
public class WishlistApi {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;





	@GET
	@Path("/json/getWishlistItemsByUser/{UserId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String retrieveOrderItemByOrderHeader(@PathParam("UserId") String UserId, @QueryParam("start") int start,
			@QueryParam("end") int end) {
		String result = null;

		Persistence p = null;
		try {
			response.setContentType(MediaType.APPLICATION_JSON);

			p = CORE.getPersistence();

			User u = p.getUser();

			Customer c = u.getCustomer();

			Module m = c.getModule("wishlist");

			Document d = m.getDocument(c, "ShoppingListItem");

			if (!u.canReadDocument(d)) {
				throw new SecurityException("read this data", u.getName());
			}

			DocumentQuery q = p.newDocumentQuery(d);


			Module m2 = c.getModule("user");
			
			Document d2 = m.getDocument(c, "UserLogin");
			DocumentQuery q2 = p.newDocumentQuery(d2);
			q2.getFilter().addLike("userloginid", UserId);

			List<UserLogin> oh = q2.beanResults();
			System.out.println(oh.get(0));
			
			
			
			q.setFirstResult(start);
			q.setMaxResults(end - start - 1);

			System.out.println("Came here");
			DocumentFilter f = q.newDocumentFilter();
			
			//q.getFilter().addEquals(, OrderId);
			q.getFilter().addIn("userloginid",oh.get(0));
			//q.getFilter().addLike("orderid",oh.get(0));
			List<ShoppingListItem> wish_beans = q.beanResults();
			result = JSON.marshall(CORE.getUser().getCustomer(), wish_beans);

		} catch (Throwable t) {
			System.out.println("Error" + t);
			t.printStackTrace();
			AbstractRestFilter.error(p, response, t.getLocalizedMessage());
		}

		return result;
	}
	
}

	

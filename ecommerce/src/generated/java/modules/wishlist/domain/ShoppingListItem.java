package modules.wishlist.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import modules.product.domain.Category;
import modules.product.domain.Product;
import modules.user.domain.UserLogin;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * ShoppingListItem
 * 
 * @navhas n productid 1 Product
 * @navhas n categoryid 1 Category
 * @navhas n userlogin 1 UserLogin
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class ShoppingListItem extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "wishlist";
	/** @hidden */
	public static final String DOCUMENT_NAME = "ShoppingListItem";

	/** @hidden */
	public static final String userloginPropertyName = "userlogin";
	/** @hidden */
	public static final String categoryidPropertyName = "categoryid";
	/** @hidden */
	public static final String productidPropertyName = "productid";

	/**
	 * User Login
	 **/
	private UserLogin userlogin = null;
	/**
	 * Category id
	 **/
	private Category categoryid = null;
	/**
	 * Product id
	 **/
	private Product productid = null;

	@Override
	@XmlTransient
	public String getBizModule() {
		return ShoppingListItem.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return ShoppingListItem.DOCUMENT_NAME;
	}

	public static ShoppingListItem newInstance() {
		try {
			return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new DomainException(e);
		}
	}

	@Override
	@XmlTransient
	public String getBizKey() {
		try {
			return org.skyve.util.Binder.formatMessage("{categoryid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof ShoppingListItem) && 
					this.getBizId().equals(((ShoppingListItem) o).getBizId()));
	}

	/**
	 * {@link #userlogin} accessor.
	 * @return	The value.
	 **/
	public UserLogin getUserlogin() {
		return userlogin;
	}

	/**
	 * {@link #userlogin} mutator.
	 * @param userlogin	The new value.
	 **/
	@XmlElement
	public void setUserlogin(UserLogin userlogin) {
		if (this.userlogin != userlogin) {
			preset(userloginPropertyName, userlogin);
			this.userlogin = userlogin;
		}
	}

	/**
	 * {@link #categoryid} accessor.
	 * @return	The value.
	 **/
	public Category getCategoryid() {
		return categoryid;
	}

	/**
	 * {@link #categoryid} mutator.
	 * @param categoryid	The new value.
	 **/
	@XmlElement
	public void setCategoryid(Category categoryid) {
		if (this.categoryid != categoryid) {
			preset(categoryidPropertyName, categoryid);
			this.categoryid = categoryid;
		}
	}

	/**
	 * {@link #productid} accessor.
	 * @return	The value.
	 **/
	public Product getProductid() {
		return productid;
	}

	/**
	 * {@link #productid} mutator.
	 * @param productid	The new value.
	 **/
	@XmlElement
	public void setProductid(Product productid) {
		if (this.productid != productid) {
			preset(productidPropertyName, productid);
			this.productid = productid;
		}
	}
}

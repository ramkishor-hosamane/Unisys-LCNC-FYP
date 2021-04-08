package modules.wishlist.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import modules.user.domain.UserLogin;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * ShoppingList
 * 
 * @navhas n userlogin 1 UserLogin
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class ShoppingList extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "wishlist";
	/** @hidden */
	public static final String DOCUMENT_NAME = "ShoppingList";

	/** @hidden */
	public static final String listidPropertyName = "listid";
	/** @hidden */
	public static final String userloginPropertyName = "userlogin";
	/** @hidden */
	public static final String typePropertyName = "type";

	/**
	 * List Id
	 **/
	private String listid;
	/**
	 * User Login
	 **/
	private UserLogin userlogin = null;
	/**
	 * Type
	 **/
	private String type;

	@Override
	@XmlTransient
	public String getBizModule() {
		return ShoppingList.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return ShoppingList.DOCUMENT_NAME;
	}

	public static ShoppingList newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{listid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof ShoppingList) && 
					this.getBizId().equals(((ShoppingList) o).getBizId()));
	}

	/**
	 * {@link #listid} accessor.
	 * @return	The value.
	 **/
	public String getListid() {
		return listid;
	}

	/**
	 * {@link #listid} mutator.
	 * @param listid	The new value.
	 **/
	@XmlElement
	public void setListid(String listid) {
		preset(listidPropertyName, listid);
		this.listid = listid;
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
	 * {@link #type} accessor.
	 * @return	The value.
	 **/
	public String getType() {
		return type;
	}

	/**
	 * {@link #type} mutator.
	 * @param type	The new value.
	 **/
	@XmlElement
	public void setType(String type) {
		preset(typePropertyName, type);
		this.type = type;
	}
}

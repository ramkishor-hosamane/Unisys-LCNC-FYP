package modules.cart.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import modules.user.domain.UserAddress;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.Decimal2;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.Decimal2Mapper;

/**
 * ShoppingCart
 * 
 * @navhas n addressid 1 UserAddress
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class ShoppingCart extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "cart";
	/** @hidden */
	public static final String DOCUMENT_NAME = "ShoppingCart";

	/** @hidden */
	public static final String cartidPropertyName = "cartid";
	/** @hidden */
	public static final String addressidPropertyName = "addressid";
	/** @hidden */
	public static final String subtotalPropertyName = "subtotal";
	/** @hidden */
	public static final String grandtotalPropertyName = "grandtotal";

	/**
	 * Cart Id
	 **/
	private String cartid;
	/**
	 * Address Id
	 **/
	private UserAddress addressid = null;
	/**
	 * Sub Total
	 **/
	private Decimal2 subtotal;
	/**
	 * Grand Total
	 **/
	private Decimal2 grandtotal;

	@Override
	@XmlTransient
	public String getBizModule() {
		return ShoppingCart.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return ShoppingCart.DOCUMENT_NAME;
	}

	public static ShoppingCart newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{cartid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof ShoppingCart) && 
					this.getBizId().equals(((ShoppingCart) o).getBizId()));
	}

	/**
	 * {@link #cartid} accessor.
	 * @return	The value.
	 **/
	public String getCartid() {
		return cartid;
	}

	/**
	 * {@link #cartid} mutator.
	 * @param cartid	The new value.
	 **/
	@XmlElement
	public void setCartid(String cartid) {
		preset(cartidPropertyName, cartid);
		this.cartid = cartid;
	}

	/**
	 * {@link #addressid} accessor.
	 * @return	The value.
	 **/
	public UserAddress getAddressid() {
		return addressid;
	}

	/**
	 * {@link #addressid} mutator.
	 * @param addressid	The new value.
	 **/
	@XmlElement
	public void setAddressid(UserAddress addressid) {
		if (this.addressid != addressid) {
			preset(addressidPropertyName, addressid);
			this.addressid = addressid;
		}
	}

	/**
	 * {@link #subtotal} accessor.
	 * @return	The value.
	 **/
	public Decimal2 getSubtotal() {
		return subtotal;
	}

	/**
	 * {@link #subtotal} mutator.
	 * @param subtotal	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal2Mapper.class)
	@XmlElement
	public void setSubtotal(Decimal2 subtotal) {
		preset(subtotalPropertyName, subtotal);
		this.subtotal = subtotal;
	}

	/**
	 * {@link #grandtotal} accessor.
	 * @return	The value.
	 **/
	public Decimal2 getGrandtotal() {
		return grandtotal;
	}

	/**
	 * {@link #grandtotal} mutator.
	 * @param grandtotal	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal2Mapper.class)
	@XmlElement
	public void setGrandtotal(Decimal2 grandtotal) {
		preset(grandtotalPropertyName, grandtotal);
		this.grandtotal = grandtotal;
	}
}

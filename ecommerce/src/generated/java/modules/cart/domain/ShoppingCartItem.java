package modules.cart.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import modules.product.domain.Product;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * ShoppingCartItem
 * 
 * @navhas n productid 1 Product
 * @navhas n cartid 1 ShoppingCart
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class ShoppingCartItem extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "cart";
	/** @hidden */
	public static final String DOCUMENT_NAME = "ShoppingCartItem";

	/** @hidden */
	public static final String cartitemidPropertyName = "cartitemid";
	/** @hidden */
	public static final String cartidPropertyName = "cartid";
	/** @hidden */
	public static final String productidPropertyName = "productid";
	/** @hidden */
	public static final String quantityPropertyName = "quantity";
	/** @hidden */
	public static final String pricePropertyName = "price";

	/**
	 * Cart Item Id
	 **/
	private String cartitemid;
	/**
	 * Cart Id
	 **/
	private ShoppingCart cartid = null;
	/**
	 * Product Id
	 **/
	private Product productid = null;
	/**
	 * Quantity
	 **/
	private Integer quantity;
	/**
	 * Price
	 **/
	private Integer price;

	@Override
	@XmlTransient
	public String getBizModule() {
		return ShoppingCartItem.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return ShoppingCartItem.DOCUMENT_NAME;
	}

	public static ShoppingCartItem newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{cartitemid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof ShoppingCartItem) && 
					this.getBizId().equals(((ShoppingCartItem) o).getBizId()));
	}

	/**
	 * {@link #cartitemid} accessor.
	 * @return	The value.
	 **/
	public String getCartitemid() {
		return cartitemid;
	}

	/**
	 * {@link #cartitemid} mutator.
	 * @param cartitemid	The new value.
	 **/
	@XmlElement
	public void setCartitemid(String cartitemid) {
		preset(cartitemidPropertyName, cartitemid);
		this.cartitemid = cartitemid;
	}

	/**
	 * {@link #cartid} accessor.
	 * @return	The value.
	 **/
	public ShoppingCart getCartid() {
		return cartid;
	}

	/**
	 * {@link #cartid} mutator.
	 * @param cartid	The new value.
	 **/
	@XmlElement
	public void setCartid(ShoppingCart cartid) {
		if (this.cartid != cartid) {
			preset(cartidPropertyName, cartid);
			this.cartid = cartid;
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

	/**
	 * {@link #quantity} accessor.
	 * @return	The value.
	 **/
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * {@link #quantity} mutator.
	 * @param quantity	The new value.
	 **/
	@XmlElement
	public void setQuantity(Integer quantity) {
		preset(quantityPropertyName, quantity);
		this.quantity = quantity;
	}

	/**
	 * {@link #price} accessor.
	 * @return	The value.
	 **/
	public Integer getPrice() {
		return price;
	}

	/**
	 * {@link #price} mutator.
	 * @param price	The new value.
	 **/
	@XmlElement
	public void setPrice(Integer price) {
		preset(pricePropertyName, price);
		this.price = price;
	}
}

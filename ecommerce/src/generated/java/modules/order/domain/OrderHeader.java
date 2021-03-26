package modules.order.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import modules.user.domain.Address;
import modules.user.domain.UserAddress;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateTime;
import org.skyve.domain.types.Decimal10;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.DateTimeMapper;
import org.skyve.impl.domain.types.jaxb.Decimal10Mapper;

/**
 * OrderHeader
 * 
 * @navhas n userloginid 1 UserAddress
 * @navhas n addressid 1 Address
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class OrderHeader extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "order";
	/** @hidden */
	public static final String DOCUMENT_NAME = "OrderHeader";

	/** @hidden */
	public static final String orderidPropertyName = "orderid";
	/** @hidden */
	public static final String userloginidPropertyName = "userloginid";
	/** @hidden */
	public static final String subtotalPropertyName = "subtotal";
	/** @hidden */
	public static final String grandtotalPropertyName = "grandtotal";
	/** @hidden */
	public static final String addressidPropertyName = "addressid";
	/** @hidden */
	public static final String paymentmethodPropertyName = "paymentmethod";
	/** @hidden */
	public static final String createdstampPropertyName = "createdstamp";
	/** @hidden */
	public static final String updatedstampPropertyName = "updatedstamp";

	/**
	 * Order Id
	 **/
	private Decimal10 orderid;
	/**
	 * User Login Id
	 **/
	private UserAddress userloginid = null;
	/**
	 * Sub Total
	 **/
	private Decimal10 subtotal;
	/**
	 * Grand Total
	 **/
	private Decimal10 grandtotal;
	/**
	 * Address Id
	 **/
	private Address addressid = null;
	/**
	 * Payment Method
	 **/
	private String paymentmethod;
	/**
	 * Created timestamp
	 **/
	private DateTime createdstamp;
	/**
	 * Updated timestamp
	 **/
	private DateTime updatedstamp;

	@Override
	@XmlTransient
	public String getBizModule() {
		return OrderHeader.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return OrderHeader.DOCUMENT_NAME;
	}

	public static OrderHeader newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{orderid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof OrderHeader) && 
					this.getBizId().equals(((OrderHeader) o).getBizId()));
	}

	/**
	 * {@link #orderid} accessor.
	 * @return	The value.
	 **/
	public Decimal10 getOrderid() {
		return orderid;
	}

	/**
	 * {@link #orderid} mutator.
	 * @param orderid	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal10Mapper.class)
	@XmlElement
	public void setOrderid(Decimal10 orderid) {
		preset(orderidPropertyName, orderid);
		this.orderid = orderid;
	}

	/**
	 * {@link #userloginid} accessor.
	 * @return	The value.
	 **/
	public UserAddress getUserloginid() {
		return userloginid;
	}

	/**
	 * {@link #userloginid} mutator.
	 * @param userloginid	The new value.
	 **/
	@XmlElement
	public void setUserloginid(UserAddress userloginid) {
		if (this.userloginid != userloginid) {
			preset(userloginidPropertyName, userloginid);
			this.userloginid = userloginid;
		}
	}

	/**
	 * {@link #subtotal} accessor.
	 * @return	The value.
	 **/
	public Decimal10 getSubtotal() {
		return subtotal;
	}

	/**
	 * {@link #subtotal} mutator.
	 * @param subtotal	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal10Mapper.class)
	@XmlElement
	public void setSubtotal(Decimal10 subtotal) {
		preset(subtotalPropertyName, subtotal);
		this.subtotal = subtotal;
	}

	/**
	 * {@link #grandtotal} accessor.
	 * @return	The value.
	 **/
	public Decimal10 getGrandtotal() {
		return grandtotal;
	}

	/**
	 * {@link #grandtotal} mutator.
	 * @param grandtotal	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal10Mapper.class)
	@XmlElement
	public void setGrandtotal(Decimal10 grandtotal) {
		preset(grandtotalPropertyName, grandtotal);
		this.grandtotal = grandtotal;
	}

	/**
	 * {@link #addressid} accessor.
	 * @return	The value.
	 **/
	public Address getAddressid() {
		return addressid;
	}

	/**
	 * {@link #addressid} mutator.
	 * @param addressid	The new value.
	 **/
	@XmlElement
	public void setAddressid(Address addressid) {
		if (this.addressid != addressid) {
			preset(addressidPropertyName, addressid);
			this.addressid = addressid;
		}
	}

	/**
	 * {@link #paymentmethod} accessor.
	 * @return	The value.
	 **/
	public String getPaymentmethod() {
		return paymentmethod;
	}

	/**
	 * {@link #paymentmethod} mutator.
	 * @param paymentmethod	The new value.
	 **/
	@XmlElement
	public void setPaymentmethod(String paymentmethod) {
		preset(paymentmethodPropertyName, paymentmethod);
		this.paymentmethod = paymentmethod;
	}

	/**
	 * {@link #createdstamp} accessor.
	 * @return	The value.
	 **/
	public DateTime getCreatedstamp() {
		return createdstamp;
	}

	/**
	 * {@link #createdstamp} mutator.
	 * @param createdstamp	The new value.
	 **/
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DateTimeMapper.class)
	@XmlElement
	public void setCreatedstamp(DateTime createdstamp) {
		preset(createdstampPropertyName, createdstamp);
		this.createdstamp = createdstamp;
	}

	/**
	 * {@link #updatedstamp} accessor.
	 * @return	The value.
	 **/
	public DateTime getUpdatedstamp() {
		return updatedstamp;
	}

	/**
	 * {@link #updatedstamp} mutator.
	 * @param updatedstamp	The new value.
	 **/
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DateTimeMapper.class)
	@XmlElement
	public void setUpdatedstamp(DateTime updatedstamp) {
		preset(updatedstampPropertyName, updatedstamp);
		this.updatedstamp = updatedstamp;
	}
}

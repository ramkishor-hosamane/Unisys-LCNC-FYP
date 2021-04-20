package modules.tracking.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateTime;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.ChangeTrackingArrayList;
import org.skyve.impl.domain.types.jaxb.DateTimeMapper;

/**
 * OrderHeader
 * 
 * @navhas n orders 0..n OrderItem
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
	public static final String MODULE_NAME = "tracking";

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
	public static final String orderstatusPropertyName = "orderstatus";

	/** @hidden */
	public static final String orderdatePropertyName = "orderdate";

	/** @hidden */
	public static final String createdstampPropertyName = "createdstamp";

	/** @hidden */
	public static final String updatedstampPropertyName = "updatedstamp";

	/** @hidden */
	public static final String ordersPropertyName = "orders";

	/**
	 * Order Id
	 **/
	private String orderid;

	/**
	 * User Login Id
	 **/
	private String userloginid;

	/**
	 * Sub Total
	 **/
	private Integer subtotal;

	/**
	 * Grand Total
	 **/
	private Integer grandtotal;

	/**
	 * Address Id
	 **/
	private String addressid;

	/**
	 * Payment Method
	 **/
	private String paymentmethod;

	/**
	 * Order Status
	 **/
	private String orderstatus;

	/**
	 * Order Date
	 **/
	private DateTime orderdate;

	/**
	 * Created timestamp
	 **/
	private DateTime createdstamp;

	/**
	 * Updated timestamp
	 **/
	private DateTime updatedstamp;

	/**
	 * OrderItem
	 **/
	private List<OrderItem> orders = new ChangeTrackingArrayList<>("orders", this);

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
	public String getOrderid() {
		return orderid;
	}

	/**
	 * {@link #orderid} mutator.
	 * @param orderid	The new value.
	 **/
	@XmlElement
	public void setOrderid(String orderid) {
		preset(orderidPropertyName, orderid);
		this.orderid = orderid;
	}

	/**
	 * {@link #userloginid} accessor.
	 * @return	The value.
	 **/
	public String getUserloginid() {
		return userloginid;
	}

	/**
	 * {@link #userloginid} mutator.
	 * @param userloginid	The new value.
	 **/
	@XmlElement
	public void setUserloginid(String userloginid) {
		preset(userloginidPropertyName, userloginid);
		this.userloginid = userloginid;
	}

	/**
	 * {@link #subtotal} accessor.
	 * @return	The value.
	 **/
	public Integer getSubtotal() {
		return subtotal;
	}

	/**
	 * {@link #subtotal} mutator.
	 * @param subtotal	The new value.
	 **/
	@XmlElement
	public void setSubtotal(Integer subtotal) {
		preset(subtotalPropertyName, subtotal);
		this.subtotal = subtotal;
	}

	/**
	 * {@link #grandtotal} accessor.
	 * @return	The value.
	 **/
	public Integer getGrandtotal() {
		return grandtotal;
	}

	/**
	 * {@link #grandtotal} mutator.
	 * @param grandtotal	The new value.
	 **/
	@XmlElement
	public void setGrandtotal(Integer grandtotal) {
		preset(grandtotalPropertyName, grandtotal);
		this.grandtotal = grandtotal;
	}

	/**
	 * {@link #addressid} accessor.
	 * @return	The value.
	 **/
	public String getAddressid() {
		return addressid;
	}

	/**
	 * {@link #addressid} mutator.
	 * @param addressid	The new value.
	 **/
	@XmlElement
	public void setAddressid(String addressid) {
		preset(addressidPropertyName, addressid);
		this.addressid = addressid;
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
	 * {@link #orderstatus} accessor.
	 * @return	The value.
	 **/
	public String getOrderstatus() {
		return orderstatus;
	}

	/**
	 * {@link #orderstatus} mutator.
	 * @param orderstatus	The new value.
	 **/
	@XmlElement
	public void setOrderstatus(String orderstatus) {
		preset(orderstatusPropertyName, orderstatus);
		this.orderstatus = orderstatus;
	}

	/**
	 * {@link #orderdate} accessor.
	 * @return	The value.
	 **/
	public DateTime getOrderdate() {
		return orderdate;
	}

	/**
	 * {@link #orderdate} mutator.
	 * @param orderdate	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DateTimeMapper.class)
	public void setOrderdate(DateTime orderdate) {
		preset(orderdatePropertyName, orderdate);
		this.orderdate = orderdate;
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
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DateTimeMapper.class)
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
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	@XmlJavaTypeAdapter(DateTimeMapper.class)
	public void setUpdatedstamp(DateTime updatedstamp) {
		preset(updatedstampPropertyName, updatedstamp);
		this.updatedstamp = updatedstamp;
	}

	/**
	 * {@link #orders} accessor.
	 * @return	The value.
	 **/
	@XmlElement
	public List<OrderItem> getOrders() {
		return orders;
	}

	/**
	 * {@link #orders} accessor.
	 * @param bizId	The bizId of the element in the list.
	 * @return	The value of the element in the list.
	 **/
	public OrderItem getOrdersElementById(String bizId) {
		return getElementById(orders, bizId);
	}

	/**
	 * {@link #orders} mutator.
	 * @param bizId	The bizId of the element in the list.
	 * @param element	The new value of the element in the list.
	 **/
	public void setOrdersElementById(String bizId, OrderItem element) {
		setElementById(orders, element);
	}

	/**
	 * {@link #orders} add.
	 * @param element	The element to add.
	 **/
	public boolean addOrdersElement(OrderItem element) {
		return orders.add(element);
	}

	/**
	 * {@link #orders} add.
	 * @param index	The index in the list to add the element to.
	 * @param element	The element to add.
	 **/
	public void addOrdersElement(int index, OrderItem element) {
		orders.add(index, element);
	}

	/**
	 * {@link #orders} remove.
	 * @param element	The element to remove.
	 **/
	public boolean removeOrdersElement(OrderItem element) {
		return orders.remove(element);
	}

	/**
	 * {@link #orders} remove.
	 * @param index	The index in the list to remove the element from.
	 **/
	public OrderItem removeOrdersElement(int index) {
		return orders.remove(index);
	}

	/**
	 * Created
	 *
	 * @return The condition
	 */
	@XmlTransient
	@Override
	public boolean isCreated() {
		return (isPersisted());
	}

	/**
	 * {@link #isCreated} negation.
	 *
	 * @return The negated condition
	 */
	@Override
	public boolean isNotCreated() {
		return (! isCreated());
	}
}

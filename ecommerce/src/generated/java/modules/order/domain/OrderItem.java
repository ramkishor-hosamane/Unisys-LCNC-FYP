package modules.order.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateTime;
import org.skyve.domain.types.Decimal2;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.DateTimeMapper;
import org.skyve.impl.domain.types.jaxb.Decimal2Mapper;

/**
 * OrderItem
 * 
 * @navhas n orderid 1 OrderHeader
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class OrderItem extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "order";
	/** @hidden */
	public static final String DOCUMENT_NAME = "OrderItem";

	/** @hidden */
	public static final String orderitemidPropertyName = "orderitemid";
	/** @hidden */
	public static final String orderidPropertyName = "orderid";
	/** @hidden */
	public static final String orderitemseqnumPropertyName = "orderitemseqnum";
	/** @hidden */
	public static final String quantityPropertyName = "quantity";
	/** @hidden */
	public static final String unitpricePropertyName = "unitprice";
	/** @hidden */
	public static final String createdstampPropertyName = "createdstamp";
	/** @hidden */
	public static final String updatedstampPropertyName = "updatedstamp";

	/**
	 * Order Item Id
	 **/
	private String orderitemid;
	/**
	 * Order Id
	 **/
	private OrderHeader orderid = null;
	/**
	 * Order Item Sequence Number
	 **/
	private String orderitemseqnum;
	/**
	 * Quantity
	 **/
	private Decimal2 quantity;
	/**
	 * Unit Price
	 **/
	private Decimal2 unitprice;
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
		return OrderItem.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return OrderItem.DOCUMENT_NAME;
	}

	public static OrderItem newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{orderitemid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof OrderItem) && 
					this.getBizId().equals(((OrderItem) o).getBizId()));
	}

	/**
	 * {@link #orderitemid} accessor.
	 * @return	The value.
	 **/
	public String getOrderitemid() {
		return orderitemid;
	}

	/**
	 * {@link #orderitemid} mutator.
	 * @param orderitemid	The new value.
	 **/
	@XmlElement
	public void setOrderitemid(String orderitemid) {
		preset(orderitemidPropertyName, orderitemid);
		this.orderitemid = orderitemid;
	}

	/**
	 * {@link #orderid} accessor.
	 * @return	The value.
	 **/
	public OrderHeader getOrderid() {
		return orderid;
	}

	/**
	 * {@link #orderid} mutator.
	 * @param orderid	The new value.
	 **/
	@XmlElement
	public void setOrderid(OrderHeader orderid) {
		if (this.orderid != orderid) {
			preset(orderidPropertyName, orderid);
			this.orderid = orderid;
		}
	}

	/**
	 * {@link #orderitemseqnum} accessor.
	 * @return	The value.
	 **/
	public String getOrderitemseqnum() {
		return orderitemseqnum;
	}

	/**
	 * {@link #orderitemseqnum} mutator.
	 * @param orderitemseqnum	The new value.
	 **/
	@XmlElement
	public void setOrderitemseqnum(String orderitemseqnum) {
		preset(orderitemseqnumPropertyName, orderitemseqnum);
		this.orderitemseqnum = orderitemseqnum;
	}

	/**
	 * {@link #quantity} accessor.
	 * @return	The value.
	 **/
	public Decimal2 getQuantity() {
		return quantity;
	}

	/**
	 * {@link #quantity} mutator.
	 * @param quantity	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal2Mapper.class)
	@XmlElement
	public void setQuantity(Decimal2 quantity) {
		preset(quantityPropertyName, quantity);
		this.quantity = quantity;
	}

	/**
	 * {@link #unitprice} accessor.
	 * @return	The value.
	 **/
	public Decimal2 getUnitprice() {
		return unitprice;
	}

	/**
	 * {@link #unitprice} mutator.
	 * @param unitprice	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal2Mapper.class)
	@XmlElement
	public void setUnitprice(Decimal2 unitprice) {
		preset(unitpricePropertyName, unitprice);
		this.unitprice = unitprice;
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

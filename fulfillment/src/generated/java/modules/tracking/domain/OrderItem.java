package modules.tracking.domain;

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
import org.skyve.impl.domain.types.jaxb.DateTimeMapper;

/**
 * ToDo
 * 
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
	public static final String MODULE_NAME = "tracking";

	/** @hidden */
	public static final String DOCUMENT_NAME = "OrderItem";

	/** @hidden */
	public static final String orderitemidPropertyName = "orderitemid";

	/** @hidden */
	public static final String productidPropertyName = "productid";

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
	 * Product Id
	 **/
	private String productid;

	/**
	 * Quantity
	 **/
	private Integer quantity;

	/**
	 * Unit Price
	 **/
	private Integer unitprice;

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
	 * {@link #productid} accessor.
	 * @return	The value.
	 **/
	public String getProductid() {
		return productid;
	}

	/**
	 * {@link #productid} mutator.
	 * @param productid	The new value.
	 **/
	@XmlElement
	public void setProductid(String productid) {
		preset(productidPropertyName, productid);
		this.productid = productid;
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
	 * {@link #unitprice} accessor.
	 * @return	The value.
	 **/
	public Integer getUnitprice() {
		return unitprice;
	}

	/**
	 * {@link #unitprice} mutator.
	 * @param unitprice	The new value.
	 **/
	@XmlElement
	public void setUnitprice(Integer unitprice) {
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
}

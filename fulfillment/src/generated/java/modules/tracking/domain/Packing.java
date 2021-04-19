package modules.tracking.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * Packing
 * 
 * @navhas n orderid 0..1 OrderHeader
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class Packing extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "tracking";

	/** @hidden */
	public static final String DOCUMENT_NAME = "Packing";

	/** @hidden */
	public static final String orderidPropertyName = "orderid";

	/** @hidden */
	public static final String checkedPropertyName = "checked";

	/**
	 * Order Id
	 **/
	private OrderHeader orderid = null;

	/**
	 * Approved
	 **/
	private Boolean checked;

	@Override
	@XmlTransient
	public String getBizModule() {
		return Packing.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Packing.DOCUMENT_NAME;
	}

	public static Packing newInstance() {
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
			return org.skyve.util.Binder.formatMessage("Packing", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Packing) && 
					this.getBizId().equals(((Packing) o).getBizId()));
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
	 * {@link #checked} accessor.
	 * @return	The value.
	 **/
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * {@link #checked} mutator.
	 * @param checked	The new value.
	 **/
	@XmlElement
	public void setChecked(Boolean checked) {
		preset(checkedPropertyName, checked);
		this.checked = checked;
	}
}

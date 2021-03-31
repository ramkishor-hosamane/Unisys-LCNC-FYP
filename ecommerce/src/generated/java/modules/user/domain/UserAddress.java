package modules.user.domain;

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
 * UserAddress
 * 
 * @navhas n addressid 1 Address
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class UserAddress extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "user";
	/** @hidden */
	public static final String DOCUMENT_NAME = "UserAddress";

	/** @hidden */
	public static final String userloginidPropertyName = "userloginid";
	/** @hidden */
	public static final String addressidPropertyName = "addressid";
	/** @hidden */
	public static final String sequencenoPropertyName = "sequenceno";
	/** @hidden */
	public static final String addresstypePropertyName = "addresstype";
	/** @hidden */
	public static final String createdstampPropertyName = "createdstamp";
	/** @hidden */
	public static final String updatedstampPropertyName = "updatedstamp";

	/**
	 * User Login ID
	 **/
	private String userloginid;
	/**
	 * Address Id
	 **/
	private Address addressid = null;
	/**
	 * Sequence number
	 **/
	private String sequenceno;
	/**
	 * Address Type
	 **/
	private String addresstype;
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
		return UserAddress.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return UserAddress.DOCUMENT_NAME;
	}

	public static UserAddress newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{userloginid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof UserAddress) && 
					this.getBizId().equals(((UserAddress) o).getBizId()));
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
	 * {@link #sequenceno} accessor.
	 * @return	The value.
	 **/
	public String getSequenceno() {
		return sequenceno;
	}

	/**
	 * {@link #sequenceno} mutator.
	 * @param sequenceno	The new value.
	 **/
	@XmlElement
	public void setSequenceno(String sequenceno) {
		preset(sequencenoPropertyName, sequenceno);
		this.sequenceno = sequenceno;
	}

	/**
	 * {@link #addresstype} accessor.
	 * @return	The value.
	 **/
	public String getAddresstype() {
		return addresstype;
	}

	/**
	 * {@link #addresstype} mutator.
	 * @param addresstype	The new value.
	 **/
	@XmlElement
	public void setAddresstype(String addresstype) {
		preset(addresstypePropertyName, addresstype);
		this.addresstype = addresstype;
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

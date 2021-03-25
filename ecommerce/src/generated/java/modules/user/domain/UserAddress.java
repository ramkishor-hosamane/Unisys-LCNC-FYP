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
import org.skyve.domain.types.Decimal10;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.DateTimeMapper;
import org.skyve.impl.domain.types.jaxb.Decimal10Mapper;

/**
 * UserAddress
 * 
 * @navhas n userlogin 1 UserLogin
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
	public static final String userloginPropertyName = "userlogin";
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
	 * User Login
	 **/
	private UserLogin userlogin = null;
	/**
	 * Address Id
	 **/
	private Decimal10 addressid;
	/**
	 * Sequence number
	 **/
	private Decimal10 sequenceno;
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
			return org.skyve.util.Binder.formatMessage("{userlogin}", this);
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
	 * {@link #addressid} accessor.
	 * @return	The value.
	 **/
	public Decimal10 getAddressid() {
		return addressid;
	}

	/**
	 * {@link #addressid} mutator.
	 * @param addressid	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal10Mapper.class)
	@XmlElement
	public void setAddressid(Decimal10 addressid) {
		preset(addressidPropertyName, addressid);
		this.addressid = addressid;
	}

	/**
	 * {@link #sequenceno} accessor.
	 * @return	The value.
	 **/
	public Decimal10 getSequenceno() {
		return sequenceno;
	}

	/**
	 * {@link #sequenceno} mutator.
	 * @param sequenceno	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal10Mapper.class)
	@XmlElement
	public void setSequenceno(Decimal10 sequenceno) {
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

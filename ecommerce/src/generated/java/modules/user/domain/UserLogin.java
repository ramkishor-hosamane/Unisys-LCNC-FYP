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
 * UserLogin
 * 
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class UserLogin extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "user";
	/** @hidden */
	public static final String DOCUMENT_NAME = "UserLogin";

	/** @hidden */
	public static final String userloginidPropertyName = "userloginid";
	/** @hidden */
	public static final String emailidPropertyName = "emailid";
	/** @hidden */
	public static final String mobilenoPropertyName = "mobileno";
	/** @hidden */
	public static final String firstnamePropertyName = "firstname";
	/** @hidden */
	public static final String lastnamePropertyName = "lastname";
	/** @hidden */
	public static final String passwordPropertyName = "password";
	/** @hidden */
	public static final String isenabledPropertyName = "isenabled";
	/** @hidden */
	public static final String createdstampPropertyName = "createdstamp";
	/** @hidden */
	public static final String updatedstampPropertyName = "updatedstamp";

	/**
	 * User Login ID
	 **/
	private String userloginid;
	/**
	 * Email ID
	 **/
	private String emailid;
	/**
	 * Mobile Number
	 **/
	private String mobileno;
	/**
	 * First Name
	 **/
	private String firstname;
	/**
	 * Last Name
	 **/
	private String lastname;
	/**
	 * Password
	 **/
	private String password;
	/**
	 * Is Enabled
	 **/
	private Boolean isenabled;
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
		return UserLogin.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return UserLogin.DOCUMENT_NAME;
	}

	public static UserLogin newInstance() {
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
		return ((o instanceof UserLogin) && 
					this.getBizId().equals(((UserLogin) o).getBizId()));
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
	 * {@link #emailid} accessor.
	 * @return	The value.
	 **/
	public String getEmailid() {
		return emailid;
	}

	/**
	 * {@link #emailid} mutator.
	 * @param emailid	The new value.
	 **/
	@XmlElement
	public void setEmailid(String emailid) {
		preset(emailidPropertyName, emailid);
		this.emailid = emailid;
	}

	/**
	 * {@link #mobileno} accessor.
	 * @return	The value.
	 **/
	public String getMobileno() {
		return mobileno;
	}

	/**
	 * {@link #mobileno} mutator.
	 * @param mobileno	The new value.
	 **/
	@XmlElement
	public void setMobileno(String mobileno) {
		preset(mobilenoPropertyName, mobileno);
		this.mobileno = mobileno;
	}

	/**
	 * {@link #firstname} accessor.
	 * @return	The value.
	 **/
	public String getFirstname() {
		return firstname;
	}

	/**
	 * {@link #firstname} mutator.
	 * @param firstname	The new value.
	 **/
	@XmlElement
	public void setFirstname(String firstname) {
		preset(firstnamePropertyName, firstname);
		this.firstname = firstname;
	}

	/**
	 * {@link #lastname} accessor.
	 * @return	The value.
	 **/
	public String getLastname() {
		return lastname;
	}

	/**
	 * {@link #lastname} mutator.
	 * @param lastname	The new value.
	 **/
	@XmlElement
	public void setLastname(String lastname) {
		preset(lastnamePropertyName, lastname);
		this.lastname = lastname;
	}

	/**
	 * {@link #password} accessor.
	 * @return	The value.
	 **/
	public String getPassword() {
		return password;
	}

	/**
	 * {@link #password} mutator.
	 * @param password	The new value.
	 **/
	@XmlElement
	public void setPassword(String password) {
		preset(passwordPropertyName, password);
		this.password = password;
	}

	/**
	 * {@link #isenabled} accessor.
	 * @return	The value.
	 **/
	public Boolean getIsenabled() {
		return isenabled;
	}

	/**
	 * {@link #isenabled} mutator.
	 * @param isenabled	The new value.
	 **/
	@XmlElement
	public void setIsenabled(Boolean isenabled) {
		preset(isenabledPropertyName, isenabled);
		this.isenabled = isenabled;
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

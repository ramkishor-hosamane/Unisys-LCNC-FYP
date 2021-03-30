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
 * Address
 * 
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class Address extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "user";
	/** @hidden */
	public static final String DOCUMENT_NAME = "Address";

	/** @hidden */
	public static final String addressidPropertyName = "addressid";
	/** @hidden */
	public static final String address1PropertyName = "address1";
	/** @hidden */
	public static final String address2PropertyName = "address2";
	/** @hidden */
	public static final String cityPropertyName = "city";
	/** @hidden */
	public static final String statePropertyName = "state";
	/** @hidden */
	public static final String zipcodePropertyName = "zipcode";
	/** @hidden */
	public static final String countryPropertyName = "country";
	/** @hidden */
	public static final String createdstampPropertyName = "createdstamp";
	/** @hidden */
	public static final String updatedstampPropertyName = "updatedstamp";

	/**
	 * Address Id
	 **/
	private String addressid;
	/**
	 * Address line 1
	 **/
	private String address1;
	/**
	 * Address line 2
	 **/
	private String address2;
	/**
	 * City
	 **/
	private String city;
	/**
	 * State
	 **/
	private String state;
	/**
	 * Zip code
	 **/
	private String zipcode;
	/**
	 * Country
	 **/
	private String country;
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
		return Address.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Address.DOCUMENT_NAME;
	}

	public static Address newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{addressid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Address) && 
					this.getBizId().equals(((Address) o).getBizId()));
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
	 * {@link #address1} accessor.
	 * @return	The value.
	 **/
	public String getAddress1() {
		return address1;
	}

	/**
	 * {@link #address1} mutator.
	 * @param address1	The new value.
	 **/
	@XmlElement
	public void setAddress1(String address1) {
		preset(address1PropertyName, address1);
		this.address1 = address1;
	}

	/**
	 * {@link #address2} accessor.
	 * @return	The value.
	 **/
	public String getAddress2() {
		return address2;
	}

	/**
	 * {@link #address2} mutator.
	 * @param address2	The new value.
	 **/
	@XmlElement
	public void setAddress2(String address2) {
		preset(address2PropertyName, address2);
		this.address2 = address2;
	}

	/**
	 * {@link #city} accessor.
	 * @return	The value.
	 **/
	public String getCity() {
		return city;
	}

	/**
	 * {@link #city} mutator.
	 * @param city	The new value.
	 **/
	@XmlElement
	public void setCity(String city) {
		preset(cityPropertyName, city);
		this.city = city;
	}

	/**
	 * {@link #state} accessor.
	 * @return	The value.
	 **/
	public String getState() {
		return state;
	}

	/**
	 * {@link #state} mutator.
	 * @param state	The new value.
	 **/
	@XmlElement
	public void setState(String state) {
		preset(statePropertyName, state);
		this.state = state;
	}

	/**
	 * {@link #zipcode} accessor.
	 * @return	The value.
	 **/
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * {@link #zipcode} mutator.
	 * @param zipcode	The new value.
	 **/
	@XmlElement
	public void setZipcode(String zipcode) {
		preset(zipcodePropertyName, zipcode);
		this.zipcode = zipcode;
	}

	/**
	 * {@link #country} accessor.
	 * @return	The value.
	 **/
	public String getCountry() {
		return country;
	}

	/**
	 * {@link #country} mutator.
	 * @param country	The new value.
	 **/
	@XmlElement
	public void setCountry(String country) {
		preset(countryPropertyName, country);
		this.country = country;
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

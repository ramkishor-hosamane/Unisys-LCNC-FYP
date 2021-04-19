package modules.admin.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * Settings
 * 
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class Settings extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "admin";
	/** @hidden */
	public static final String DOCUMENT_NAME = "Settings";

	/** @hidden */
	public static final String wishlistPropertyName = "wishlist";

	/**
	 * Wishlist
	 * <br/>
	 * Enable or Disable wishlist
	 **/
	private Boolean wishlist;

	@Override
	@XmlTransient
	public String getBizModule() {
		return Settings.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Settings.DOCUMENT_NAME;
	}

	public static Settings newInstance() {
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
			return org.skyve.util.Binder.formatMessage("Settings", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Settings) && 
					this.getBizId().equals(((Settings) o).getBizId()));
	}

	/**
	 * {@link #wishlist} accessor.
	 * @return	The value.
	 **/
	public Boolean getWishlist() {
		return wishlist;
	}

	/**
	 * {@link #wishlist} mutator.
	 * @param wishlist	The new value.
	 **/
	@XmlElement
	public void setWishlist(Boolean wishlist) {
		preset(wishlistPropertyName, wishlist);
		this.wishlist = wishlist;
	}
}

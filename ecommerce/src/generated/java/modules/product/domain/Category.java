package modules.product.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * Category
 * 
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class Category extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "product";
	/** @hidden */
	public static final String DOCUMENT_NAME = "Category";

	/** @hidden */
	public static final String categoryidPropertyName = "categoryid";
	/** @hidden */
	public static final String sequencenumPropertyName = "sequencenum";
	/** @hidden */
	public static final String categorynamePropertyName = "categoryname";

	/**
	 * Category Id
	 **/
	private String categoryid;
	/**
	 * Sequence number
	 **/
	private String sequencenum;
	/**
	 * Category
	 **/
	private String categoryname;

	@Override
	@XmlTransient
	public String getBizModule() {
		return Category.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Category.DOCUMENT_NAME;
	}

	public static Category newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{categoryid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Category) && 
					this.getBizId().equals(((Category) o).getBizId()));
	}

	/**
	 * {@link #categoryid} accessor.
	 * @return	The value.
	 **/
	public String getCategoryid() {
		return categoryid;
	}

	/**
	 * {@link #categoryid} mutator.
	 * @param categoryid	The new value.
	 **/
	@XmlElement
	public void setCategoryid(String categoryid) {
		preset(categoryidPropertyName, categoryid);
		this.categoryid = categoryid;
	}

	/**
	 * {@link #sequencenum} accessor.
	 * @return	The value.
	 **/
	public String getSequencenum() {
		return sequencenum;
	}

	/**
	 * {@link #sequencenum} mutator.
	 * @param sequencenum	The new value.
	 **/
	@XmlElement
	public void setSequencenum(String sequencenum) {
		preset(sequencenumPropertyName, sequencenum);
		this.sequencenum = sequencenum;
	}

	/**
	 * {@link #categoryname} accessor.
	 * @return	The value.
	 **/
	public String getCategoryname() {
		return categoryname;
	}

	/**
	 * {@link #categoryname} mutator.
	 * @param categoryname	The new value.
	 **/
	@XmlElement
	public void setCategoryname(String categoryname) {
		preset(categorynamePropertyName, categoryname);
		this.categoryname = categoryname;
	}
}

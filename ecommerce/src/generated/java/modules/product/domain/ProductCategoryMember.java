package modules.product.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * ProductCategoryMember
 * 
 * @navhas n productid 1 Product
 * @navhas n categoryid 1 Category
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class ProductCategoryMember extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "product";
	/** @hidden */
	public static final String DOCUMENT_NAME = "ProductCategoryMember";

	/** @hidden */
	public static final String categoryidPropertyName = "categoryid";
	/** @hidden */
	public static final String productidPropertyName = "productid";
	/** @hidden */
	public static final String sequencenumPropertyName = "sequencenum";

	/**
	 * Category id
	 **/
	private Category categoryid = null;
	/**
	 * Product id
	 **/
	private Product productid = null;
	/**
	 * Sequence number
	 **/
	private String sequencenum;

	@Override
	@XmlTransient
	public String getBizModule() {
		return ProductCategoryMember.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return ProductCategoryMember.DOCUMENT_NAME;
	}

	public static ProductCategoryMember newInstance() {
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
		return ((o instanceof ProductCategoryMember) && 
					this.getBizId().equals(((ProductCategoryMember) o).getBizId()));
	}

	/**
	 * {@link #categoryid} accessor.
	 * @return	The value.
	 **/
	public Category getCategoryid() {
		return categoryid;
	}

	/**
	 * {@link #categoryid} mutator.
	 * @param categoryid	The new value.
	 **/
	@XmlElement
	public void setCategoryid(Category categoryid) {
		if (this.categoryid != categoryid) {
			preset(categoryidPropertyName, categoryid);
			this.categoryid = categoryid;
		}
	}

	/**
	 * {@link #productid} accessor.
	 * @return	The value.
	 **/
	public Product getProductid() {
		return productid;
	}

	/**
	 * {@link #productid} mutator.
	 * @param productid	The new value.
	 **/
	@XmlElement
	public void setProductid(Product productid) {
		if (this.productid != productid) {
			preset(productidPropertyName, productid);
			this.productid = productid;
		}
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
}

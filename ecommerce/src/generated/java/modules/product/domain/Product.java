package modules.product.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.Decimal10;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.types.jaxb.Decimal10Mapper;

/**
 * Product
 * 
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class Product extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "product";
	/** @hidden */
	public static final String DOCUMENT_NAME = "Product";

	/** @hidden */
	public static final String productidPropertyName = "productid";
	/** @hidden */
	public static final String productnamePropertyName = "productname";
	/** @hidden */
	public static final String productpricePropertyName = "productprice";
	/** @hidden */
	public static final String productstockPropertyName = "productstock";
	/** @hidden */
	public static final String smallimgPropertyName = "smallimg";
	/** @hidden */
	public static final String largeimgPropertyName = "largeimg";
	/** @hidden */
	public static final String descriptionPropertyName = "description";

	/**
	 * Product Id
	 **/
	private Decimal10 productid;
	/**
	 * Product Name
	 **/
	private String productname;
	/**
	 * Product Price
	 **/
	private Decimal10 productprice;
	/**
	 * Product Stock
	 **/
	private Decimal10 productstock;
	/**
	 * Small Image
	 **/
	private String smallimg;
	/**
	 * Large Image
	 **/
	private String largeimg;
	/**
	 * Description
	 **/
	private String description;

	@Override
	@XmlTransient
	public String getBizModule() {
		return Product.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Product.DOCUMENT_NAME;
	}

	public static Product newInstance() {
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
			return org.skyve.util.Binder.formatMessage("{productid}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Product) && 
					this.getBizId().equals(((Product) o).getBizId()));
	}

	/**
	 * {@link #productid} accessor.
	 * @return	The value.
	 **/
	public Decimal10 getProductid() {
		return productid;
	}

	/**
	 * {@link #productid} mutator.
	 * @param productid	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal10Mapper.class)
	@XmlElement
	public void setProductid(Decimal10 productid) {
		preset(productidPropertyName, productid);
		this.productid = productid;
	}

	/**
	 * {@link #productname} accessor.
	 * @return	The value.
	 **/
	public String getProductname() {
		return productname;
	}

	/**
	 * {@link #productname} mutator.
	 * @param productname	The new value.
	 **/
	@XmlElement
	public void setProductname(String productname) {
		preset(productnamePropertyName, productname);
		this.productname = productname;
	}

	/**
	 * {@link #productprice} accessor.
	 * @return	The value.
	 **/
	public Decimal10 getProductprice() {
		return productprice;
	}

	/**
	 * {@link #productprice} mutator.
	 * @param productprice	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal10Mapper.class)
	@XmlElement
	public void setProductprice(Decimal10 productprice) {
		preset(productpricePropertyName, productprice);
		this.productprice = productprice;
	}

	/**
	 * {@link #productstock} accessor.
	 * @return	The value.
	 **/
	public Decimal10 getProductstock() {
		return productstock;
	}

	/**
	 * {@link #productstock} mutator.
	 * @param productstock	The new value.
	 **/
	@XmlJavaTypeAdapter(Decimal10Mapper.class)
	@XmlElement
	public void setProductstock(Decimal10 productstock) {
		preset(productstockPropertyName, productstock);
		this.productstock = productstock;
	}

	/**
	 * {@link #smallimg} accessor.
	 * @return	The value.
	 **/
	public String getSmallimg() {
		return smallimg;
	}

	/**
	 * {@link #smallimg} mutator.
	 * @param smallimg	The new value.
	 **/
	@XmlElement
	public void setSmallimg(String smallimg) {
		preset(smallimgPropertyName, smallimg);
		this.smallimg = smallimg;
	}

	/**
	 * {@link #largeimg} accessor.
	 * @return	The value.
	 **/
	public String getLargeimg() {
		return largeimg;
	}

	/**
	 * {@link #largeimg} mutator.
	 * @param largeimg	The new value.
	 **/
	@XmlElement
	public void setLargeimg(String largeimg) {
		preset(largeimgPropertyName, largeimg);
		this.largeimg = largeimg;
	}

	/**
	 * {@link #description} accessor.
	 * @return	The value.
	 **/
	public String getDescription() {
		return description;
	}

	/**
	 * {@link #description} mutator.
	 * @param description	The new value.
	 **/
	@XmlElement
	public void setDescription(String description) {
		preset(descriptionPropertyName, description);
		this.description = description;
	}
}

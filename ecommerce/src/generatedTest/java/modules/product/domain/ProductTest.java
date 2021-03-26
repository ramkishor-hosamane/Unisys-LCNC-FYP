package modules.product.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class ProductTest extends AbstractDomainTest<Product> {

	@Override
	protected Product getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Product.MODULE_NAME, Product.DOCUMENT_NAME);
	}
}
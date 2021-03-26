package modules.product.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class ProductCategoryMemberTest extends AbstractDomainTest<ProductCategoryMember> {

	@Override
	protected ProductCategoryMember getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(ProductCategoryMember.MODULE_NAME, ProductCategoryMember.DOCUMENT_NAME);
	}
}
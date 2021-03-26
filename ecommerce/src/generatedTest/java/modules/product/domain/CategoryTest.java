package modules.product.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class CategoryTest extends AbstractDomainTest<Category> {

	@Override
	protected Category getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Category.MODULE_NAME, Category.DOCUMENT_NAME);
	}
}
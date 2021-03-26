package modules.cart.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class ShoppingCartTest extends AbstractDomainTest<ShoppingCart> {

	@Override
	protected ShoppingCart getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(ShoppingCart.MODULE_NAME, ShoppingCart.DOCUMENT_NAME);
	}
}
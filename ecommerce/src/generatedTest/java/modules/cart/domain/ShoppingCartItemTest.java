package modules.cart.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class ShoppingCartItemTest extends AbstractDomainTest<ShoppingCartItem> {

	@Override
	protected ShoppingCartItem getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(ShoppingCartItem.MODULE_NAME, ShoppingCartItem.DOCUMENT_NAME);
	}
}
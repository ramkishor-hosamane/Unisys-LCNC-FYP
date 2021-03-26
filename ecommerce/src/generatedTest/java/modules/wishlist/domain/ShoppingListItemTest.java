package modules.wishlist.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class ShoppingListItemTest extends AbstractDomainTest<ShoppingListItem> {

	@Override
	protected ShoppingListItem getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(ShoppingListItem.MODULE_NAME, ShoppingListItem.DOCUMENT_NAME);
	}
}
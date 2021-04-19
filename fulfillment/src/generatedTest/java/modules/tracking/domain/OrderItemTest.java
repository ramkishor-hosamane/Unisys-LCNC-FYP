package modules.tracking.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class OrderItemTest extends AbstractDomainTest<OrderItem> {

	@Override
	protected OrderItem getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(OrderItem.MODULE_NAME, OrderItem.DOCUMENT_NAME);
	}
}
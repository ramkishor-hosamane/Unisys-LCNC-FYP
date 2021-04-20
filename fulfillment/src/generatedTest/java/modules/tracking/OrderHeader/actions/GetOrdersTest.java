package modules.tracking.OrderHeader.actions;

import modules.tracking.domain.OrderHeader;
import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractActionTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractActionTest} to create your own tests for this action.
 */
public class GetOrdersTest extends AbstractActionTest<OrderHeader, GetOrders> {

	@Override
	protected GetOrders getAction() {
		return new GetOrders();
	}

	@Override
	protected OrderHeader getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(OrderHeader.MODULE_NAME, OrderHeader.DOCUMENT_NAME);
	}
}
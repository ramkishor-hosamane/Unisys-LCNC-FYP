package modules.tracking.OrderHeader.actions;

import modules.tracking.domain.OrderHeader;
import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractActionTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractActionTest} to create your own tests for this action.
 */
public class SubmitOrderTest extends AbstractActionTest<OrderHeader, SubmitOrder> {

	@Override
	protected SubmitOrder getAction() {
		return new SubmitOrder();
	}

	@Override
	protected OrderHeader getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(OrderHeader.MODULE_NAME, OrderHeader.DOCUMENT_NAME);
	}
}
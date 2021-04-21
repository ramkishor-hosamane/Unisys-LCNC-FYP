package modules.tracking.Shipping.actions;

import modules.tracking.domain.Shipping;
import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractActionTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractActionTest} to create your own tests for this action.
 */
public class SubmitShippingTest extends AbstractActionTest<Shipping, SubmitShipping> {

	@Override
	protected SubmitShipping getAction() {
		return new SubmitShipping();
	}

	@Override
	protected Shipping getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Shipping.MODULE_NAME, Shipping.DOCUMENT_NAME);
	}
}
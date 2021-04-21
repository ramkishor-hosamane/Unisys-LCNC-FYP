package modules.tracking.Picking.actions;

import modules.tracking.domain.Picking;
import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractActionTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractActionTest} to create your own tests for this action.
 */
public class SubmitPickingTest extends AbstractActionTest<Picking, SubmitPicking> {

	@Override
	protected SubmitPicking getAction() {
		return new SubmitPicking();
	}

	@Override
	protected Picking getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Picking.MODULE_NAME, Picking.DOCUMENT_NAME);
	}
}
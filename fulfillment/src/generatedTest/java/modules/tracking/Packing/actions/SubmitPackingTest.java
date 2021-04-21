package modules.tracking.Packing.actions;

import modules.tracking.domain.Packing;
import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractActionTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractActionTest} to create your own tests for this action.
 */
public class SubmitPackingTest extends AbstractActionTest<Packing, SubmitPacking> {

	@Override
	protected SubmitPacking getAction() {
		return new SubmitPacking();
	}

	@Override
	protected Packing getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Packing.MODULE_NAME, Packing.DOCUMENT_NAME);
	}
}
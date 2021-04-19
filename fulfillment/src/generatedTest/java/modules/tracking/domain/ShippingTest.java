package modules.tracking.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class ShippingTest extends AbstractDomainTest<Shipping> {

	@Override
	protected Shipping getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Shipping.MODULE_NAME, Shipping.DOCUMENT_NAME);
	}
}
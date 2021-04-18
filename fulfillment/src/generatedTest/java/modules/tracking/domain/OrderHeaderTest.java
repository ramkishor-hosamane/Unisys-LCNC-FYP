package modules.tracking.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class OrderHeaderTest extends AbstractDomainTest<OrderHeader> {

	@Override
	protected OrderHeader getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(OrderHeader.MODULE_NAME, OrderHeader.DOCUMENT_NAME);
	}
}
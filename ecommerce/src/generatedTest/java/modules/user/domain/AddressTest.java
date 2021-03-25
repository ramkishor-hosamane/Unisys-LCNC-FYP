package modules.user.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class AddressTest extends AbstractDomainTest<Address> {

	@Override
	protected Address getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Address.MODULE_NAME, Address.DOCUMENT_NAME);
	}
}
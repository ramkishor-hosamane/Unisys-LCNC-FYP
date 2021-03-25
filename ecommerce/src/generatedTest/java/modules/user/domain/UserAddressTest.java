package modules.user.domain;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractDomainTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractDomainTest} to create your own tests for this document.
 */
public class UserAddressTest extends AbstractDomainTest<UserAddress> {

	@Override
	protected UserAddress getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(UserAddress.MODULE_NAME, UserAddress.DOCUMENT_NAME);
	}
}
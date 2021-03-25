package router;

import org.skyve.metadata.router.UxUi;

public class UxUis {
	public static final UxUi PHONE = UxUi.newPrimeFaces("phone", "editorial", "nova-light", null);
	public static final UxUi TABLET = UxUi.newPrimeFaces("tablet", "editorial", "nova-light", null);
	public static final UxUi DESKTOP = UxUi.newSmartClient("desktop", "Tahoe", "casablanca");
	public static final UxUi EXTERNAL = UxUi.newPrimeFaces("external", "editorial", "nova-light", null);
	public static final UxUi STARTUP = UxUi.newPrimeFaces("startup", "editorial", "nova-light", null);
}
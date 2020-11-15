package org.jresearch.gwt.locale.client;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Base Locale test class.
 */
@SuppressWarnings("nls")
public abstract class AbstractLocaleTest extends GWTTestCase {

	private static boolean firstTest = true;;

	@Override
	public String getModuleName() {
		return "org.jresearch.gwt.locale.module_test";
	}

	@Override
	public void gwtSetUp() throws Exception {
		if (firstTest) {
			gwtSetUpOnce();
			firstTest = false;
		}
		super.gwtSetUp();
	}

	public void gwtSetUpOnce() {
		// nothing
	}

}

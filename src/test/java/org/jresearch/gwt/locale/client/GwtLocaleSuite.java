package org.jresearch.gwt.locale.client;

import org.jresearch.gwt.locale.client.locale.TestLocale;

import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.Test;

public class GwtLocaleSuite {

	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite("Locale browser independent tests");

		// $JUnit-BEGIN$
		suite.addTestSuite(GwtTest.class);
		suite.addTestSuite(TestLocale.class);
		// $JUnit-END$

		return suite;
	}
}

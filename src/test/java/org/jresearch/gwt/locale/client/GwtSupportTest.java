package org.jresearch.gwt.locale.client;

import java.util.Locale;

import org.jresearch.gwt.locale.client.cldr.LocaleInfo;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;


@SuppressWarnings({ "static-method", "nls" })
public class GwtSupportTest extends GWTTestCase {

	/**
	 * Must refer to a valid module that sources this class.
	 */
	@Override
	public String getModuleName() {
		return "org.jresearch.gwt.locale.module";
	}

	@Test
	public void testSupportedLocalesOfDateTimeFormat() {
		Locale[] test = new Locale[] { LocaleInfo.AGQ_CM, LocaleInfo.DE_DE };
		Locale[] result = Support.supportedLocalesOfDateTimeFormat(test);
		assertNotNull(result);
		assertEquals(2, result.length);
	}

	@Test
	public void testSupportedLocalesOfNumberFormat() {
		Locale[] test = new Locale[] { LocaleInfo.AGQ_CM, LocaleInfo.DE_DE };
		Locale[] result = Support.supportedLocalesOfNumberFormat(test);
		assertNotNull(result);
		assertEquals(2, result.length);
	}
}

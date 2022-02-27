/*
 * Copyright (c) 2020-present Stanislav Spiridonov
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jresearch.gwt.emu.org.jresearch.gwt.locale.client.locale;

import java.util.Arrays;
import java.util.List;

import org.jresearch.gwt.emu.java.util.Locale;
import org.jresearch.gwt.emu.org.jresearch.gwt.locale.client.AbstractLocaleTest;
import org.junit.Test;

/**
 * Test Locale implementation in the browser
 */
@SuppressWarnings({ "static-method", "nls" })
public class TestLocale extends AbstractLocaleTest {

	public void test_default() {
		Locale locale = Locale.getDefault();
		assertNotNull(locale);
	}

	public void test_availableJavaConstant() {
		Locale[] locales = Locale.getAvailableLocales();
		assertNotNull(locales);
		List<Locale> localesList = Arrays.asList(locales);
		assertTrue(localesList.contains(Locale.CANADA));
		assertTrue(localesList.contains(Locale.CANADA_FRENCH));
		assertTrue(localesList.contains(Locale.CHINA));
		assertTrue(localesList.contains(Locale.ENGLISH));
		assertTrue(localesList.contains(Locale.FRANCE));
		assertTrue(localesList.contains(Locale.FRANCE));
		assertTrue(localesList.contains(Locale.GERMAN));
		assertTrue(localesList.contains(Locale.GERMANY));
		assertTrue(localesList.contains(Locale.ITALIAN));
		assertTrue(localesList.contains(Locale.ITALY));
		assertTrue(localesList.contains(Locale.JAPAN));
		assertTrue(localesList.contains(Locale.JAPANESE));
		assertTrue(localesList.contains(Locale.KOREA));
		assertTrue(localesList.contains(Locale.KOREAN));
		assertTrue(localesList.contains(Locale.PRC));
		assertTrue(localesList.contains(Locale.ROOT));
		assertTrue(localesList.contains(Locale.SIMPLIFIED_CHINESE));
		assertTrue(localesList.contains(Locale.TAIWAN));
		assertTrue(localesList.contains(Locale.TRADITIONAL_CHINESE));
		assertTrue(localesList.contains(Locale.UK));
		assertTrue(localesList.contains(Locale.US));
	}

	public void test_forLanguageTag_rootForEmpty() {
		Locale locale = Locale.forLanguageTag("");
		assertEquals(Locale.ROOT, locale);
	}

	public void test_forLanguageTag_rootForUnd() {
		Locale locale = Locale.forLanguageTag("und");
		assertEquals(Locale.ROOT, locale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_forLanguageTag_null() {
		try {
			Locale.forLanguageTag(null);
			fail("Missing exception");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void test_new_null_lang() {
		try {
			new Locale(null);
			fail("Missing exception");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void test_new_null_reg() {
		try {
			new Locale("null", null);
			fail("Missing exception");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void test_new_null_var() {
		try {
			new Locale("null", "null", null);
			fail("Missing exception");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void test_new_lang() {
		try {
			new Locale("nullnulln");
			fail("Missing exception");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void test_new_reg() {
		try {
			new Locale("it", "null");
			fail("Missing exception");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void test_new_var() {
		try {
			new Locale("ru", "GB", "JP");
			fail("Missing exception");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	public void test_new_he() {
		Locale locale = new Locale("he");
		assertEquals("iw", locale.getLanguage());
	}

	public void test_new_yi() {
		Locale locale = new Locale("yi");
		assertEquals("ji", locale.getLanguage());
	}

	public void test_new_id() {
		Locale locale = new Locale("id");
		assertEquals("in", locale.getLanguage());
	}

	public void test_new_ja_JP_JP() {
		Locale locale = new Locale("ja", "JP", "JP");
		assertEquals("ja-JP-u-ca-japanese", locale.toLanguageTag());
	}

	public void test_new_th_TH_TH() {
		Locale locale = new Locale("th", "TH", "TH");
		assertEquals("th-TH-u-nu-thai", locale.toLanguageTag());
	}

}

package org.jresearch.gwt.locale.client;

import java.util.Locale;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.jresearch.gwt.locale.client.cldr.LocaleInfo;
import org.jresearch.gwt.locale.client.loader.TimeJsBundle;
import org.jresearch.gwt.locale.client.locale.Locales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;

@SuppressWarnings("nls")
public class Support {

	private static final Logger LOGGER = LoggerFactory.getLogger(Support.class);

	private static final TimeJsBundle bundle = GWT.create(TimeJsBundle.class);

	private static boolean commonInitialized = false;

	static {
		init();
	}

	public static void init() {
		if (!commonInitialized) {
			LOGGER.trace("common initialization");
			ScriptInjector.fromString(bundle.support().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
			commonInitialized = true;
		}
	}

	@Nonnull
	public static Locale[] supportedLocalesOfDateTimeFormat(Locale[] locales) {
		String[] a = Stream.of(locales).map(Locale::toLanguageTag).filter(l -> !"root".equalsIgnoreCase(l)).toArray(String[]::new);
		String[] supportedLocales = SupportJs.supportedLocalesOfDateTimeFormat(a);
		return Stream.of(supportedLocales).map(Support::toLocale).toArray(Locale[]::new);
	}

	@Nonnull
	public static Locale[] supportedLocalesOfNumberFormat(Locale[] locales) {
		String[] a = Stream.of(locales).map(Locale::toLanguageTag).filter(l -> !"root".equalsIgnoreCase(l)).toArray(String[]::new);
		String[] supportedLocales = SupportJs.supportedLocalesOfNumberFormat(a);
		return Stream.of(supportedLocales).map(Support::toLocale).toArray(Locale[]::new);
	}

	public static Locale toLocale(String langTag) {
		return Stream.of(LocaleInfo.LOCALES).filter(l -> langTag.equalsIgnoreCase(l.toLanguageTag())).findAny().orElseGet(() -> createLocale(langTag));
	}

	public static Locale createLocale(String langTag) {
		String[] parts = langTag.split("-", 3);
		return parts.length == 1 ? new Locale(parts[0]) : parts.length == 2 ? createLocale(parts[0], parts[1]) : createLocale(parts[0], parts[1], parts[2]);
	}

	public static Locale createLocale(String lang, String something) {
		return isScript(something) ? Locales.create(lang, "", something, "") : Locales.create(lang, something, "", "");
	}

	public static Locale createLocale(String lang, String something01, String something02) {
		return isScript(something01) ? Locales.create(lang, something02, something01, "") : Locales.create(lang, something01, "", something02);
	}

	private static boolean isScript(String something) {
		return something.length() == 4;
	}

}

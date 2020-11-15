package org.jresearch.gwt.locale.client.locale;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.jresearch.gwt.locale.langtag.LangTag;
import org.jresearch.gwt.locale.langtag.LangTagException;

@SuppressWarnings("nls")
public class LocaleRegistry {

	private LocaleRegistry() {
		// prevent instantiation
	}

	private static Map<LangTag, Locale> reg = new HashMap<>();

	public static Locale register(String language, String region, String script, String variant) {
		if (language == null) {
			throw new NullPointerException("Language can't be a null");
		} else if (language.isEmpty()) {
			return reg.computeIfAbsent(null, t -> new Locale(""));
		}
		try {
			LangTag langTag = new LangTag(language);
			if (!region.isEmpty()) {
				langTag.setRegion(region);
			}
			if (!script.isEmpty()) {
				langTag.setScript(script);
			}
			if (!variant.isEmpty()) {
				langTag.setVariants(variant);
			}
			return register(langTag);
		} catch (LangTagException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Locale register(String language, String region, String script) {
		return register(language, region, script, "");
	}

	public static Locale register(String language, String region) {
		return register(language, region, "", "");
	}

	public static Locale register(String language) {
		return register(language, "", "", "");
	}

	public static Optional<Locale> lookup(LangTag tag) {
		return Optional.ofNullable(reg.get(tag));
	}

	public static Locale register(LangTag languageTag) {
		return reg.computeIfAbsent(languageTag, LocaleRegistry::by);
	}

	private static Locale by(LangTag languageTag) {
		return Locale.forLanguageTag(languageTag.toString());
	}

	public static Locale[] getRegisteredLocations() {
		return reg.values().toArray(new Locale[reg.values().size()]);
	}

}

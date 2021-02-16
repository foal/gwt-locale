package org.jresearch.gwt.emu.org.jresearch.gwt.locale.client.locale;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jresearch.gwt.emu.java.util.Locale;
import org.jresearch.locale.langtag.ImmutableLangTag;
import org.jresearch.locale.langtag.ImmutableLangTag.Builder;
import org.jresearch.locale.langtag.LangTag;

//@Wrap("org.jresearch.gwt.emu")
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
		Builder builder = ImmutableLangTag.builder()
				.primaryLanguage(language)
				.region(region)
				.script(script);
		if (!variant.isEmpty()) {
			builder.addVariants(variant);
		}

		return register(builder.build());
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

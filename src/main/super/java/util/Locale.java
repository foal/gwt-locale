package java.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jresearch.gwt.locale.client.locale.LocaleRegistry;

import com.google.gwt.i18n.client.LocaleInfo;
import com.nimbusds.langtag.LangTag;
import com.nimbusds.langtag.LangTagException;
import com.nimbusds.langtag.ReadOnlyLangTag;

@SuppressWarnings("nls")
public final class Locale {

	/** Constant from JDK */
	public static final Locale ENGLISH = LocaleRegistry.register("en");
	public static final Locale FRENCH = LocaleRegistry.register("fr");
	public static final Locale GERMAN = LocaleRegistry.register("de");
	public static final Locale ITALIAN = LocaleRegistry.register("it");
	public static final Locale JAPANESE = LocaleRegistry.register("ja");
	public static final Locale KOREAN = LocaleRegistry.register("ko");
	public static final Locale CHINESE = LocaleRegistry.register("zh");
	public static final Locale SIMPLIFIED_CHINESE = LocaleRegistry.register("zh", "CN");
	public static final Locale TRADITIONAL_CHINESE = LocaleRegistry.register("zh", "TW");
	public static final Locale FRANCE = LocaleRegistry.register("fr", "FR");
	public static final Locale GERMANY = LocaleRegistry.register("de", "DE");
	public static final Locale ITALY = LocaleRegistry.register("it", "IT");
	public static final Locale JAPAN = LocaleRegistry.register("ja", "JP");
	public static final Locale KOREA = LocaleRegistry.register("ko", "KR");
	public static final Locale UK = LocaleRegistry.register("en", "GB");
	public static final Locale US = LocaleRegistry.register("en", "US");
	public static final Locale CANADA = LocaleRegistry.register("en", "CA");
	public static final Locale CANADA_FRENCH = LocaleRegistry.register("fr", "CA");
	public static final Locale ROOT = LocaleRegistry.register("");
	public static final Locale CHINA = SIMPLIFIED_CHINESE;
	public static final Locale PRC = SIMPLIFIED_CHINESE;
	public static final Locale TAIWAN = TRADITIONAL_CHINESE;

	private static Locale defaultLocale = ROOT;

	private final ReadOnlyLangTag languageTag;

	static {
		String localeName = LocaleInfo.getCurrentLocale().getLocaleName();
		if (!"default".equalsIgnoreCase(localeName)) {
			String[] split = localeName.split("_", 3);
			if (split.length == 1) {
				defaultLocale = new Locale(localeName);
			} else if (split.length == 2) {
				defaultLocale = new Locale(split[0], split[1]);
			} else if (split.length == 3) {
				defaultLocale = new Locale(split[0], split[1], split[2]);
			}
		}
	}

	private Locale(LangTag langTag) {
		try {
			languageTag = LangTag.parse(langTag.toString());
		} catch (LangTagException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public Locale(String language, String region, String script, String variant) {
		if (language == null || script == null || region == null || variant == null) {
			throw new NullPointerException("You can't initialize Locale with null values");
		}
		if (language.isEmpty()) {
			languageTag = null;
		} else {
			try {
				LangTag lg = new LangTag(language);
				if (!region.isEmpty()) {
					lg.setRegion(region);
				}
				if (!script.isEmpty()) {
					lg.setScript(script);
				}
				if (!variant.isEmpty()) {
					lg.setVariants(variant);
				}
				languageTag = lg;
			} catch (LangTagException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public Locale(String language, String region, String script) {
		this(language, region, script, "");
	}

	public Locale(String language, String region) {
		this(language, region, "", "");
	}

	public Locale(String language) {
		this(language, "", "", "");
	}

	public static Locale getDefault() {
		return defaultLocale;
	}

	public static void setDefault(Locale locale) {
		defaultLocale = locale;
	}

	public static Locale[] getAvailableLocales() {
		return LocaleRegistry.getRegisteredLocations();
	}

	public String getLanguage() {
		return languageTag == null ? "" : languageTag.getLanguage();
	}

	public String getScript() {
		return languageTag == null ? "" : nullToEmpty(languageTag.getScript());
	}

	public String getCountry() {
		return languageTag == null ? "" : nullToEmpty(languageTag.getRegion());
	}

	public String getVariant() {
		if (languageTag == null) {
			return "";
		}
		String[] variants = languageTag.getVariants();
		return variants == null || variants.length == 0 ? "" : variants[0];
	}

	public boolean hasExtensions() {
		return languageTag != null && languageTag.getExtensions() != null && languageTag.getExtensions().length != 0;
	}

	public Locale stripExtensions() {
		if (hasExtensions()) {
			try {
				LangTag tag = LangTag.parse(languageTag.toString());
				tag.setExtensions((String[]) null);
				LocaleRegistry.lookup(tag).orElseGet(() -> new Locale(tag));
			} catch (LangTagException e) {
				throw new IllegalStateException(e);
			}
		}
		return this;
	}

	public String getExtension(char key) {
		if (hasExtensions()) {
			return Stream.of(languageTag.getExtensions())
					.filter(e -> e.charAt(0) == key)
					.findAny()
					.orElse(null);
		}
		return null;
	}

	@SuppressWarnings("boxing")
	public Set<Character> getExtensionKeys() {
		if (hasExtensions()) {
			return Stream.of(languageTag.getExtensions())
					.map(e -> e.charAt(0))
					.collect(Collectors.toSet());
		}
		return Collections.emptySet();
	}

	@Override
	public final String toString() {
		if (languageTag == null) {
			return "";
		}
		StringBuilder result = new StringBuilder(languageTag.getLanguage());

		String variant = getVariant();

		if (languageTag.getRegion() != null || !variant.isEmpty() || languageTag.getScript() != null || hasExtensions()) {
			result.append('_');
			result.append(getCountry());
		}
		if (!variant.isEmpty()) {
			result.append('_');
			result.append(variant);
		}

		if (languageTag.getScript() != null) {
			result.append("_#");
			result.append(languageTag.getScript());
		}

		if (hasExtensions()) {
			result.append('_');
			// check if we haven't '#' symbol added
			if (languageTag.getScript() == null) {
				result.append('#');
			}
			String[] extensions = languageTag.getExtensions();
			for (String ext : extensions) {
				result.append(ext).append('-');
			}
			result.deleteCharAt(result.length() - 1);
		}

		return result.toString();
	}

	public String toLanguageTag() {
		return languageTag == null ? "und" : languageTag.toString();
	}

	public static Locale forLanguageTag(String languageTag) {
		try {
			LangTag tag = LangTag.parse(languageTag);
			return LocaleRegistry.lookup(tag).orElseGet(() -> new Locale(tag));
		} catch (LangTagException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static String nullToEmpty(String str) {
		return str == null ? "" : str;
	}

	@Override
	public int hashCode() {
		return (languageTag == null ? "und" : languageTag).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null
				&& obj instanceof Locale
				&& toLanguageTag().equals(((Locale) obj).toLanguageTag());
	}

}

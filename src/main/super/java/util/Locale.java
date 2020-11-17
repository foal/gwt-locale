package java.util;

import java.util.stream.Collectors;

import org.jresearch.gwt.locale.client.locale.LocaleRegistry;
import org.jresearch.gwt.locale.langtag.ImmutableLangTag;
import org.jresearch.gwt.locale.langtag.LangTag;

import com.google.gwt.i18n.client.LocaleInfo;

@SuppressWarnings("nls")
public final class Locale {

	private static Map<String, String> LEGACY_ISO = new HashMap<>();

	static {
		LEGACY_ISO.put("he", "iw");
		LEGACY_ISO.put("yi", "ji");
		LEGACY_ISO.put("id", "in");
	}

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

	private final ImmutableLangTag languageTag;

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
		languageTag = ImmutableLangTag.copyOf(langTag);
	}

	public Locale(String language, String region, String variant) {
		if (language == null || region == null || variant == null) {
			throw new IllegalArgumentException("You can't initialize Locale with null values, lang " + language + ", region " + region + ", variant " + variant);
		}
		if (language.isEmpty()) {
			languageTag = null;
		} else {
			String lang = legacyIsoSupport(language);
			String tag = legacyCombinationsCheck(lang, region, variant);
			if (tag != null) {
				languageTag = LangTag.parse(tag);
			} else {
				languageTag = ImmutableLangTag.builder().primaryLanguage(lang)
						.region(region)
						.addVariants(variant)
						.build();
			}
		}
	}

	public Locale(String language, String region) {
		this(language, region, "");
	}

	public Locale(String language) {
		this(language, "", "");
	}

	public static Locale getDefault() {
		return defaultLocale;
	}

	private static String legacyIsoSupport(String language) {
		return LEGACY_ISO.getOrDefault(language, language);
	}

	private static String legacyCombinationsCheck(String language, String region, String variant) {
		if ("ja".equalsIgnoreCase(language) && "JP".equalsIgnoreCase(region) && "JP".equalsIgnoreCase(variant)) {
			// japanese calendar case: ja_JP_JP -> ja-JP-u-ca-japanese
			return "ja-JP-u-ca-japanese";
		} else if ("th".equalsIgnoreCase(language) && "th".equalsIgnoreCase(region) && "th".equalsIgnoreCase(variant)) {
			// thai numbersystem case: th_TH_TH -> th-TH-u-nu-thai
			return "th-TH-u-nu-thai";
		}
		return null;
	}

	public static void setDefault(Locale locale) {
		defaultLocale = locale;
	}

	public static Locale[] getAvailableLocales() {
		return LocaleRegistry.getRegisteredLocations();
	}

	public String getLanguage() {
		return languageTag == null ? "" : languageTag.language();
	}

	public String getScript() {
		return languageTag == null ? "" : languageTag.script();
	}

	public String getCountry() {
		return languageTag == null ? "" : languageTag.region();
	}

	public String getVariant() {
		if (languageTag == null) {
			return "";
		}
		return languageTag.variants().stream().findFirst().orElse("");
	}

	public boolean hasExtensions() {
		return languageTag != null && !languageTag.extensions().isEmpty();
	}

	public Locale stripExtensions() {
		if (hasExtensions()) {
			LangTag tag = languageTag.withExtensions();
			LocaleRegistry.lookup(tag).orElseGet(() -> new Locale(tag));
		}
		return this;
	}

	public String getExtension(char key) {
		return languageTag.extensions().stream()
				.filter(e -> e.charAt(0) == key)
				.findAny()
				.orElse(null);
	}

	@SuppressWarnings("boxing")
	public Set<Character> getExtensionKeys() {
		return languageTag.extensions().stream()
				.map(e -> e.charAt(0))
				.collect(Collectors.toSet());
	}

	@Override
	public final String toString() {
		if (languageTag == null) {
			return "";
		}
		StringBuilder result = new StringBuilder(languageTag.language());

		String variant = getVariant();

		if (!languageTag.region().isEmpty() || !variant.isEmpty() || !languageTag.script().isEmpty() || hasExtensions()) {
			result.append('_');
			result.append(getCountry());
		}

		if (!variant.isEmpty()) {
			result.append('_');
			result.append(variant);
		}

		if (!languageTag.script().isEmpty()) {
			result.append("_#");
			result.append(languageTag.script());
		}

		if (hasExtensions()) {
			result.append('_');
			// check if we haven't '#' symbol added
			if (languageTag.script().isEmpty()) {
				result.append('#');
			}
			List<String> extensions = languageTag.extensions();
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
		if (languageTag == null) {
			throw new IllegalArgumentException("Language tag can't be a null.");
		}
		// Special case for ROOT locale
		if (languageTag.isEmpty() || languageTag.equalsIgnoreCase("und")) {
			return ROOT;
		}
		LangTag tag = LangTag.parse(languageTag);
		return LocaleRegistry.lookup(tag).orElseGet(() -> new Locale(tag));
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

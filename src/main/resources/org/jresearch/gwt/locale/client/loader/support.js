if (typeof Intl == 'object' && typeof Intl.DateTimeFormat == 'function'  && typeof Intl.DateTimeFormat.supportedLocalesOf == 'function') {
	supportedLocalesOfDateTimeFormat = function(locales) {
		return Intl.DateTimeFormat.supportedLocalesOf(locales, { localeMatcher: 'lookup' });
	};
} else {
	supportedLocalesOfDateTimeFormat = function(locales) {
		return ["ROOT", "US"];
	};
}

if (typeof Intl == 'object' && typeof Intl.NumberFormat == 'function'  && typeof Intl.NumberFormat.supportedLocalesOf == 'function') {
	supportedLocalesOfNumberFormat = function(locales) {
		return Intl.NumberFormat.supportedLocalesOf(locales, { localeMatcher: 'lookup' });
	};
} else {
	supportedLocalesOfNumberFormat = function(locales) {
		return ["ROOT", "US"];
	};
}


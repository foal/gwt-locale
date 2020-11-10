package org.jresearch.gwt.locale.client;

import static jsinterop.annotations.JsPackage.GLOBAL;

import javax.annotation.Nonnull;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = GLOBAL, name = "support")
public class SupportJs {

	@Nonnull
	@JsMethod(namespace = JsPackage.GLOBAL)
	public static native String[] supportedLocalesOfDateTimeFormat(String[] locales);

	@Nonnull
	@JsMethod(namespace = JsPackage.GLOBAL)
	public static native String[] supportedLocalesOfNumberFormat(String[] locales);

}

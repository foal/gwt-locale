[![Maven Central](https://img.shields.io/maven-central/v/org.jresearch.gwt.locale/org.jresearch.gwt.locale)](https://mvnrepository.com/artifact/org.jresearch.gwt.locale/org.jresearch.gwt.locale)
[![Build](https://github.com/foal/gwt-locale/actions/workflows/BuildSnapshot.yml/badge.svg)](https://github.com/foal/gwt-locale/actions/workflows/BuildSnapshot.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=foal_gwt-locale&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=foal_gwt-locale)

## GWT java.util.Locale emulation

Emulation use the https://github.com/foal/gwt-language-tag internally. It provide almost all methods to work with Locale. Also it provide base set of **registered** locales as it defined in original java.util.Locale class.

All registered locales a returned by `Locale.getAvailableLocales()` call. 

To register new locale you can use the `org.jresearch.gwt.locale.client.locale.LocaleRegistry` that allows to register new Locales based on components of languageTag.

There is also possible to create new locale by constructor of `java.util.Locale`, but such locale considering as temporary, and will not register automatically.  

Issues about the adaptation should be reported here at GitHub.

### CLDR Locales
There is also the separate GWT module [GWT-locale-CLDR](https://github.com/foal/gwt-locale-cldr) that provides the constants for all supported CLDR locales. The locales will be automatikally registerd if you add the module to the project

### Using

* Add the following project dependency to pom.xml
```xml
<dependency>
	<groupId>org.jresearch.gwt.locale</groupId>
	<artifactId>org.jresearch.gwt.locale</artifactId>
	<version>1.0.4</version>
</dependency>
```
* Add `<inherits name="org.jresearch.gwt.locale.module"/>` to your module.gwt.xml, if you use gwt-maven-plugin form Thomas Broyer (https://github.com/tbroyer/gwt-maven-plugin) it will be done automatically

### Unimplemented or partial implemented features
* **java.util.Locale implementation may clash with original GWT implementation**. See [dicussion on Google Groups](https://groups.google.com/forum/#!msg/Google-Web-Toolkit/D0I1-Oao_V8/k5FEBrxNBQAJ) and similar [issue](https://github.com/gwtproject/gwt/issues/9682) with gwt-commons-lang3 (the class will separate to another project)* Compatibility with J2CL
* Updates from Java 9-14
* `getDisplay` methods
* some specific Language Tag methods
* `java.util.Locale.Builder` class
* `java.util.Locale.Category` enum
* `java.util.Locale.LanguageRange` class

#### FAQs

1. What version of Java SE does this project map to?
This project currently maps to the contents of release Java SE 8u20.

### Releases
Available in the [Maven Central repository](https://search.maven.org/search?q=g:org.jresearch.gwt.locale)

### Support
GitHub [issues](https://github.com/foal/gwt-locale/issues) and [pull requests](https://github.com/foal/gwt-locale/pulls) should be used when you want to help advance the project.

Note that pull requests and issues will only be considered so far as matching the behavior of Java SE releases.
Additional requested features will be rejected.

Pull requests must _not_ be copied from the JDK, because the GPL license is incompatible with the Apache license used here.


### Building from sources
* check out [APT project](https://github.com/foal/gwt-time-apt) and INSTALL (`mvn clean install`) it.
* check out this project
* `mvn clean install`
* The project use the parent pom located on Sonatype snapshot repository.
```xml
<repositories>
    <repository>
        <id>oss.sonatype.org-snapshot</id>
        <url>http://oss.sonatype.org/content/repositories/snapshots</url>
        <releases><enabled>false</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>
```
or download directly https://oss.sonatype.org/content/repositories/snapshots/org/jresearch/org.jresearch.pom/29-SNAPSHOT/

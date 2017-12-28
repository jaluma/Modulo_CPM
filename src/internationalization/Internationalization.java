package internationalization;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import logic.ListProduct;

public class Internationalization {
	public static final Locale[] locationSupported = new Locale[] {Locale.forLanguageTag("es-ES"), Locale.forLanguageTag("en-US")};
	
	private static final String BUNDLE_NAME = "resources.values";

	private static final String DEFAULT_LANGUAGE = Locale.getDefault().getLanguage();
	private static final String DEFAULT_COUNTRY = Locale.getDefault().getCountry();

	public static String language = DEFAULT_LANGUAGE;
	public static String country = DEFAULT_COUNTRY;;
	public static Locale locale = new Locale(language, country);

	private static ResourceBundle RESOURCE_BUNDLE = inicialiceBundle();

	private Internationalization() {
	}

	private static ResourceBundle inicialiceBundle() {
		ResourceBundle rb;
		try {
			rb = ResourceBundle.getBundle(BUNDLE_NAME, locale);
		} catch (MissingResourceException e) {
			rb = ResourceBundle.getBundle(BUNDLE_NAME);
		}
		return rb;
	}

	public static void changeLocation(String language, String country) {
		locale = new Locale(language, country);
		Locale.setDefault(locale);
		RESOURCE_BUNDLE = inicialiceBundle();
		ResourceBundle.clearCache();
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static Locale getLocate() {
		return locale;
	}

	public static String getActualDate() {
		Date now = new Date();
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
		return formatter.format(now);
	}

	public static String getFormatDate(Date date) {
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		return formatter.format(date);
	}

	public static String getCurrency(double amount) {
		return NumberFormat.getCurrencyInstance(locale).format(amount);
	}

	public static char getMnemonic(String key) {
		String str = key + "_m";
		return Internationalization.getString(str).charAt(0);
	}

	public static String getToolTips(String key) {
		String str = key + "_tt";
		return Internationalization.getString(str);

	}

	public static String getProduct(String code) {
		String str = code + "_name";
		String aux = Internationalization.getString(str);
		char letter = aux.charAt(0);
		return letter == '!' ? ListProduct.searchProduct(code).getName() : aux;
	}

	public static String getCountry(String country) {
		return Internationalization.getString(country);
	}
}

package it.windtre.tremobilitycms.ui.utils;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.data.domain.Sort;

import java.util.Locale;

public class BakeryConst {

	public static final Locale APP_LOCALE = Locale.US;

	public static final String PAGE_ROOT = "";
	public static final String PAGE_STOREFRONT = "products";
	public static final String PAGE_STOREFRONT_EDIT = "storefront/edit";
	public static final String PAGE_DASHBOARD = "dashboard";
	public static final String PAGE_USERS = "users";
	public static final String PAGE_PRODUCTS = "products";
	public static final String PAGE_CONTAINERS = "containers";
	public static final String PAGE_ELEMENTS = "elements";
	public static final String PAGE_CARDS = "cards";
	public static final String PAGE_SERVICES = "services";
	public static final String PAGE_SERVICESLIST = "serviceslist";
	public static final String PAGE_SERVICE_ITEMS = "serviceitems";
	public static final String PAGE_ZONES = "zoneitems";
	public static final String PAGE_LOGOUT = "logout";
	public static final String PAGE_DEFAULT = PAGE_SERVICES;

	public static final String ICON_STOREFRONT = "edit";
	public static final String ICON_DASHBOARD = "clock";
	public static final String ICON_USERS = "user";
	public static final String ICON_PRODUCTS = "calendar";
	public static final String ICON_CONTAINERS = "clock";
	public static final String ICON_ELEMENTS = "clock";
	public static final String ICON_CARDS = "clock";
	public static final String ICON_SERVICES = "clock";
	public static final String ICON_SERVICE_ITEMS = "clock";
	public static final String ICON_ZONES = "clock";
	public static final String ICON_LOGOUT = "arrow-right";

	public static final String TITLE_STOREFRONT = "Storefront";
	public static final String TITLE_DASHBOARD = "Dashboard";
	public static final String TITLE_USERS = "UTENTE";
	public static final String TITLE_PRODUCTS = "Workspace";
	public static final String TITLE_CONTAINERS = "HOME PAGE";
	public static final String TITLE_ELEMENTS = "COMPONENTE";
	public static final String TITLE_CARDS = "Cards";
	public static final String TITLE_SERVICES = "SERVIZIO";
	public static final String TITLE_SERVICE_ITEMS = "BIGLIETTO";
	public static final String TITLE_ZONES = "Zones";
	public static final String TITLE_LOGOUT = "LOGOUT";
	public static final String TITLE_NOT_FOUND = "Page was not found";
	public static final String TITLE_ACCESS_DENIED = "Access denied";

	public static final String[] ORDER_SORT_FIELDS = {"dueDate", "dueTime", "id"};
	public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

	public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes";

	// Mutable for testing.
	public static int NOTIFICATION_DURATION = 4000;

}

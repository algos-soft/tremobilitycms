package it.windtre.tremobilitycms.backend.data;

public class Role {
	public static final String OPERATOR = "operator";
	public static final String READONLY = "readonly";
	public static final String ADMIN = "admin";

	private Role() {
		// Static methods and fields only
	}

	public static String[] getAllRoles() {
		return new String[] {OPERATOR, READONLY, ADMIN };
	}

}

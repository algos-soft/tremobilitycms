package it.windtre.tremobilitycms.backend.data;

/**
 * Created by vcasari on 11/09/18.
 */
public class Enable {
    public static final String ENABLED = "ENABLED";
    public static final String DISABLED = "DISABLED";

    private Enable() {
        // Static methods and fields only
    }

    public static String[] getAllEnables() {
        return new String[] { ENABLED, DISABLED };
    }
}

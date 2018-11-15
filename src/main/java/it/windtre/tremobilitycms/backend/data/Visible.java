package it.windtre.tremobilitycms.backend.data;

/**
 * Created by vcasari on 11/09/18.
 */
public class Visible {
    public static final String VISIBLE = "VISIBLE";
    public static final String NOT_VISIBLE = "NOT VISIBLE";

    private Visible() {
        // Static methods and fields only
    }

    public static String[] getAllVisibles() {
        return new String[] { VISIBLE, NOT_VISIBLE };
    }
}

package it.windtre.tremobilitycms.backend.data;

public class InfoZoneType {

    public static final String NONE = "";
    public static final String A = "A";
    public static final String C = "C";
    public static final String N = "N";
    public static final String V = "V";

    public static String[] getAllZoneTypes() {
        return new String[] {NONE, A, C, N, V};
    }


}

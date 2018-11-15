package it.windtre.tremobilitycms.backend.data;

public class ElementModeType {
    public static final String BANNER = "BANNER";
    public static final String BUTTON = "BUTTON";

    private ElementModeType() {

    }

    public static String[] getAllElementModeTypes() {
        return new String[] {BANNER, BUTTON};
    }
}

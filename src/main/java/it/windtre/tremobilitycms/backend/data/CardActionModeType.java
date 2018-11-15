package it.windtre.tremobilitycms.backend.data;

public class CardActionModeType {
    public static final String INTERNAL = "INTERNAL";
    public static final String EXTERNAL = "EXTERNAL";

    private CardActionModeType() {

    }

    public static String[] getAllCardActionModeTypes() {
        return new String[] {INTERNAL, EXTERNAL};
    }
}


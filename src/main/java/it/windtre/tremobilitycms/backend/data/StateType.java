package it.windtre.tremobilitycms.backend.data;

public class StateType {
    public static final String VISIBLE = "VISIBLE";
    public static final String INVISIBLE = "INVISIBLE";

    private StateType() {

    }

    public static String[] getAllStateTypes() {
        return new String[] {VISIBLE, INVISIBLE};
    }
}

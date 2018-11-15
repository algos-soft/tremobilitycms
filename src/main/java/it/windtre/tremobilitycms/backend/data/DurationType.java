package it.windtre.tremobilitycms.backend.data;

public class DurationType {
    public static final String FROM_PURCHASE_TIME = "fromPurchaseTime";
    public static final String FROM_MIDNIGHT = "fromMidnight";

    public static String[] getAllDurationTypes() {
        return new String[] {FROM_PURCHASE_TIME, FROM_MIDNIGHT};
    }

}

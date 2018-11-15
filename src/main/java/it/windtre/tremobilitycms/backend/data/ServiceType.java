package it.windtre.tremobilitycms.backend.data;

public class ServiceType {
    public static final String TICKETING = "TICKETING";
    public static final String PARKING = "PARKING";
    public static final String PARKING_RECHARGE = "PARKING RECHARGE";
    public static final String ZTL = "ZTL";

    private ServiceType() {

    }

    public static String[] getAllServiceTypes() {
        return new String[] {TICKETING, PARKING, PARKING_RECHARGE, ZTL};
    }
}

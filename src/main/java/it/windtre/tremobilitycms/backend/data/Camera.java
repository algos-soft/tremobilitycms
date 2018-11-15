package it.windtre.tremobilitycms.backend.data;

/**
 * Created by vcasari on 11/09/18.
 */
public class Camera {
    public static final String REAR = "REAR";
    public static final String FRONT = "FRONT";

    private Camera() {
        // Static methods and fields only
    }

    public static String[] getAllCameras() {
        return new String[] { REAR, FRONT };
    }
}

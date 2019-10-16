package com.banisaleh.stmik.inkubatormobile.Helper;

import com.banisaleh.stmik.inkubatormobile.Api_Service.ApiService;

import java.util.HashMap;

public class AppConstant {

    public static final String BASE_URL = "http://sinarindahmentariabadi.id/skripsi/index.php/";
    public static final String myPreferenced = "INKUBATOR_APP";
    public static int READ_TIMEOUT = 6000;
    public static int CONNECTION_TIMEOUT = 6000;
    public static final int REQUEST_LOCATION = 500;
    public final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    public final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    public static final int SETINTERVALLOCATION = 500;
    public static final int RELOAD_LOCAITON = 3000;
    public static final int SPALSH_SCREEN = 5000;
    public static final int READTIMEOUT = 5000;
    public static final int CONNECTIONTIMEOUT = 5000;
    public static final int MIN_JARAK = 10;

    private static ApiService svcServiceApi;
    public static HashMap<String, String> trxNotifications = new HashMap<String, String>();
    public static ApiService getServiceApi() {
        if (svcServiceApi != null) {
            return svcServiceApi;
        } else {
            svcServiceApi = new ApiService();
        }
        return svcServiceApi;
    }

}

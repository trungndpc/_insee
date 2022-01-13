package vn.insee.retailer.common;

import vn.insee.common.Constant;

public class AppCommon {
    public static final AppCommon INSTANCE = new AppCommon();
    public static final String AUTHEN_ZALO_URL = "";

    public long getOAId() {
        //Todo
        return 428332895304538762l;
    }

    public String getDomain() {
        //Todo
        return "https://cuahang.insee.udev.com.vn";
    }

    public long getZaloAppId() {
        //Todo
        return 191292518983577786l;
    }

    public String getSecretZaloApp() {
        //Todo
        return "yspZ48H6T8LKh68ReMIz";
    }

    public String getVersion() {
        //Todo
        return "1.0.0";
    }


}

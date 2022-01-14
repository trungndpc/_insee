package vn.insee.retailer.common;

import vn.insee.common.Constant;

public class AppCommon {
    public static final AppCommon INSTANCE = new AppCommon();

    public String getAuthenZaloUrl() {
        return String.format("https://oauth.zaloapp.com/v3/auth?app_id=%d&state=insee", getZaloAppId());
    }

    public long getOAId() {
        //Todo
        return 428332895304538762l;
    }

    public String getDomain() {
        //Todo
//        return "https://cuahang.insee.udev.com.vn";
        return "https://8df8-2402-800-63b8-e859-fb0e-3ea0-ae01-a76a.ngrok.io";
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

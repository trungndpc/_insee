package vn.insee.admin.retailer.common;


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
        return "https://cuahang.insee.udev.com.vn";
    }

    public long getZaloAppId() {
        //Todo
        return 1509917773870835507l;
    }

    public String getSecretZaloApp() {
        //Todo
        return "rVt8VXsrjHrDjT87c6NP";
    }

    public String getVersion() {
        //Todo
        return "1.0.0";
    }


}

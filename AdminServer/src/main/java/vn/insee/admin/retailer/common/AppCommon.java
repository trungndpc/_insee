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

    public String getAccessToken() {
        //Todo
        return  "KHSc0tA2u1rM6IW1VOBd5bqRBGbvcPrX6nWQFaFvaryw6YufJT3XDYaxA6fOv8Sp9Hym9MNP-6v7G6WCROdEJ4OMULSsZyqSJX1Y00thh61a46eG9Fx7KMHWOWqyeULwNbKkEY6CbcL2HYKI8AlYLMTYAIj1XfD8Qs8OL1gjWZT1IoXv0DNd9N01BtWduvSkOpibKqMkbqW1JmuEMhE840TcEdHSeuvYF5KUVnszjp59O2PV2u6CIK1RF1ShlRiLVoGpRLZT-G0iDdH2NDN83nmHNt1dzkqMEn1WSqcCgXGC4XPi4wp_Bt6Lw1a";
    }

}

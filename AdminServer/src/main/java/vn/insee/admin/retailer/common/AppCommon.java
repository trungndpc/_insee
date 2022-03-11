package vn.insee.admin.retailer.common;


public class AppCommon {
    public static final AppCommon INSTANCE = new AppCommon();

    public String getAuthenZaloUrl() {
        return String.format("https://oauth.zaloapp.com/v3/auth?app_id=%d&state=insee", getZaloAppId());
    }

    public long getOAId() {
        return 2222725635797558435l;
    }

    public String getDomain() {
        return "https://cuahang.insee.udev.com.vn";
    }

    public long getZaloAppId() {
        return 1509917773870835507l;
    }

    public String getSecretZaloApp() {
        return "rVt8VXsrjHrDjT87c6NP";
    }

    public String getVersion() {
        return "1.0.0";
    }

    public String getAccessToken() {
        return  "kSHIKb19Odg-obnGNqbbSRN8MLXRKcWhsxGNGK5QJM-il6a31L0iGBNH4H0BFtLlbVWlCNGeQ7dZommgSYmoQlEhMJOS9s9alC9JDomNErV0qGCyTrTOOTcrA39sNcbdtO0B1Yb3DMk2nmiG6XHkOxBkB3qQTsvxWV4B0myaCYcHv4Xq7GSIDAtYJLqY93KZdQOAL7HqHGZ8aGbY8MfmGvwQL6ez6Hesfk9cPZGVApAKoabHFY4M3xJwLYCQ9prvigDWAmvbM76PkGu_BJfIQ9dsAXftE6vAuReZ55ayRMAvnY5BsRG96LDUQdu";
    }

}

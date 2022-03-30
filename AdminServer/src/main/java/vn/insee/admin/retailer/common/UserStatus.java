package vn.insee.admin.retailer.common;

public class UserStatus {
    public static final int DISABLED = -1;
    public static final int WAIT_COMPLETE_PROFILE = 5;
    public static final int WAITING_ACTIVE = 7;
    public static final int WAIT_APPROVAL = 8;
    public static final int APPROVED = 10;

    public static String findByName(int id) {
        switch (id) {
            case - 1:
                return "Đã từ chối";
            case 5 :
                return "Chưa hoàn thành profile";
            case 7 :
                return "Chờ kích hoạt";
            case 8 :
                return "Chờ duyệt";
            case 10 :
                return "Đã duyệt";
        }
        return "";
    }


}

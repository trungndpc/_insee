package vn.insee.common.status;

public class StatusForm {
    public static final int INIT = 1;
    public static final int APPROVED = 2;
    public static final int REJECTED = 3;
    public static final int SENT_GIFT = 4;
    public static final int RECEIVED = 5;

    public static String findByName(int id) {
        switch (id) {
            case INIT:
                return  "Chờ duyệt";
            case APPROVED:
                return "Đã duyệt";
            case REJECTED:
                return "Từ chối";
            case SENT_GIFT:
                return "Đã gửi quà";
            case RECEIVED:
                return "Đã nhận quà";
        }
        return "";
    }
}

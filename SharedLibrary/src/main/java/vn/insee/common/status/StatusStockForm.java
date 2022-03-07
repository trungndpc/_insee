package vn.insee.common.status;

public class StatusStockForm {
    public static final int INIT = 1;
    public static final int APPROVED = 2;
    public static final int REJECTED = 3;

    public String findByName(int id) {
        switch (id) {
            case INIT:
                return  "Chờ duyệt";
            case APPROVED:
                return "Đã duyệt";
            case REJECTED:
                return "Từ chối";
        }
        return "";
    }
}

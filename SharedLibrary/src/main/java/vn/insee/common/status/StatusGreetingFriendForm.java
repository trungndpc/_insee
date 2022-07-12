package vn.insee.common.status;

public class StatusGreetingFriendForm {
    public static final int WAITING_SUBMIT_FORM = 0;
    public static final int WAITING_APPROVAL = 1;
    public static final int APPROVED = 2;
    public static final int REJECTED = 3;

    public String findByName(int id) {
        switch (id) {
            case WAITING_APPROVAL:
                return  "Chờ duyệt";
            case APPROVED:
                return "Đã duyệt";
            case REJECTED:
                return "Từ chối";
        }
        return "";
    }
}

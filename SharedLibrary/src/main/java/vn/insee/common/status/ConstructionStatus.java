package vn.insee.common.status;

public enum ConstructionStatus {
    WAITING_APPROVAL(1), APPROVED(2), REJECTED(3), SEND_GIFT(4), RECEIVED_GIFT(5), RE_SUBMIT(6), VERIFIED(7), NEED_APPROVAL_MORE(8);
    private int status;

    ConstructionStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static ConstructionStatus findByStatus(int status) {
        switch (status) {
            case 1 : return WAITING_APPROVAL;
            case 2 : return APPROVED;
            case 3 : return REJECTED;
            case 4 : return SEND_GIFT;
            case 5 : return RECEIVED_GIFT;
            case 6 : return RE_SUBMIT;
            case 7 : return VERIFIED;
            case 8 : return NEED_APPROVAL_MORE;
            default: return null;
        }
    }
}

package vn.insee.common.status;

public enum GiftStatus {
    SEND(1), RECEIVED(2), ROLLED(3), APPROVED(4), WAITING_SENT(5);
    private int status;

    GiftStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static GiftStatus findByValue(int status) {
        switch (status) {
            case 1 : return SEND;
            case 2 : return RECEIVED;
            case 3 : return ROLLED;
            case 4 : return APPROVED;
            case 5 : return WAITING_SENT;
        }
        return null;
    }
}

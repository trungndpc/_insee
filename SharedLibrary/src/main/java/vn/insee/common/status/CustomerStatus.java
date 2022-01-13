package vn.insee.common.status;

public enum CustomerStatus {
    REVIEWING(1), APPROVED(2), REJECTED(3);
    private int status;

    CustomerStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static CustomerStatus findByStatus(int status) {
        switch (status) {
            case 1 : return REVIEWING;
            case 2 : return APPROVED;
            case 3 : return REJECTED;
            default: return null;
        }
    }

}

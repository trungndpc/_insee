package vn.insee.common.status;

public enum BillStatus {
    WAITING_APPROVAL(1), APPROVED(2), REJECT(3) ;
    private int status;

    BillStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

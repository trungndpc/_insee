package vn.insee.common.status;

public enum StatusRegister {
    NOT_REGISTER(1), NOT_CUSTOMER(2), WAITING_REVIEW(3), REGISTERED(4), NEED_SUBMIT_AGAIN(5);

    private int status;

    StatusRegister(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

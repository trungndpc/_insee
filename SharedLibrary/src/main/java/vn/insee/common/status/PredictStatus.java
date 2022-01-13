package vn.insee.common.status;

public enum PredictStatus {
    INIT(1), LOCKED(2), FAILED(3), SUCCESS(4), SEND_GIFT(5), SUCCESS_BY_NOT_TOP(6);
    private int value;

    PredictStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

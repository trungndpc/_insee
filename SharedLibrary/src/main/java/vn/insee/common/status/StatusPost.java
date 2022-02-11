package vn.insee.common.status;

public enum PromotionStatus {
    INIT(1), EDITING(2), PUBLISHED(3), DELETED(4);

    PromotionStatus(int status) {
        this.status = status;
    }

    private int status;

    public int getStatus() {
        return status;
    }
}

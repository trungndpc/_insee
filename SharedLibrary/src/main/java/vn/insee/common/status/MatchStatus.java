package vn.insee.common.status;

public enum MatchStatus {
    INIT(1), PROCESSING(2), DONE(3);
    private int value;

    MatchStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

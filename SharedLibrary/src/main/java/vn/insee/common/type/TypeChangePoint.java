package vn.insee.common.type;

public enum TypeChangePoint {
    EXCHANGE_POINT(1);
    private int value;

    TypeChangePoint(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

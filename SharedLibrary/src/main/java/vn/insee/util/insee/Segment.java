package vn.insee.util.insee;

public enum  Segment {
    HOUSING_SOLUTION(1), BUILDING_INFRASTRUCTURE(2);
    private int value;

    Segment(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

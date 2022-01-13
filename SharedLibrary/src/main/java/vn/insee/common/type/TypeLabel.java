package vn.insee.common.type;

public enum TypeLabel {
    CONSTRUCTION(1);

    private int type;

    TypeLabel(int type) {
        this.type = type;
    }

    public static TypeLabel findByType(Integer type) {
        switch (type) {
            case 1 : return CONSTRUCTION;
            default: return null;
        }
    }

    public int getType() {
        return type;
    }
}

package vn.insee.common.type;

public enum  TypeConstruction {
    NEXT_CONSTRUCTION(1), NOW_CONSTRUCTION(2), NOW_CONSTRUCTION_V2(3), CONSTRUCTION_LOYALTY(4);
    private int type;

    TypeConstruction(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static TypeConstruction findByType(int type) {
        switch (type) {
            case 1 : return NEXT_CONSTRUCTION;
            case 2 : return NOW_CONSTRUCTION;
            case 3 : return NOW_CONSTRUCTION_V2;
            case 4 : return CONSTRUCTION_LOYALTY;
            default: return null;
        }
    }
}

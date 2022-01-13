package vn.insee.common.type;

public enum TypePromotion {
    NEXT_CONSTRUCTION(1), NOW_CONSTRUCTION(2), NOW_CONSTRUCTION_V2(3), PREDICT_FOOTBALL(4), LOYALTY(5), SHARE_LINK_REGISTER(6);
    private int type;

    TypePromotion(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static TypePromotion findByType(int type) {
        switch (type) {
            case 1 : return NEXT_CONSTRUCTION;
            case 2 : return NOW_CONSTRUCTION;
            case 3 : return NOW_CONSTRUCTION_V2;
            case 4 : return PREDICT_FOOTBALL;
            case 5 : return LOYALTY;
            case 6 : return SHARE_LINK_REGISTER;
            default: return null;
        }
    }
}

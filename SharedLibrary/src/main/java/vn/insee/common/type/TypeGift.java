package vn.insee.common.type;

public enum TypeGift {
    CARD_PHONE(1), LUCKY_DRAW_ROTATION(2), VOUCHER(3);
    private int type;

    TypeGift(int type) {
        this.type = type;
    }

    public static TypeGift findByValue(int type) {
        switch (type) {
            case 1 : return  CARD_PHONE;
            case 2 : return  LUCKY_DRAW_ROTATION;
            case 3 : return VOUCHER;
        }
        return null;
    }

    public int getType() {
        return type;
    }
}

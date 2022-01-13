package vn.insee.common.type;

public enum TypeNetworkBrand {
    VIETTEL(1), VINAPHONE(2), MOBIPHONE(3);

    private Integer id;

    TypeNetworkBrand(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

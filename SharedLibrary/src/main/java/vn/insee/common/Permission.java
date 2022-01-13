package vn.insee.common;

public enum Permission {

    ADMIN(1, "Admin"),
    CONSTRUCTOR(2, "Constructor"),
    RETAILER(3, "Retailer"),
    ANONYMOUS(0, "Anonymous");

    private int id;
    private String name;

    Permission(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Permission findById(int id) {
        switch (id) {
            case 1 : return ADMIN;
            case 2 : return CONSTRUCTOR;
            case 3 : return RETAILER;
            default: return ANONYMOUS;
        }
    }
}

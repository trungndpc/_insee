package vn.insee.common.status;

public enum ImageStatus {
    WAITING_APPROVAL(1), APPROVED(2), REJECT(3) ;
    private int status;

    ImageStatus(int status) {
        this.status = status;
    }

    public static ImageStatus findByStatus(int status) {
        switch (status) {
            case 1 : return WAITING_APPROVAL;
            case 2 : return APPROVED;
            case 3 : return REJECT;
            default: return null;
        }
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

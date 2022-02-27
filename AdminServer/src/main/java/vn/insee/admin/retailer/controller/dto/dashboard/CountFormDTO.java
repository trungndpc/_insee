package vn.insee.admin.retailer.controller.dto.dashboard;

public class CountFormDTO {
    private int total;
    private int init;
    private int approved;
    private int sendGift;
    private int receivedGift;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getInit() {
        return init;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getSendGift() {
        return sendGift;
    }

    public void setSendGift(int sendGift) {
        this.sendGift = sendGift;
    }

    public int getReceivedGift() {
        return receivedGift;
    }

    public void setReceivedGift(int receivedGift) {
        this.receivedGift = receivedGift;
    }
}

package vn.insee.admin.retailer.controller.dto.dashboard;

public class CountGiftDTO {
    private int total;
    private int send;
    private int received;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSend() {
        return send;
    }

    public void setSend(int send) {
        this.send = send;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }
}

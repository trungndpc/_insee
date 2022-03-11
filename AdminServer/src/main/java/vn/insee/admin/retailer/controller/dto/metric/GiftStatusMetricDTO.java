package vn.insee.admin.retailer.controller.dto.metric;

public class GiftStatusMetricDTO {
    private int sent;
    private int received;

    public int getSent() {
        return sent;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }
}

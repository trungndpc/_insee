package vn.insee.admin.retailer.controller.dto.metric;

public class UserDataMetricDTO {
    private String date;
    private int total;

    public UserDataMetricDTO() {
    }

    public UserDataMetricDTO(String date, int total) {
        this.date = date;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

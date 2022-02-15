package vn.insee.admin.retailer.controller.dto.metric;

public class FormDateMetricDTO {
    private String date;
    private int total;

    public FormDateMetricDTO() {
    }

    public FormDateMetricDTO(String date, int total) {
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

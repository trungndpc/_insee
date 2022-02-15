package vn.insee.admin.retailer.controller.dto.metric;

public class FormCityMetricDTO {
    private int total;
    private int city;

    public FormCityMetricDTO() {
    }

    public FormCityMetricDTO(int total, int city) {
        this.total = total;
        this.city = city;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }
}

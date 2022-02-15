package vn.insee.admin.retailer.controller.dto.metric;

public class UserCityMetricDTO {
    private int total;
    private int city;

    public UserCityMetricDTO() {
    }

    public UserCityMetricDTO(int total, int city) {
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

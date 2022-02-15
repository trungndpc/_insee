package vn.insee.jpa.metric;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCityMetric {
    private int total;
    private int city;

    public UserCityMetric() {
    }

    public UserCityMetric(int total, int city) {
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

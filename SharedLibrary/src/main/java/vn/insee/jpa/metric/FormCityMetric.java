package vn.insee.jpa.metric;

public class FormCityMetric {
    private int total;
    private int city;

    public FormCityMetric() {
    }

    public FormCityMetric(int city, int total) {
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

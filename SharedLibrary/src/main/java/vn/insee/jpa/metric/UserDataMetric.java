package vn.insee.jpa.metric;

public class UserDataMetric {
    private String date;
    private int total;

    public UserDataMetric() {
    }

    public UserDataMetric(String date, int total) {
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

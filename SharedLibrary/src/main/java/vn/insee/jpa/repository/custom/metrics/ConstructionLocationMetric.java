package vn.insee.jpa.repository.custom.metrics;

public class ConstructionLocationMetric {
    private int location;
    private int total;

    public ConstructionLocationMetric(int location, int total) {
        this.location = location;
        this.total = total;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

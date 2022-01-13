package vn.insee.jpa.repository.custom.metrics;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerLocationMetric {
    private int total;
    @JsonProperty(value = "location_id")
    private int locationId;

    public CustomerLocationMetric(int locationId, int total) {
        this.total = total;
        this.locationId = locationId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}

package vn.insee.jpa.repository.custom.metrics;

public class SuccessPredictMetric {
    private int customerId;
    private int amount;


    public SuccessPredictMetric(int customerId, int amount) {
        this.customerId = customerId;
        this.amount = amount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

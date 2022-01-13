package vn.insee.util.insee;

public class INSEEMessage {
    private int tree;
    private double reduce;
    private int productId;
    private double bags;

    public INSEEMessage(int tree, double reduce, int productId, double bags) {
        this.tree = tree;
        this.reduce = reduce;
        this.productId = productId;
        this.bags = bags;
    }

    public int getTree() {
        return tree;
    }

    public void setTree(int tree) {
        this.tree = tree;
    }

    public double getReduce() {
        return reduce;
    }

    public void setReduce(double reduce) {
        this.reduce = reduce;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getBags() {
        return bags;
    }

    public void setBags(double bags) {
        this.bags = bags;
    }
}

package vn.insee.util.insee;

public class Product {
    private final int id;
    private final String name;
    private final int inseeVolumeExhaust;
    private final int commonVolumeExhaust;
    private final int volumeOneBag;
    private final Segment segment;

    public Product(int id, String name, int inseeVolumeExhaust, int commonVolumeExhaust, int volumeOneBag, Segment segment) {
        this.id = id;
        this.name = name;
        this.inseeVolumeExhaust = inseeVolumeExhaust;
        this.commonVolumeExhaust = commonVolumeExhaust;
        this.volumeOneBag = volumeOneBag;
        this.segment = segment;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getInseeVolumeExhaust() {
        return inseeVolumeExhaust;
    }

    public int getCommonVolumeExhaust() {
        return commonVolumeExhaust;
    }

    public int getVolumeOneBag() {
        return volumeOneBag;
    }

    public Segment getSegment() {
        return segment;
    }
}

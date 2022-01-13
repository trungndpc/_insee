package vn.insee.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "bill", schema="promotion")
public class BillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String link;
    private int status;
    private int constructionId;
    private Integer volumeCiment;
    private String labelId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(int constructionId) {
        this.constructionId = constructionId;
    }

    public Integer getVolumeCiment() {
        return volumeCiment;
    }

    public void setVolumeCiment(Integer volumeCiment) {
        this.volumeCiment = volumeCiment;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }
}

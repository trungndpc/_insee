package vn.insee.jpa.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import vn.insee.jpa.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "promotion", schema = "public")
@TypeDef(name = "list-array",typeClass = ListArrayType.class)
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class PromotionEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private int type;
    private Integer status;
    private Long timeStart;
    private Long timeEnd;

    @Type(type = "list-array")
    @Column(name = "cements",columnDefinition = "integer[]")
    private List<Integer> cements;

    @Type(type = "list-array")
    @Column(name = "locations",columnDefinition = "integer[]")
    private List<Integer> locations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public List<Integer> getCements() {
        return cements;
    }

    public void setCements(List<Integer> cements) {
        this.cements = cements;
    }

    public List<Integer> getLocations() {
        return locations;
    }

    public void setLocations(List<Integer> locations) {
        this.locations = locations;
    }
}

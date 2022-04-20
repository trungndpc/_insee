package vn.insee.jpa.entity;


import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import vn.insee.jpa.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "post", schema = "public")
@TypeDef(name = "list-array",typeClass = ListArrayType.class)
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String cover;
    private String content;
    private String summary;
    private Integer status;
    private Integer promotionId;
    private Long timeStart;
    private Long timeEnd;
    @Type(type = "list-array")
    @Column(name = "city_ids",columnDefinition = "integer[]")
    private List<Integer> cityIds;

    @Type(type = "list-array")
    @Column(name = "district_ids",columnDefinition = "integer[]")
    private List<Integer> districtIds;

    private int userTarget;


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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
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

    public List<Integer> getCityIds() {
        return cityIds;
    }

    public void setCityIds(List<Integer> cityIds) {
        this.cityIds = cityIds;
    }

    public List<Integer> getDistrictIds() {
        return districtIds;
    }

    public void setDistrictIds(List<Integer> districtIds) {
        this.districtIds = districtIds;
    }

    public int getUserTarget() {
        return userTarget;
    }

    public void setUserTarget(int userTarget) {
        this.userTarget = userTarget;
    }
}

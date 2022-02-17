package vn.insee.jpa.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import vn.insee.jpa.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "broadcast", schema = "public")
@TypeDef(name = "list-array",typeClass = ListArrayType.class)
public class BroadcastEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private long timeStart;
    private int status;
    private int type;

    @Type(type = "list-array")
    @Column(name = "user_ids", columnDefinition = "integers[]")
    private List<Integer> userIds;

    @Type(type = "list-array")
    @Column(name = "city_ids", columnDefinition = "integers[]")
    private List<Integer> cityIds;

    @Type(type = "list-array")
    @Column(name = "district_ids", columnDefinition = "integers[]")
    private List<Integer> districtIds;
    private Integer postId;
    private Integer seen;
    private Integer click;
    private String jobId;
    private Integer totalUids;
    private Integer totalUidsAfterBuildUser;
    private Integer totalUidsSuccessSend;

    @Type(type = "list-array")
    @Column(name = "clickers", columnDefinition = "integers[]")
    private List<Integer> clickers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getSeen() {
        return seen;
    }

    public void setSeen(Integer seen) {
        this.seen = seen;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getTotalUids() {
        return totalUids;
    }

    public void setTotalUids(Integer totalUids) {
        this.totalUids = totalUids;
    }

    public Integer getTotalUidsAfterBuildUser() {
        return totalUidsAfterBuildUser;
    }

    public void setTotalUidsAfterBuildUser(Integer totalUidsAfterBuildUser) {
        this.totalUidsAfterBuildUser = totalUidsAfterBuildUser;
    }

    public Integer getTotalUidsSuccessSend() {
        return totalUidsSuccessSend;
    }

    public void setTotalUidsSuccessSend(Integer totalUidsSuccessSend) {
        this.totalUidsSuccessSend = totalUidsSuccessSend;
    }

    public List<Integer> getClickers() {
        return clickers;
    }

    public void setClickers(List<Integer> clickers) {
        this.clickers = clickers;
    }
}

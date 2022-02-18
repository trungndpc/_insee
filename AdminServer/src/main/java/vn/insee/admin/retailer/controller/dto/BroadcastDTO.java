package vn.insee.admin.retailer.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BroadcastDTO {
    private int id;
    private String name;
    private long timeStart;
    private int status;
    private int type;
    private List<Integer> userIds;
    private Integer postId;
    private Integer seen;
    private Integer click;
    private List<Integer> cityIds;
    private List<Integer> districtIds;
    private Integer totalUids;
    private Integer totalUidsAfterBuildUser;
    private Integer totalUidsSuccessSend;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}

package vn.insee.admin.retailer.woker;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostNormalBroadcastTask {
    private List<Integer> uids;
    private int postId;
    private int broadcastId;

    public List<Integer> getUids() {
        return uids;
    }

    public void setUids(List<Integer> uids) {
        this.uids = uids;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(int broadcastId) {
        this.broadcastId = broadcastId;
    }
}

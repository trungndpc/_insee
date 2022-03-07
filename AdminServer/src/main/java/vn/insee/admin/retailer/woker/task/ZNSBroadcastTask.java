package vn.insee.admin.retailer.woker.task;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZNSBroadcastTask {
    private List<Integer> uids;
    private int broadcastId;

    public List<Integer> getUids() {
        return uids;
    }

    public void setUids(List<Integer> uids) {
        this.uids = uids;
    }

    public int getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(int broadcastId) {
        this.broadcastId = broadcastId;
    }
}

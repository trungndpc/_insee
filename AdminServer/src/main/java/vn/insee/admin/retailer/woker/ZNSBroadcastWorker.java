package vn.insee.admin.retailer.woker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.service.BroadcastService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.admin.retailer.woker.task.ZNSBroadcastTask;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.common.status.StatusBroadcast;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.BroadcastEntity;
import vn.insee.jpa.entity.UserEntity;

import java.util.List;

@Component
public class ZNSBroadcastWorker {
    private static final String TEMPLATE_ID = "221294";
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private BroadcastService broadcastService;

    @Job(name = "ZNS_BROADCAST", retries = 1)
    public void execute(ZNSBroadcastTask task) {
        int totalUid = 0;
        int totalUidAfterBuildUser = 0;
        int totalSuccessSend = 0;
        int error = 0;
        BroadcastEntity broadcastEntity = broadcastService.get(task.getBroadcastId());
        try{
            totalUid = task.getUids().size();
            List<Integer> uids = task.getUids();
            for (Integer uid: uids) {
                UserEntity userEntity = userService.findById(uid);
                if (userEntity.getStatus() == StatusUser.WAITING_ACTIVE) {
                    totalUidAfterBuildUser++;
                    JSONObject data = new JSONObject();
                    data.put("customer_name", userEntity.getName());
                    data.put("product_name", "Xi măng");
                    data.put("customer_code", userEntity.getInseeId());
                    data.put("order_date", "01/01/2022");
                    String phone = userEntity.getPhone();
                    if (phone.startsWith("0")) {
                        phone = phone.replaceFirst("0", "84");
                    }
                    LOGGER.error("SEND................: " + phone);
                    ZaloService.INSTANCE.sendZNS(phone, TEMPLATE_ID, userEntity.getPhone(), data);
                    totalSuccessSend++;
                }
            }
        }catch (Exception e) {
            error = -1;
            LOGGER.error(e.getMessage(), e);
        }finally {
            broadcastEntity.setTotalUids(totalUid);
            broadcastEntity.setTotalUidsAfterBuildUser(totalUidAfterBuildUser);
            broadcastEntity.setTotalUidsSuccessSend(totalSuccessSend);
            if (error == 0) {
                broadcastEntity.setStatus(StatusBroadcast.DONE);
            }else {
                broadcastEntity.setStatus(StatusBroadcast.FAILED);
            }
            broadcastService.update(broadcastEntity);
        }
    }

}

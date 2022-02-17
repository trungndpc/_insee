package vn.insee.admin.retailer.woker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.common.UserStatus;
import vn.insee.admin.retailer.message.PostBroadcastMessage;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.service.BroadcastService;
import vn.insee.admin.retailer.service.PostService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.common.Constant;
import vn.insee.common.status.StatusBroadcast;
import vn.insee.jpa.entity.BroadcastEntity;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private PostService postService;

    @Job(name = "BROADCAST", retries = 1)
    public void execute(PostNormalBroadcastTask task) {
        int totalUid = 0;
        int totalUidAfterBuildUser = 0;
        int totalSuccessSend = 0;
        int error = 0;
        BroadcastEntity broadcastEntity = broadcastService.get(task.getBroadcastId());
        try{
            totalUid = task.getUids().size();
            List<Integer> uids = task.getUids();
            List<User> users = uids.stream().map(this::getUser).filter(e -> e != null)
                    .collect(Collectors.toList());
            totalUidAfterBuildUser = users.size();
            PostEntity postEntity = postService.get(task.getPostId());
            for (User user: users) {
                PostBroadcastMessage postBroadcastMessage = new PostBroadcastMessage(user);
                postBroadcastMessage.setImg(postEntity.getCover());
                postBroadcastMessage.setLink(Constant.CLIENT_DOMAIN + "/bai-viet/" + postEntity.getId() + "?brcId=" + broadcastEntity.getId());
                postBroadcastMessage.setSummary(postEntity.getSummary());
                postBroadcastMessage.setTitle(postEntity.getTitle());
                if (postBroadcastMessage.send()) {
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

    private User getUser(int uid) {
        try{
            UserEntity userEntity = userService.findById(uid);
            if (userEntity.getStatus() != UserStatus.APPROVED || userEntity.getFollowerId() == null) {
                throw new Exception("user is not valid to send broadcast uid: " + uid);
            }
            return new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

}

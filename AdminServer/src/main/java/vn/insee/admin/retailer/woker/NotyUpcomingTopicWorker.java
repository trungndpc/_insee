package vn.insee.admin.retailer.woker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.admin.retailer.message.Before5MinMessage;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.service.LightingQuizPromotionService;
import vn.insee.admin.retailer.service.PostService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;

import java.util.List;
import java.util.Optional;


@Service
public class NotyUpcomingTopicWorker {
    private static final Logger LOGGER = LogManager.getLogger(NotyUpcomingTopicWorker.class);

    @Autowired
    private LightingQuizPromotionService promotionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Job(name = "NOTY_UPCOMING_TOPIC", retries = 1)
    public void execute(NotyUpcomingTopicTask task) {
        try{
            int promotionId = task.getPromotionId();
            String topicId = task.getTopicId();

            LightingQuizPromotionEntity promotionEntity = promotionService.get(promotionId);
            if (promotionEntity == null) {
                throw new Exception("promotion is not exit: " + promotionId);
            }
            List<TopicDTO> listTopicDTO = promotionService.getListTopicDTO(promotionEntity);
            if (listTopicDTO == null) {
                throw new Exception("topics is null " + promotionId);
            }
            Optional<TopicDTO> opTopicDTO = listTopicDTO.stream().filter(topic -> topic.getId().equals(topicId)).findFirst();
            if (!opTopicDTO.isPresent()) {
                throw new Exception("topic is not exits " + topicId);
            }

            List<Integer> cityIds = promotionEntity.getCityIds();
            List<Integer> districtIds = promotionEntity.getDistrictIds();

            List<UserEntity> userEntities = userService.findBy(cityIds, districtIds, StatusUser.APPROVED);
            if (userEntities != null) {
                userEntities.stream().forEach(userEntity -> {
                    sendUser(promotionId, opTopicDTO.get(), userEntity);
                });
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void sendUser(int promotionId, TopicDTO topic, UserEntity userEntity) {
        try{
            User user = new User();
            user.setFollowerId(userEntity.getFollowerId());
            user.setName(userEntity.getName());
            user.setUid(user.getUid());
            PostEntity post = postService.findPost(promotionId, userEntity);
            Before5MinMessage msg = new Before5MinMessage(user, topic.getTitle(), post != null ? post.getId() : 0, topic.getTimeStart());
            msg.send();
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }




}

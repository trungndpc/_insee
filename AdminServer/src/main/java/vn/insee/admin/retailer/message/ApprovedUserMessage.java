package vn.insee.admin.retailer.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.admin.retailer.wrapper.entity.ZaloMessage;


public class TopLeaderBoardLightingQuizMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger(TopLeaderBoardLightingQuizMessage.class);
    private String title;
    public TopLeaderBoardLightingQuizMessage(User user, String title) {
        super(user);
        this.title = title;
    }

    @Override
    public boolean send() {
        try{
            ZaloMessage message = buildMsg();
            ZaloService.INSTANCE.send(getUser().getFollowerId(), message);
            return true;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    private ZaloMessage buildMsg() {
        ZaloMessage zaloMessage = ZaloMessage.toTextMessage("Chúc mừng anh chị đã nằm trong top 10 người " +
                "trả lời đúng và nhanh nhất của chủ đề: " + title);
        return zaloMessage;
    }
}

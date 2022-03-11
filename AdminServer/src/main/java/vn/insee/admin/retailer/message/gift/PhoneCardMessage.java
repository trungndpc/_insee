package vn.insee.admin.retailer.message.gift;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.admin.retailer.common.AppCommon;
import vn.insee.admin.retailer.message.Message;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.admin.retailer.wrapper.entity.ZaloMessage;

import java.util.Arrays;

public class PhoneCardMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger(PhoneCardMessage.class);
    private static final String TITLE = "Chúc mừng !!!";
    private static final String SUB_TITLE_FORMAT = "Chúc mừng quý cửa hàng %s đã nhận được thẻ điện thoại %s từ chương trình khuyến " +
            "mãi của câu lạc bộ INSEE Cửa Hàng Ngoại Hạng. Vui lòng bấm vào tin nhắn để nhận được phần thưởng.";
    private String storeName;
    private String giftTitle;
    private int giftId;

    public PhoneCardMessage(User user, String storeName, String giftTitle, int giftId) {
        super(user);
        this.storeName = storeName;
        this.giftTitle = giftTitle;
        this.giftId = giftId;
    }

    public ZaloMessage build() {
        String subTitle = String.format(SUB_TITLE_FORMAT, storeName, giftTitle);
        ZaloMessage.Attachment.Payload.Element.Action action = new ZaloMessage.Attachment.Payload.Element.Action();
        action.type = "oa.open.url";
        action.url = AppCommon.INSTANCE.getDomain() + "/qua-tang/" + giftId;

        ZaloMessage.Attachment.Payload.Element element = new ZaloMessage.Attachment.Payload.Element();
        element.title = TITLE;
        element.subtitle = subTitle;
        element.imageUrl =  "https://media.istockphoto.com/vectors/gift-card-with-red-bow-vector-id865405838?k=20&m=865405838&s=612x612&w=0&h=UYCSnYgcJH0ln-n8D6FlubgnMnpOq2PzaQOG8FOou8o=";
        element.action = action;
        return ZaloMessage.toListTemplate(Arrays.asList(element));
    }

    @Override
    public boolean send() {
        try{
            ZaloMessage message = build();
            ZaloService.INSTANCE.send(getUser().getFollowerId(), message);
            return true;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}

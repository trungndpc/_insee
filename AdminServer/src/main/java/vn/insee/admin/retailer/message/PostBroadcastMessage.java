package vn.insee.admin.retailer.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.util.UriUtils;
import vn.insee.admin.retailer.wrapper.ZaloService;
import vn.insee.admin.retailer.wrapper.entity.ZaloMessage;

import java.util.LinkedList;
import java.util.List;

public class PostBroadcastMessage extends Message {
    private static final Logger LOGGER = LogManager.getLogger(PostBroadcastMessage.class);
    private String title;
    private String summary;
    private String link;
    private String img;

    public PostBroadcastMessage(User user) {
        super(user);
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
        List<ZaloMessage.Attachment.Payload.Element> elements = new LinkedList<>();
        ZaloMessage.Attachment.Payload.Element.Action ctrAction = new ZaloMessage.Attachment.Payload.Element.Action();
        ctrAction.type = "oa.open.url";
        ctrAction.url = link;
        ZaloMessage.Attachment.Payload.Element ctrElement = new ZaloMessage.Attachment.Payload.Element();
        ctrElement.title = title;
        ctrElement.imageUrl = UriUtils.encodePath(img, "UTF-8");
        ctrElement.action = ctrAction;
        ctrElement.subtitle = summary;
        elements.add(ctrElement);
        return ZaloMessage.toListTemplate(elements);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

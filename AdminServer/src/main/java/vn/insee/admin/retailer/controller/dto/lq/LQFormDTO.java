package vn.insee.admin.retailer.controller.dto.lq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import vn.insee.admin.retailer.controller.dto.FormDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LQFormDTO extends FormDTO {

    @JsonRawValue
    private String jsonDetail;
    private int point;
    private String topicId;

    public String getJsonDetail() {
        return jsonDetail;
    }

    public void setJsonDetail(String jsonDetail) {
        this.jsonDetail = jsonDetail;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}

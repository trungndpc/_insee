package vn.insee.retailer.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.insee.util.NoiseUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDTO {
    private String id;
    private String content;
    private boolean right;

    private void checkAndGenID(String id) {
        if (this.id == null || this.id.isEmpty() || !this.id.startsWith("answer_id_")) {
            this.id = "answer_id_" + NoiseUtil.random();
        }else {
            this.id = id;
        }
    }

    public String getId() {
        checkAndGenID(this.id);
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}

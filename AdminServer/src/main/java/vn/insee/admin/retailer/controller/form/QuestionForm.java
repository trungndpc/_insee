package vn.insee.admin.retailer.controller.form;


import vn.insee.admin.retailer.controller.dto.AnswerDTO;
import vn.insee.util.NoiseUtil;

import java.util.List;

public class QuestionForm {
    private String id;
    private String content;
    private List<AnswerDTO> options;

    private void checkAndGenID(String id) {
        if (this.id == null || this.id.isEmpty() || !this.id.startsWith("question_id_")) {
            this.id = "question_id_" + NoiseUtil.random();
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

    public List<AnswerDTO> getOptions() {
        return options;
    }

    public void setOptions(List<AnswerDTO> options) {
        this.options = options;
    }
}
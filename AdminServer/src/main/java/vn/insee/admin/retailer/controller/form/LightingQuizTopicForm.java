package vn.insee.admin.retailer.controller.form;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.admin.retailer.controller.dto.QuestionDTO;
import vn.insee.util.NoiseUtil;

import java.util.List;

public class LightingQuizTopicForm {
    private static final Logger LOGGER = LogManager.getLogger();
    private String id;
    private String title;
    private List<QuestionDTO> questions;
    private Long timeStart;
    private Long timeEnd;

    private void checkAndGenID(String id) {
        if (this.id == null || this.id.isEmpty() || !this.id.startsWith("topic_id_")) {
            this.id = "topic_id_" + NoiseUtil.random();
        }else {
            this.id = id;
        }
    }

    public String getId() {
        checkAndGenID(this.id);
        return this.id;
    }

    public void setId(String id) {
        checkAndGenID(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Long timeEnd) {
        this.timeEnd = timeEnd;
    }
}

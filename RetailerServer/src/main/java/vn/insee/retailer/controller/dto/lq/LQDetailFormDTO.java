package vn.insee.retailer.controller.dto.lq;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LQDetailFormDTO {
    private String topicId;
    private long timeStart;
    private long timeEnd;
    private List<LQQuestionFormDTO> questions;

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public List<LQQuestionFormDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<LQQuestionFormDTO> questions) {
        this.questions = questions;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}

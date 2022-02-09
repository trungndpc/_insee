package vn.insee.retailer.bot.script;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import vn.insee.common.status.StatusForm;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;
import vn.insee.retailer.bot.LightingSession;
import vn.insee.retailer.bot.Question;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.question.LQQuestion;
import vn.insee.retailer.bot.question.LightingQuizGameRatioQuestion;
import vn.insee.retailer.bot.question.ReadyToStartLightingQuizQuestion;
import vn.insee.retailer.controller.dto.QuestionDTO;
import vn.insee.retailer.controller.dto.TopicDTO;
import vn.insee.retailer.controller.dto.lq.LQDetailFormDTO;
import vn.insee.retailer.controller.dto.lq.LQQuestionFormDTO;
import vn.insee.retailer.service.LightingQuizFormService;
import vn.insee.retailer.service.LightingQuizPromotionService;
import vn.insee.retailer.util.BeanUtil;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;

import java.util.*;
import java.util.stream.Collectors;

public class LightingQuizScript  {
    private LightingSession session;
    private User user;
    private ObjectMapper objectMapper = new ObjectMapper();
    private TopicDTO topic;
    private int promotionId;
    private final LightingQuizPromotionService lightingQuizService = BeanUtil.getBean(LightingQuizPromotionService.class);
    private final LightingQuizFormService lightingQuizFormService = BeanUtil.getBean(LightingQuizFormService.class);

    public LightingQuizScript(User user) {
        this.user = user;
    }

    public void start() throws JsonProcessingException {
        this.session = new LightingSession();
        this.promotionId = promotionId;
        this.topic = topic;
        ReadyToStartLightingQuizQuestion startQuestion = new ReadyToStartLightingQuizQuestion(user);
        startQuestion.ask();
        this.session.setWaitingQuestionId(startQuestion.getQuestionId());
        Map<String, LightingSession.LQQuestionSS> questions = this.session.getQuestion();
        if (questions == null) {
            questions = new HashMap<>();
        }
        LightingSession.LQQuestionSS lqQuestionSS = new LightingSession.LQQuestionSS();
        lqQuestionSS.setJson(new JSONObject(this.objectMapper.writeValueAsString(startQuestion)));
        lqQuestionSS.setZclass(ReadyToStartLightingQuizQuestion.class);
        questions.put(startQuestion.getQuestionId(), lqQuestionSS);
        this.session.setQuestion(questions);
        this.session.setPromotionId(promotionId);
        this.session.setTimeStart(System.currentTimeMillis());
    }

    public void process(ZaloWebhookMessage msg) throws Exception {
        String waitingQuestionId = session.getWaitingQuestionId();
        if (waitingQuestionId != null) {
            Question question = getWaitingQuestion(waitingQuestionId);
            if (question == null) {
                throw new Exception("can not init waiting question | " + waitingQuestionId);
            }
            boolean isAccept = question.ans(msg);

            //store session
            String jsonQuestion = this.objectMapper.writeValueAsString(question);
            Map<String, LightingSession.LQQuestionSS> mapSession = session.getQuestion();
            LightingSession.LQQuestionSS lqQuestionSS = new LightingSession.LQQuestionSS();
            lqQuestionSS.setZclass(question.getClass());
            lqQuestionSS.setJson(new JSONObject(jsonQuestion));
            mapSession.put(waitingQuestionId, lqQuestionSS);
            session.setQuestion(mapSession);

            if (isAccept) {
                Question nextQuestion = getNextQuestion();
                if (nextQuestion == null) {
                    //submit
                    submit();
                }else {
                    boolean ask = nextQuestion.ask();
                    if (ask) {
                        //store session
                        session.setWaitingQuestionId(((LQQuestion)nextQuestion).getQuestionId());
                        LightingSession.LQQuestionSS nextLqQuestionSS = new LightingSession.LQQuestionSS();
                        nextLqQuestionSS.setZclass(nextQuestion.getClass());
                        nextLqQuestionSS.setJson(new JSONObject(this.objectMapper.writeValueAsString(nextQuestion)));
                        mapSession.put(session.getWaitingQuestionId(), nextLqQuestionSS);
                        session.setQuestion(mapSession);
                    }
                }
            }
        }
    }

    private void submit() throws JsonProcessingException {
        Map<String, LightingSession.LQQuestionSS> question = session.getQuestion();
        LQDetailFormDTO lqDetailFormDTO = new LQDetailFormDTO();
        List<LQQuestionFormDTO> formDTOS = new ArrayList<>();
        for (String id: question.keySet()) {
            LightingSession.LQQuestionSS lqQuestionSS = question.get(id);
            if (lqQuestionSS.getZclass() == LightingQuizGameRatioQuestion.class) {
                LightingQuizGameRatioQuestion lightingQuizGameRatioQuestion = this.objectMapper.readValue(lqQuestionSS.getJson().toString(),
                        LightingQuizGameRatioQuestion.class);
                LQQuestionFormDTO lqQuestionFormDTO = new LQQuestionFormDTO();
                lqQuestionFormDTO.setId(lightingQuizGameRatioQuestion.getQuestionId());
                lqQuestionFormDTO.setTrue(lightingQuizGameRatioQuestion.getTrueAns().equals(lqQuestionFormDTO.getAnswer()));
                lqQuestionFormDTO.setAnswer(lightingQuizGameRatioQuestion.getUserAnswer().toString());
                formDTOS.add(lqQuestionFormDTO);
            }
        }
        lqDetailFormDTO.setQuestions(formDTOS);
        lqDetailFormDTO.setTimeStart(session.getTimeStart());
        lqDetailFormDTO.setTimeEnd(System.currentTimeMillis());
        lqDetailFormDTO.setTopicId(topic.getId());

        LightingQuizFormEntity lightingQuizFormEntity = new LightingQuizFormEntity();
        lightingQuizFormEntity.setJsonDetail(this.objectMapper.writeValueAsString(lqDetailFormDTO));
        lightingQuizFormEntity.setPromotionId(promotionId);
        lightingQuizFormEntity.setType(TypePromotion.LIGHTING_QUIZ_GAME_PROMOTION_TYPE);
        lightingQuizFormEntity.setStatus(StatusForm.INIT);
        lightingQuizFormEntity.setUserId(this.user.getUid());
        lightingQuizFormService.submit(lightingQuizFormEntity);
    }

    private Question getNextQuestion() {
        Set<String> topics = session.getQuestion().keySet();
        Optional<QuestionDTO> opQuestion = topic.getQuestions().stream().filter(t -> !topics.contains(t.getId()))
                .findAny();
        if (!opQuestion.isPresent()) {
            return null;
        }
        QuestionDTO question = opQuestion.get();
        List<String> options = question.getOptions().stream().map(o -> o.getContent()).collect(Collectors.toList());
        String trueAns = question.getOptions().stream().filter(o -> o.isRight()).findAny().get().getContent();

        LightingQuizGameRatioQuestion ratioQuestion = new LightingQuizGameRatioQuestion(user);
        ratioQuestion.setQuestionId(question.getId());
        ratioQuestion.setPromotionId(promotionId);
        ratioQuestion.setTrueAns(trueAns);
        ratioQuestion.setContent(question.getContent());
        ratioQuestion.setOptions(options);
        return ratioQuestion;
    }

    private Question getWaitingQuestion(String id) throws JsonProcessingException {
        LightingSession.LQQuestionSS lqQuestionSS = session.getQuestion().get(id);
        switch (lqQuestionSS.getZclass().getSimpleName()) {
            case "ReadyToStartLightingQuizQuestion":
                return this.objectMapper.readValue(lqQuestionSS.getJson().toString(),
                        ReadyToStartLightingQuizQuestion.class);
            case "LightingQuizGameRatioQuestion":
                return this.objectMapper.readValue(lqQuestionSS.getJson().toString(),
                        LightingQuizGameRatioQuestion.class);
        }
        return null;
    }

    public LightingSession getSession() {
        return session;
    }


}

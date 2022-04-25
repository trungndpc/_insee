package vn.insee.retailer.bot.script;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import vn.insee.common.status.StatusLightingQuizForm;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.retailer.bot.LightingSession;
import vn.insee.retailer.bot.Question;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.message.CompleteGameMessage;
import vn.insee.retailer.bot.question.LightingQuizGameRatioQuestion;
import vn.insee.retailer.bot.question.ReadyToStartLightingQuizQuestion;
import vn.insee.retailer.controller.dto.QuestionDTO;
import vn.insee.retailer.controller.dto.TopicDTO;
import vn.insee.retailer.controller.dto.lq.LQDetailFormDTO;
import vn.insee.retailer.controller.dto.lq.LQQuestionFormDTO;
import vn.insee.retailer.service.LightingQuizFormService;
import vn.insee.retailer.service.LightingQuizPromotionService;
import vn.insee.retailer.util.BeanUtil;
import vn.insee.retailer.util.StringUtils;
import vn.insee.retailer.webhook.WebhookSessionManager;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;

import java.util.*;
import java.util.stream.Collectors;

public class LightingQuizScript  {
    private static final Logger LOGGER = LogManager.getLogger();
    private final static LightingQuizPromotionService LIGHTING_QUIZ_PROMOTION_SERVICE = BeanUtil.getBean(LightingQuizPromotionService.class);
    private final static LightingQuizFormService LIGHTING_QUIZ_FORM_SERVICE = BeanUtil.getBean(LightingQuizFormService.class);
    private final static WebhookSessionManager WEBHOOK_SESSION_MANAGER = BeanUtil.getBean(WebhookSessionManager.class);

    private LightingSession session;
    private User user;
    private ObjectMapper objectMapper = new ObjectMapper();
    private TopicDTO topic;
    private LightingQuizPromotionEntity promotion;


    public LightingQuizScript(User user) throws JsonProcessingException {
        this.user = user;
        initSession(user);
    }

    private void initSession(User user) throws JsonProcessingException {
        Object currentSession = WEBHOOK_SESSION_MANAGER.getCurrentSession(user.getUid());
        if (currentSession == null || !(currentSession instanceof LightingSession)) {
            this.session = new LightingSession();
        }else {
            this.session = (LightingSession) currentSession;
        }
        if (this.session.getPromotionId() != 0 && this.session.getTopicId() != null) {
            LightingQuizPromotionEntity lightingQuizPromotionEntity = LIGHTING_QUIZ_PROMOTION_SERVICE.get(this.session.getPromotionId());
            TopicDTO topic = LIGHTING_QUIZ_PROMOTION_SERVICE.getTopicUpComing(lightingQuizPromotionEntity);
            this.promotion = lightingQuizPromotionEntity;
            this.topic = topic;
        }
        WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);
    }

    public void start(LightingQuizPromotionEntity promotion, TopicDTO topic) throws JsonProcessingException {
        if (StringUtils.isEmpty(session.getWaitingQuestionId())) {
            ReadyToStartLightingQuizQuestion startQuestion = new ReadyToStartLightingQuizQuestion(user);
            startQuestion.ask();
            this.session.setWaitingQuestionId(startQuestion.getId());
            JSONObject json = new JSONObject(this.objectMapper.writeValueAsString(startQuestion));
            this.session.putQuestion(startQuestion.getId(), new LightingSession.LQQuestionSS(startQuestion.getId(),
                    ReadyToStartLightingQuizQuestion.class, json));
            this.session.setPromotionId(promotion.getId());
            this.session.setTopicId(topic.getId());
            this.promotion = promotion;
            this.topic = topic;
            WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);
        }
    }

    public void process(ZaloWebhookMessage msg) throws Exception {
        String waitingQuestionId = session.getWaitingQuestionId();
        if (waitingQuestionId != null) {
            Question question = initWaitingQuestion(waitingQuestionId);
            if (question == null) {
                throw new Exception("can not init waiting question | " + waitingQuestionId);
            }
            boolean isAccept = question.ans(msg);

            //store session
            String jsonQuestion = this.objectMapper.writeValueAsString(question);
            LightingSession.LQQuestionSS lqQuestionSS = new LightingSession.LQQuestionSS(question.getId(),
                    question.getClass(), new JSONObject(jsonQuestion));
            this.session.putQuestion(question.getId(), lqQuestionSS);

            //start to count time
            if (question instanceof ReadyToStartLightingQuizQuestion && isAccept) {
                this.session.setTimeStart(System.currentTimeMillis());
            }
            WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);


            if (isAccept) {
                Question nextQuestion = getNextQuestion();
                if (nextQuestion == null) {
                    //submit
                    complete();
                    WEBHOOK_SESSION_MANAGER.clearSession(user.getUid());
                }else {
                    boolean ask = nextQuestion.ask();
                    if (ask) {
                        //store session
                        this.session.setWaitingQuestionId(nextQuestion.getId());
                        LightingSession.LQQuestionSS nextLqQuestionSS = new LightingSession.LQQuestionSS(nextQuestion.getId(), nextQuestion.getClass(),
                                new JSONObject(this.objectMapper.writeValueAsString(nextQuestion)));
                        this.session.putQuestion(nextQuestion.getId(), nextLqQuestionSS);
                        WEBHOOK_SESSION_MANAGER.saveSession(user.getUid(), this.session);
                    }
                }
            }
        }
    }

    private void complete() throws JsonProcessingException {
        Map<String, LightingSession.LQQuestionSS> questions = session.getQuestions();
        LQDetailFormDTO lqDetailFormDTO = new LQDetailFormDTO();
        List<LQQuestionFormDTO> formDTOS = new ArrayList<>();

        for (String id: questions.keySet()) {
            LightingSession.LQQuestionSS lqQuestionSS = questions.get(id);
            if (lqQuestionSS.getZclass() == LightingQuizGameRatioQuestion.class) {
                LightingQuizGameRatioQuestion lightingQuizGameRatioQuestion = this.objectMapper.readValue(lqQuestionSS.getJson().toString(),
                        LightingQuizGameRatioQuestion.class);
                LQQuestionFormDTO lqQuestionFormDTO = new LQQuestionFormDTO();
                lqQuestionFormDTO.setId(lightingQuizGameRatioQuestion.getQuestionId());
                lqQuestionFormDTO.setTrue(lightingQuizGameRatioQuestion.getTrueAns().equals(lightingQuizGameRatioQuestion.getUserAnswer()));
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
        lightingQuizFormEntity.setPromotionId(this.promotion.getId());
        lightingQuizFormEntity.setType(TypePromotion.LIGHTING_QUIZ_GAME_PROMOTION_TYPE);
        lightingQuizFormEntity.setStatus(StatusLightingQuizForm.INIT);
        lightingQuizFormEntity.setUserId(this.user.getUid());
        lightingQuizFormEntity.setTopicId(topic.getId());
        long count = lqDetailFormDTO.getQuestions().stream().filter(q -> q.isTrue()).count();
        lightingQuizFormEntity.setPoint((int) count);
        LIGHTING_QUIZ_FORM_SERVICE.submit(lightingQuizFormEntity);

        //send msg complete
        CompleteGameMessage completeGameMessage = new CompleteGameMessage(this.user, lightingQuizFormEntity.getPoint(),
                lqDetailFormDTO.getQuestions().size(), System.currentTimeMillis() - session.getTimeStart());
        completeGameMessage.send();
    }


    private Question getNextQuestion() {
        Set<String> questionIds = session.getQuestions().values().stream()
                .map(LightingSession.LQQuestionSS::getId).collect(Collectors.toSet());
        Optional<QuestionDTO> opQuestion = topic.getQuestions().stream()
                .filter(t -> !questionIds.contains(t.getId()))
                .findAny();

        if (!opQuestion.isPresent()) {
            return null;
        }

        QuestionDTO question = opQuestion.get();
        List<String> options = question.getOptions().stream().map(o -> o.getContent()).collect(Collectors.toList());
        String trueAns = question.getOptions().stream().filter(o -> o.isRight()).findAny().get().getContent();

        LightingQuizGameRatioQuestion ratioQuestion = new LightingQuizGameRatioQuestion(user, question.getId());
        ratioQuestion.setPromotionId(this.promotion.getId());
        ratioQuestion.setQuestionId(question.getId());
        ratioQuestion.setTrueAns(trueAns);
        ratioQuestion.setContent(question.getContent());
        ratioQuestion.setOptions(options);
        return ratioQuestion;
    }

    private Question initWaitingQuestion(String id) throws JsonProcessingException {
        LightingSession.LQQuestionSS lqQuestionSS = session.getQuestions().get(id);
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

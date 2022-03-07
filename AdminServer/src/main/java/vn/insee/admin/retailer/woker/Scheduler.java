package vn.insee.admin.retailer.woker;

import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.woker.task.PostBroadcastTask;
import vn.insee.admin.retailer.woker.task.TopicTask;
import vn.insee.admin.retailer.woker.task.ZNSBroadcastTask;

import java.time.LocalDateTime;

@Component
public class Scheduler {

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private PostBroadcastWorker service;

    @Autowired
    private UpcomingTopicWorker notyUpcomingTopicWorker;

    @Autowired
    private StartTopicWorker startTopicWorker;

    @Autowired
    private EndTopicWorker endTopicWorker;

    @Autowired
    private ZNSBroadcastWorker znsBroadcastWorker;

    public String addJob(LocalDateTime localDateTime, PostBroadcastTask job) {
        JobId jobId = jobScheduler.schedule(localDateTime, () -> service.execute(job));
        return jobId.asUUID().toString();
    }

    public String addJob(LocalDateTime localDateTime, TopicTask task) {
        JobId jobId = jobScheduler.schedule(localDateTime, () -> notyUpcomingTopicWorker.execute(task));
        return jobId.asUUID().toString();
    }

    public String addJob2StartTopicLQPromotion(LocalDateTime localDateTime, TopicTask task) {
        JobId jobId = jobScheduler.schedule(localDateTime, () -> startTopicWorker.execute(task));
        return jobId.asUUID().toString();
    }

    public String endTopicLQPromotion(LocalDateTime localDateTime, TopicTask task) {
        JobId jobId = jobScheduler.schedule(localDateTime, () -> endTopicWorker.execute(task));
        return jobId.asUUID().toString();
    }

    public String addZNSBroadcastJob(LocalDateTime localDateTime, ZNSBroadcastTask task) {
        JobId jobId = jobScheduler.schedule(localDateTime, () -> znsBroadcastWorker.execute(task));
        return jobId.asUUID().toString();
    }

    public void remove(String id) {
        jobScheduler.delete(JobId.parse(id));
    }

}

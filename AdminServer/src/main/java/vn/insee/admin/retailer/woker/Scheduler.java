package vn.insee.admin.retailer.woker;

import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Scheduler {

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private BroadcastJobWorker service;

    @Autowired
    private NotyUpcomingTopicWorker notyUpcomingTopicWorker;

    @Autowired
    private StartTopicWorker startTopicWorker;

    public String addJob(LocalDateTime localDateTime, PostNormalBroadcastTask job) {
        JobId jobId = jobScheduler.schedule(localDateTime, () -> service.execute(job));
        return jobId.asUUID().toString();
    }

    public String addJob(LocalDateTime localDateTime, NotyUpcomingTopicTask task) {
        JobId jobId = jobScheduler.schedule(localDateTime, () -> notyUpcomingTopicWorker.execute(task));
        return jobId.asUUID().toString();
    }

    public String addJob2StartTopicLQPromotion(LocalDateTime localDateTime, NotyUpcomingTopicTask task) {
        JobId jobId = jobScheduler.schedule(localDateTime, () -> startTopicWorker.execute(task));
        return jobId.asUUID().toString();
    }

    public void remove(String id) {
        jobScheduler.delete(JobId.parse(id));
    }

}

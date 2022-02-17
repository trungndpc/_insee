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
    private JobService service;

    public String addJob(LocalDateTime localDateTime, PostNormalBroadcastTask job) {
        JobId jobId = jobScheduler.schedule(localDateTime, () -> service.execute(job));
        return jobId.asUUID().toString();
    }

    public void remove(String id) {
        jobScheduler.delete(JobId.parse(id));
    }

}

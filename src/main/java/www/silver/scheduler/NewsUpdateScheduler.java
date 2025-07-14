package www.silver.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;

@Component
@EnableScheduling
public class NewsUpdateScheduler {

    @Inject private JobLauncher jobLauncher;

    /* �� ���� ���� Job  (ITnewsUpdateJob) */
    @Inject private Job ITnewsUpdateJob;

    

    /* -------------------- ������ -------------------- */

    /** ���� 10:00  ���� ���� */
    @Scheduled(cron = "0 55 11 * * *")
    public void runCollectJob() {
        launch(ITnewsUpdateJob, "���� ����");
    }

 

    /* ----------------- ���� ���� ���� ---------------- */

    private void launch(Job job, String name) {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())   // �׻� ���ο� �ν��Ͻ�
                    .toJobParameters();

            System.out.printf("=== [%s] Job ���� : %s%n", name, LocalDateTime.now());
            jobLauncher.run(job, params);
            System.out.printf("=== [%s] Job �Ϸ� : %s%n", name, LocalDateTime.now());

        } catch (Exception e) {
            System.err.printf("=== [%s] Job ���� : %s%n", name, e.getMessage());
            e.printStackTrace();
        }
    }
}

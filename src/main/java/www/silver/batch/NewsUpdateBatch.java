package www.silver.batch;

import javax.inject.Inject;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class NewsUpdateBatch {

   @Inject
   private JobBuilderFactory jobBuilderFactory; // Spring Batch Job ����

   @Inject
   private StepBuilderFactory stepBuilderFactory; // Spring Batch Step ����

   @Inject
   private NewsUpdateTasklet ITnewsUpdateTasklet; // ���� ���� ������Ʈ ������ ������ Tasklet

   // IT ���� ������Ʈ ��ġ Job�� Bean���� ���
   // "ITnewsUpdateJob"�̶�� �̸����� Job�� ����
   // RunIdIncrementer�� ����Ͽ� �� ���ึ�� ������ Job �ν��Ͻ� ID�� �ο�
   // ITnewsUpdateStep�� ù ��° Step���� ���
   @Bean
   public Job ITnewsUpdateJob(JobBuilderFactory jbf, Step ITnewsUpdateStep) {
       return jbf.get("ITnewsUpdateJob")         
                 .incrementer(new RunIdIncrementer())
                 .start(ITnewsUpdateStep)         // ���� ���� Tasklet
                 .build();
   }



   // IT ���� ������Ʈ�� �����ϴ� Step�� Bean���� ���
   // "ITnewsUpdateStep"�̶�� �̸����� Step�� ����
   // ITnewsUpdateTasklet�� �����Ͽ� ���� ���� ���� �� ���� ������ ó��
   @Bean
   public Step ITnewsUpdateStep() {
      return stepBuilderFactory.get("ITnewsUpdateStep").tasklet(ITnewsUpdateTasklet).build();
   }
}

package www.silver.batch;

import java.time.LocalDateTime;

import javax.inject.Inject;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import www.silver.service.IF_newsService;

@Component
public class NewsUpdateTasklet implements Tasklet {

   @Inject
   private IF_newsService newsService;

   // ��ġ Step���� ����Ǵ� �޼���
   // ���� ����/�Ϸ� �ð�, �۾� ID ���� �α׷� ���
   // ���� �߻� �� �α׸� ����ϰ�, ���ܸ� �ٽ� ���� Spring Batch�� ���з� �ν��ϰ� ��
   @Override
   public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

      System.out.println("=== ���� ������Ʈ ���� ===");
      System.out.println("���� �ð�: " + LocalDateTime.now());
      System.out.println("�۾� ��ġ ID: " + chunkContext.getStepContext().getJobName());

      try {
         // �ܺ� API�� ȣ���Ͽ� �ֽ� ���� ������ ����
         System.out.println("���� ������ ���� - �ܺ� API ȣ�� ����");

         // Reader + Processor + Writer ������ ���� �������� ó��
         newsService.fetchAndSaveNews();

         System.out.println("=== ���� ������Ʈ �Ϸ� ===");
         System.out.println("�Ϸ� �ð�: " + LocalDateTime.now());
      } catch (Exception e) {
         // ���� �߻� �� �α� ��� �� ���� ������
         System.err.println("=== ���� ������Ʈ �� ���� �߻� ===");
         System.err.println("���� �޽���: " + e.getMessage());
         System.err.println("���� �߻� �ð�: " + LocalDateTime.now());
         e.printStackTrace();

         // Spring Batch�� ���з� �ν��ϵ��� ���� ������
         throw e;
      }

      // �۾��� ���������� �������� �˸�
      return RepeatStatus.FINISHED;
   }
}

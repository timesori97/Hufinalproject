package www.silver.service; // ��Ű�� ����

import org.springframework.http.HttpEntity; // HTTP ��û ��ƼƼ
import org.springframework.http.HttpHeaders; // HTTP ���
import org.springframework.http.HttpMethod; // HTTP �޼���
import org.springframework.http.ResponseEntity; // HTTP ����
import org.springframework.stereotype.Service; // ���� Ŭ���� ������̼�
import org.springframework.web.client.HttpClientErrorException; // HTTP Ŭ���̾�Ʈ ���� ����
import org.springframework.web.client.RestTemplate; // REST API ȣ�� ����
import www.silver.dao.SubmissionDAO; // SubmissionDAO �������̽�
import www.silver.vo.SubmissionVO; // SubmissionVO Ŭ����

import javax.inject.Inject; // ������ ���� ������̼�
import java.util.ArrayList; // ArrayList Ŭ����
import java.util.HashMap; // HashMap Ŭ����
import java.util.List; // List �������̽�
import java.util.Map; // Map �������̽�

@Service // ������ ���� ������ ���
public class SubmissionServiceImpl implements SubmissionService { // SubmissionService �������̽� ����
    @Inject // ������ ����
    private SubmissionDAO submissionDAO; // SubmissionDAO ��ü

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate �ν��Ͻ� ����

    private String apiKey = "AIzaSyAxfZZisd9-XKV5UUEIBjyGp9R2v4sFWcI"; // Gemini API Ű (���� Ű�� ��ü �ʿ�)

    @Override // �������̽� �޼��� �������̵�
    public void saveSubmission(SubmissionVO submission) { // ���� �����͸� �����ϴ� �޼���
        submissionDAO.saveSubmission(submission); // DAO�� ���� �����ͺ��̽��� ����
    }

    @Override // �������̽� �޼��� �������̵�
    public String gradeSubmission(String code, String problemDescription, String language) { // �ڵ� ä�� �޼���
        System.out.println("�ε�� API Ű: " + apiKey); // API Ű ��� (������)
        String prompt = "������ �ڵ� �����Դϴ�: " + problemDescription + "\n\n" + // ������Ʈ ���� ����
                "����ڰ� ������ �ڵ�� " + language + " ���� �ۼ��Ǿ����ϴ�:\n" + code + "\n\n" + // ������ �ڵ� ����
                "�� �ڵ尡 ��Ȯ���� Ȯ���� �ּ���. ��Ȯ�ϴٸ� ������ 'Correct'�� �����ϰ�, �׷��� �ʴٸ� 'Incorrect'�� ������ �� �ǵ���� ������ �ּ���.�ѱ۷� �������ּ���."; // ���û���

        HttpHeaders headers = new HttpHeaders(); // HTTP ��� ��ü ����
        headers.set("Content-Type", "application/json"); // ������ Ÿ�� ����

        Map<String, Object> requestBody = new HashMap<>(); // ��û ������ �� ����
        List<Map<String, Object>> contentsList = new ArrayList<>(); // contents ����Ʈ ����
        Map<String, Object> contentMap = new HashMap<>(); // content �� ����
        List<Map<String, String>> partsList = new ArrayList<>(); // parts ����Ʈ ����
        Map<String, String> partMap = new HashMap<>(); // part �� ����
        partMap.put("text", prompt); // ������Ʈ �ؽ�Ʈ �߰�
        partsList.add(partMap); // parts ����Ʈ�� �߰�
        contentMap.put("parts", partsList); // content �ʿ� parts �߰�
        contentsList.add(contentMap); // contents ����Ʈ�� �߰�
        requestBody.put("contents", contentsList); // ��û ������ contents �߰�

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers); // HTTP ��û ��ƼƼ ����
        try { // ���� ó�� ����
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey; // API URL
            ResponseEntity<Map> response = restTemplate.exchange( // API ȣ��
                    url, // ��û URL
                    HttpMethod.POST, // POST �޼���
                    entity, // ��û ��ƼƼ
                    Map.class // ���� Ÿ��
            );
            Map<String, Object> responseBody = response.getBody(); // ���� ���� ��������
            if (responseBody != null && responseBody.containsKey("candidates")) { // ���� ��ȿ�� �˻�
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates"); // candidates ����Ʈ ����
                if (!candidates.isEmpty()) { // candidates�� ������� ���� ���
                    Map<String, Object> candidate = candidates.get(0); // ù ��° candidate ��������
                    Map<String, Object> content = (Map<String, Object>) candidate.get("content"); // content ����
                    List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts"); // parts ����
                    if (!parts.isEmpty()) { // parts�� ������� ���� ���
                        return parts.get(0).get("text"); // ù ��° part�� �ؽ�Ʈ ��ȯ
                    }
                }
            }
            return "����: Gemini API���� ������ ������ �� �����ϴ�."; // ���� ó�� ���� �� ���� �޽��� ��ȯ
        } catch (HttpClientErrorException e) { // HTTP ���� ó��
            System.err.println("Gemini API ����: " + e.getResponseBodyAsString()); // ���� ���
            throw e; // ���� ��
        }
    }
}
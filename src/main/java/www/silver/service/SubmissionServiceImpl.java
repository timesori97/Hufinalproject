package www.silver.service; // 패키지 선언

import org.springframework.http.HttpEntity; // HTTP 요청 엔티티
import org.springframework.http.HttpHeaders; // HTTP 헤더
import org.springframework.http.HttpMethod; // HTTP 메서드
import org.springframework.http.ResponseEntity; // HTTP 응답
import org.springframework.stereotype.Service; // 서비스 클래스 어노테이션
import org.springframework.web.client.HttpClientErrorException; // HTTP 클라이언트 오류 예외
import org.springframework.web.client.RestTemplate; // REST API 호출 도구
import www.silver.dao.SubmissionDAO; // SubmissionDAO 인터페이스
import www.silver.vo.SubmissionVO; // SubmissionVO 클래스

import javax.inject.Inject; // 의존성 주입 어노테이션
import java.util.ArrayList; // ArrayList 클래스
import java.util.HashMap; // HashMap 클래스
import java.util.List; // List 인터페이스
import java.util.Map; // Map 인터페이스

@Service // 스프링 서비스 빈으로 등록
public class SubmissionServiceImpl implements SubmissionService { // SubmissionService 인터페이스 구현
    @Inject // 의존성 주입
    private SubmissionDAO submissionDAO; // SubmissionDAO 객체

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate 인스턴스 생성

    private String apiKey = "AIzaSyAxfZZisd9-XKV5UUEIBjyGp9R2v4sFWcI"; // Gemini API 키 (실제 키로 교체 필요)

    @Override // 인터페이스 메서드 오버라이드
    public void saveSubmission(SubmissionVO submission) { // 제출 데이터를 저장하는 메서드
        submissionDAO.saveSubmission(submission); // DAO를 통해 데이터베이스에 저장
    }

    @Override // 인터페이스 메서드 오버라이드
    public String gradeSubmission(String code, String problemDescription, String language) { // 코드 채점 메서드
        System.out.println("로드된 API 키: " + apiKey); // API 키 출력 (디버깅용)
        String prompt = "다음은 코딩 문제입니다: " + problemDescription + "\n\n" + // 프롬프트 생성 시작
                "사용자가 제출한 코드는 " + language + " 언어로 작성되었습니다:\n" + code + "\n\n" + // 문제와 코드 포함
                "이 코드가 정확한지 확인해 주세요. 정확하다면 응답을 'Correct'로 시작하고, 그렇지 않다면 'Incorrect'로 시작한 후 피드백을 제공해 주세요.한글로 설명해주세요."; // 지시사항

        HttpHeaders headers = new HttpHeaders(); // HTTP 헤더 객체 생성
        headers.set("Content-Type", "application/json"); // 콘텐츠 타입 설정

        Map<String, Object> requestBody = new HashMap<>(); // 요청 본문용 맵 생성
        List<Map<String, Object>> contentsList = new ArrayList<>(); // contents 리스트 생성
        Map<String, Object> contentMap = new HashMap<>(); // content 맵 생성
        List<Map<String, String>> partsList = new ArrayList<>(); // parts 리스트 생성
        Map<String, String> partMap = new HashMap<>(); // part 맵 생성
        partMap.put("text", prompt); // 프롬프트 텍스트 추가
        partsList.add(partMap); // parts 리스트에 추가
        contentMap.put("parts", partsList); // content 맵에 parts 추가
        contentsList.add(contentMap); // contents 리스트에 추가
        requestBody.put("contents", contentsList); // 요청 본문에 contents 추가

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers); // HTTP 요청 엔티티 생성
        try { // 예외 처리 시작
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey; // API URL
            ResponseEntity<Map> response = restTemplate.exchange( // API 호출
                    url, // 요청 URL
                    HttpMethod.POST, // POST 메서드
                    entity, // 요청 엔티티
                    Map.class // 응답 타입
            );
            Map<String, Object> responseBody = response.getBody(); // 응답 본문 가져오기
            if (responseBody != null && responseBody.containsKey("candidates")) { // 응답 유효성 검사
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates"); // candidates 리스트 추출
                if (!candidates.isEmpty()) { // candidates가 비어있지 않은 경우
                    Map<String, Object> candidate = candidates.get(0); // 첫 번째 candidate 가져오기
                    Map<String, Object> content = (Map<String, Object>) candidate.get("content"); // content 추출
                    List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts"); // parts 추출
                    if (!parts.isEmpty()) { // parts가 비어있지 않은 경우
                        return parts.get(0).get("text"); // 첫 번째 part의 텍스트 반환
                    }
                }
            }
            return "오류: Gemini API에서 응답을 가져올 수 없습니다."; // 응답 처리 실패 시 오류 메시지 반환
        } catch (HttpClientErrorException e) { // HTTP 오류 처리
            System.err.println("Gemini API 오류: " + e.getResponseBodyAsString()); // 오류 출력
            throw e; // 예외 재
        }
    }
}
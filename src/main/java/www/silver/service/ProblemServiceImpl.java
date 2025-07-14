package www.silver.service; // 패키지 선언

import org.springframework.beans.factory.annotation.Autowired; // 자동 주입 어노테이션
import org.springframework.stereotype.Service; // 서비스 클래스 어노테이션
import www.silver.dao.ProblemDAO; // ProblemDAO 인터페이스
import www.silver.vo.ProblemVO; // ProblemVO 클래스

import java.util.HashMap; // HashMap 클래스
import java.util.List; // List 인터페이스
import java.util.Map; // Map 인터페이스

@Service // 스프링 서비스 빈으로 등록
public class ProblemServiceImpl implements ProblemService { // ProblemService 인터페이스 구현
    @Autowired // 의존성 자동 주입
    private ProblemDAO problemDAO; // ProblemDAO 객체

    @Override // 인터페이스 메서드 오버라이드
    public List<ProblemVO> getAllProblems() { // 모든 문제를 조회하는 메서드
        return problemDAO.getAllProblems(); // DAO를 통해 데이터 조회
    }

    @Override // 인터페이스 메서드 오버라이드
    public List<ProblemVO> getProblemsByLanguageAndDifficulty(String language, String difficulty) { // 언어와 난이도로 필터링된 문제를 조회
        Map<String, String> params = new HashMap<>(); // 파라미터 맵 생성
        params.put("language", language); // 언어 파라미터 추가
        params.put("difficulty", difficulty); // 난이도 파라미터 추가
        return problemDAO.getProblemsByLanguageAndDifficulty(params); // DAO를 통해 데이터 조회
    }

    @Override // 인터페이스 메서드 오버라이드
    public ProblemVO getProblemById(int problemId) { // 특정 ID의 문제를 조회
        return problemDAO.getProblemById(problemId); // DAO를 통해 데이터 조회
    }
}
package www.silver.dao; // 패키지 선언

import org.apache.ibatis.session.SqlSession; // MyBatis 세션
import www.silver.vo.ProblemVO; // ProblemVO 클래스

import javax.inject.Inject; // 의존성 주입 어노테이션
import java.util.List; // List 인터페이스
import java.util.Map; // Map 인터페이스

@org.springframework.stereotype.Repository // 스프링 레포지토리 빈으로 등록
public class ProblemDAOImpl implements ProblemDAO { // ProblemDAO 인터페이스 구현
    @Inject // 의존성 주입
    private SqlSession sqlSession; // MyBatis 세션 객체

    @Override // 인터페이스 메서드 오버라이드
    public List<ProblemVO> getAllProblems() { // 모든 문제 조회 메서드
        return sqlSession.selectList("www.silver.dao.ProblemDAO.getAllProblems"); // MyBatis를 통해 목록 조회
    }

    @Override // 인터페이스 메서드 오버라이드
    public List<ProblemVO> getProblemsByLanguageAndDifficulty(Map<String, String> params) { // 필터링된 문제 조회 메서드
        return sqlSession.selectList("www.silver.dao.ProblemDAO.getProblemsByLanguageAndDifficulty", params); // MyBatis를 통해 목록 조회
    }

    @Override // 인터페이스 메서드 오버라이드
    public ProblemVO getProblemById(int problemId) { // 특정 문제 조회 메서드
        return sqlSession.selectOne("www.silver.dao.ProblemDAO.getProblemById", problemId); // MyBatis를 통해 단일 조회
    }
}
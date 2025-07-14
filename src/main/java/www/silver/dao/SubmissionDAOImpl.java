package www.silver.dao; // 패키지 선언

import org.apache.ibatis.session.SqlSession; // MyBatis 세션
import www.silver.vo.SubmissionVO; // SubmissionVO 클래스

import javax.inject.Inject; // 의존성 주입 어노테이션

@org.springframework.stereotype.Repository // 스프링 레포지토리 빈으로 등록
public class SubmissionDAOImpl implements SubmissionDAO { // SubmissionDAO 인터페이스 구현
    @Inject // 의존성 주입
    private SqlSession sqlSession; // MyBatis 세션 객체

    @Override // 인터페이스 메서드 오버라이드
    public void saveSubmission(SubmissionVO submission) { // 제출 데이터 저장 메서드
        sqlSession.insert("www.silver.dao.SubmissionDAO.saveSubmission", submission); // MyBatis를 통해 삽입
    }
}
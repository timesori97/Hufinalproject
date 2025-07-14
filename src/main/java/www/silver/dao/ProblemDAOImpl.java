package www.silver.dao; // ��Ű�� ����

import org.apache.ibatis.session.SqlSession; // MyBatis ����
import www.silver.vo.ProblemVO; // ProblemVO Ŭ����

import javax.inject.Inject; // ������ ���� ������̼�
import java.util.List; // List �������̽�
import java.util.Map; // Map �������̽�

@org.springframework.stereotype.Repository // ������ �������丮 ������ ���
public class ProblemDAOImpl implements ProblemDAO { // ProblemDAO �������̽� ����
    @Inject // ������ ����
    private SqlSession sqlSession; // MyBatis ���� ��ü

    @Override // �������̽� �޼��� �������̵�
    public List<ProblemVO> getAllProblems() { // ��� ���� ��ȸ �޼���
        return sqlSession.selectList("www.silver.dao.ProblemDAO.getAllProblems"); // MyBatis�� ���� ��� ��ȸ
    }

    @Override // �������̽� �޼��� �������̵�
    public List<ProblemVO> getProblemsByLanguageAndDifficulty(Map<String, String> params) { // ���͸��� ���� ��ȸ �޼���
        return sqlSession.selectList("www.silver.dao.ProblemDAO.getProblemsByLanguageAndDifficulty", params); // MyBatis�� ���� ��� ��ȸ
    }

    @Override // �������̽� �޼��� �������̵�
    public ProblemVO getProblemById(int problemId) { // Ư�� ���� ��ȸ �޼���
        return sqlSession.selectOne("www.silver.dao.ProblemDAO.getProblemById", problemId); // MyBatis�� ���� ���� ��ȸ
    }
}
package www.silver.dao; // ��Ű�� ����

import org.apache.ibatis.session.SqlSession; // MyBatis ����
import www.silver.vo.SubmissionVO; // SubmissionVO Ŭ����

import javax.inject.Inject; // ������ ���� ������̼�

@org.springframework.stereotype.Repository // ������ �������丮 ������ ���
public class SubmissionDAOImpl implements SubmissionDAO { // SubmissionDAO �������̽� ����
    @Inject // ������ ����
    private SqlSession sqlSession; // MyBatis ���� ��ü

    @Override // �������̽� �޼��� �������̵�
    public void saveSubmission(SubmissionVO submission) { // ���� ������ ���� �޼���
        sqlSession.insert("www.silver.dao.SubmissionDAO.saveSubmission", submission); // MyBatis�� ���� ����
    }
}
package www.silver.service; // ��Ű�� ����

import org.springframework.beans.factory.annotation.Autowired; // �ڵ� ���� ������̼�
import org.springframework.stereotype.Service; // ���� Ŭ���� ������̼�
import www.silver.dao.ProblemDAO; // ProblemDAO �������̽�
import www.silver.vo.ProblemVO; // ProblemVO Ŭ����

import java.util.HashMap; // HashMap Ŭ����
import java.util.List; // List �������̽�
import java.util.Map; // Map �������̽�

@Service // ������ ���� ������ ���
public class ProblemServiceImpl implements ProblemService { // ProblemService �������̽� ����
    @Autowired // ������ �ڵ� ����
    private ProblemDAO problemDAO; // ProblemDAO ��ü

    @Override // �������̽� �޼��� �������̵�
    public List<ProblemVO> getAllProblems() { // ��� ������ ��ȸ�ϴ� �޼���
        return problemDAO.getAllProblems(); // DAO�� ���� ������ ��ȸ
    }

    @Override // �������̽� �޼��� �������̵�
    public List<ProblemVO> getProblemsByLanguageAndDifficulty(String language, String difficulty) { // ���� ���̵��� ���͸��� ������ ��ȸ
        Map<String, String> params = new HashMap<>(); // �Ķ���� �� ����
        params.put("language", language); // ��� �Ķ���� �߰�
        params.put("difficulty", difficulty); // ���̵� �Ķ���� �߰�
        return problemDAO.getProblemsByLanguageAndDifficulty(params); // DAO�� ���� ������ ��ȸ
    }

    @Override // �������̽� �޼��� �������̵�
    public ProblemVO getProblemById(int problemId) { // Ư�� ID�� ������ ��ȸ
        return problemDAO.getProblemById(problemId); // DAO�� ���� ������ ��ȸ
    }
}
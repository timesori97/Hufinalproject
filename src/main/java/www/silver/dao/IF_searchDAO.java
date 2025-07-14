package www.silver.dao;

import www.silver.vo.BoardVO;
import java.util.List;
import java.util.Map;

public interface IF_searchDAO {

	// �Խñ� �ۼ���(writer) �������� �˻�� �ش��ϴ� �Խñ� ����� ����¡�Ͽ� ��ȸ
	List<BoardVO> searchByWriter(Map<String, Object> params);

	// �Խñ��� ���α׷��� ��� �������� �˻�� �ش��ϴ� �Խñ� ����� ����¡�Ͽ� ��ȸ
	List<BoardVO> searchByProgrammingLanguage(Map<String, Object> params);

	// Ư�� Ű���尡 ���Ե� �ۼ����� �Խñ� ��ü ������ ��ȸ
	int countByWriter(String keyword);

	// Ư�� Ű���尡 ���Ե� ��������� �Խñ� ��ü ������ ��ȸ
	int countByProgrammingLanguage(String keyword);
}

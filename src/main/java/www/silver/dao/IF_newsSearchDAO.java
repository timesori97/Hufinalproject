package www.silver.dao;

import www.silver.vo.NewsVO;
import java.util.List;
import java.util.Map;

public interface IF_newsSearchDAO {

	// ���� ������ �������� �˻�� �ش��ϴ� ���� ����� ����¡�Ͽ� ��ȸ
	List<NewsVO> searchByTitle(Map<String, Object> params);

	// ���� ī�װ��� �������� �˻�� �ش��ϴ� ���� ����� ����¡�Ͽ� ��ȸ
	List<NewsVO> searchByCategory(Map<String, Object> params);

	// Ư�� Ű���尡 ���Ե� ���� ������ ��ü ������ ��ȸ
	int countByTitle(String keyword);

	// Ư�� Ű���尡 ���Ե� ���� ī�װ��� ��ü ������ ��ȸ
	int countByCategory(String keyword);
}

package www.silver.dao;

import www.silver.vo.NewsVO;
import java.util.List;

public interface IF_newsDAO {

	// ���� �����͸� DB�� ����
	public void insertNews(NewsVO news);

	// 2025�� IT ���� ����� ����¡�Ͽ� ��ȸ
	// start ��ȸ ���� �ε��� (0���� ����)
	// pageSize �� �������� ��ȸ�� ���� ����
	public List<NewsVO> select2025ItNews(int start, int pageSize);

	// ��ü ���� �������� ������ ��ȸ
	public int selectTotalNewsCount();
	
	
	public int deleteLowSimilarity(float threshold);
}

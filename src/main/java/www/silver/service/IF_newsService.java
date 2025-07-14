package www.silver.service;

import www.silver.vo.NewsVO;
import java.util.List;

public interface IF_newsService {

	// �ܺ� IT ���� API�� ȣ���Ͽ� �ֽ� ������ �����ϰ�, DB�� ����
	public void fetchAndSaveNews();

	// 2025�⿡ ����� IT ���� ����� ������ ������ ��ȸ
	public List<NewsVO> get2025ItNews(int page, int pageSize);

	// ��ü IT ���� �������� ������ ��ȸ
	public int getTotalNewsCount();
}

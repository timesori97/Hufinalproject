package www.silver.service;

import www.silver.vo.NewsVO;
import www.silver.vo.PageVO;
import java.util.List;

public interface IF_newsSearchService {

	// Ű����� Ÿ��(����/ī�װ� ��), ������ ��ȣ�� �̿��� ���� ����� �˻�
	List<NewsVO> searchNews(String keyword, String type, int page);

	// Ű����, Ÿ��, ������ ��ȣ�� ���� ����¡ ������ ����Ͽ� ��ȯ
	PageVO searchPagingParam(String keyword, String type, int page);

	// Ű����� Ÿ�Կ� �ش��ϴ� ��ü �˻� ��� ������ ��ȸ
	int getSearchTotalCount(String keyword, String type);
}

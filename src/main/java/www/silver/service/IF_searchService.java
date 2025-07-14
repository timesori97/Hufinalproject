package www.silver.service;

import www.silver.vo.BoardVO;
import www.silver.vo.PageVO;
import java.util.List;

public interface IF_searchService {

	// Ű����� Ÿ�Կ� �ش��ϴ� ��ü �Խù� �˻� ��� ������ ��ȸ
	public int getSearchTotalCount(String keyword, String type);

	// �˻� ����(Ű����, �ۼ���, ������ ��ȣ)�� ���� ������ ������ ����Ͽ� ��ȯ
	PageVO searchPagingParam(String keyword, String writer, int page);

	// Ű����, Ÿ��, ������ ��ȣ�� ���� �Խù� ����� �˻�
	List<BoardVO> searchContent(String keyword, String type, int page);
}
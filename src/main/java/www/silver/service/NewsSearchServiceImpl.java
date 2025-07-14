package www.silver.service;

import org.springframework.stereotype.Service;
import www.silver.dao.IF_newsSearchDAO;
import www.silver.vo.NewsVO;
import www.silver.vo.PageVO;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsSearchServiceImpl implements IF_newsSearchService {

    @Inject
    IF_newsSearchDAO newsSearchDAO;

    int pageLimit = 10; // �� �������� �Ѹ� ���� ����
    int blockLimit = 5; // �� ��Ͽ� �Ѹ� ������ ��ȣ ����(������ ���)

    // Ű����� Ÿ��(����/ī�װ�), ������ ��ȣ�� �̿��� ���� ����� �˻�
    @Override
    public List<NewsVO> searchNews(String keyword, String type, int page) {
        try {
            if (page < 1) {
                page = 1;
            }
            int pagingStart = (page - 1) * pageLimit;

            Map<String, Object> searchParams = new HashMap<>();
            searchParams.put("keyword", keyword);
            searchParams.put("start", pagingStart);
            searchParams.put("limit", pageLimit);

            switch (type) {
                case "title":
                    return newsSearchDAO.searchByTitle(searchParams);
                case "category":
                    return newsSearchDAO.searchByCategory(searchParams);
                default:
                    return new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("���� �˻� �� ���� �߻�: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Ű����, Ÿ��, ������ ��ȣ�� ���� ������ ������ ����Ͽ� ��ȯ
    @Override
    public PageVO searchPagingParam(String keyword, String type, int page) {
        try {
            int searchCount = getSearchTotalCount(keyword, type);
            int maxPage = (int) Math.ceil((double) searchCount / pageLimit);
            if (maxPage < 1) maxPage = 1;

            if (page < 1) page = 1;
            if (page > maxPage) page = maxPage;

            int startPage = ((page - 1) / blockLimit) * blockLimit + 1;
            int endPage = startPage + blockLimit - 1;
            if (endPage > maxPage) endPage = maxPage;

            PageVO pageVO = new PageVO();
            pageVO.setPage(page);
            pageVO.setMaxPage(maxPage);
            pageVO.setStartPage(startPage);
            pageVO.setEndPage(endPage);

            return pageVO;
        } catch (Exception e) {
            System.out.println("������ ��� �� ���� �߻�: " + e.getMessage());
            PageVO defaultPageVO = new PageVO();
            defaultPageVO.setPage(1);
            defaultPageVO.setMaxPage(1);
            defaultPageVO.setStartPage(1);
            defaultPageVO.setEndPage(1);
            return defaultPageVO;
        }
    }

    // Ű����� Ÿ�Կ� �ش��ϴ� ��ü �˻� ��� ������ ��ȸ
    @Override
    public int getSearchTotalCount(String keyword, String type) {
        try {
            switch (type) {
                case "title":
                    return newsSearchDAO.countByTitle(keyword);
                case "category":
                    return newsSearchDAO.countByCategory(keyword);
                default:
                    return 0;
            }
        } catch (Exception e) {
            System.out.println("�� ���� ��ȸ �� ���� �߻�: " + e.getMessage());
            return 0;
        }
    }
}
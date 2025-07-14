package www.silver.service;

import org.springframework.stereotype.Service;
import www.silver.dao.IF_searchDAO;
import www.silver.vo.BoardVO;
import www.silver.vo.PageVO;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements IF_searchService {

    @Inject
    IF_searchDAO searchDAO;

    int pageLimit = 10; // �� �������� ��� �Խñ� ��
    int blockLimit = 5; // �Ʒ��� ��� ������ ��ȣ ����(������ ���)

    // Ű����, Ÿ��(�ۼ���/���α׷��� ���), ������ ��ȣ�� ���� �Խñ� ����� �˻�
    @Override
    public List<BoardVO> searchContent(String keyword, String type, int page) {
        try {
            if (page < 1) {
                page = 1;
            }
            int pagingStart = (page - 1) * pageLimit;

            Map<String, Object> searchType = new HashMap<>();
            searchType.put("keyword", keyword);
            searchType.put("start", pagingStart);
            searchType.put("limit", pageLimit);

            switch (type) {
                case "writer":
                    return searchDAO.searchByWriter(searchType);
                case "programmingLanguage":
                    return searchDAO.searchByProgrammingLanguage(searchType);
                default:
                    return new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("�˻� �� ���� �߻�: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // �˻� ����� ���� �Խñ� ������ ������ ����Ͽ� ��ȯ
    @Override
    public PageVO searchPagingParam(String keyword, String type, int page) {
        try {
            // ��ü �˻� ��� ���� ��ȸ
            int searchCount = getSearchTotalCount(keyword, type);
            System.out.println("Total search count: " + searchCount);

            // ��ü ������ �� ���
            int maxPage = (int) Math.ceil((double) searchCount / pageLimit);
            if (maxPage < 1) maxPage = 1;

            // ���� ������ ��ȣ ��ȿ�� üũ
            if (page < 1) page = 1;
            if (page > maxPage) page = maxPage;

            // �� ��Ͽ� ��� ����/�� ������ ��ȣ ���
            int startPage = ((page - 1) / blockLimit) * blockLimit + 1;
            int endPage = startPage + blockLimit - 1;
            if (endPage > maxPage) endPage = maxPage;

            PageVO pagevo = new PageVO();
            pagevo.setPage(page);
            pagevo.setMaxPage(maxPage);
            pagevo.setStartPage(startPage);
            pagevo.setEndPage(endPage);
            
            pagevo.setTotalCount(searchCount);   // �߰� �ʵ�
            pagevo.setPageSize(pageLimit);       // �߰� pageSize �ڵ�, JSP ������ ����

            System.out.println("== �˻� ������ �Ķ���� Ȯ�� ==");
            System.out.println("keyword = " + keyword + ", type = " + type);
            System.out.println("page = " + page);
            System.out.println("startPage = " + startPage);
            System.out.println("endPage = " + endPage);
            System.out.println("maxPage = " + maxPage);

            return pagevo;

        } catch (Exception e) {
            System.out.println("������ ��� �� ���� �߻�: " + e.getMessage());
            // ���� �߻� �� �⺻ ������ ���� ��ȯ
            PageVO defaultPagevo = new PageVO();
            defaultPagevo.setPage(1);
            defaultPagevo.setMaxPage(1);
            defaultPagevo.setStartPage(1);
            defaultPagevo.setEndPage(1);
            return defaultPagevo;
        }
    }

    // �ۼ��� �Ǵ� ���α׷��� ��� ���� �˻� ����� ��ü �Խñ� ������ ��ȯ
    @Override
    public int getSearchTotalCount(String keyword, String type) {
        try {
            switch (type) {
                case "writer":
                    return searchDAO.countByWriter(keyword);
                case "programmingLanguage":
                    return searchDAO.countByProgrammingLanguage(keyword);
                default:
                    return 0;
            }
        } catch (Exception e) {
            System.out.println("�� ���� ��ȸ �� ���� �߻�: " + e.getMessage());
            return 0;
        }
    }
}
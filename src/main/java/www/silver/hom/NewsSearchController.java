package www.silver.hom;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import www.silver.service.IF_newsSearchService;
import www.silver.vo.NewsVO;
import www.silver.vo.PageVO;
import javax.inject.Inject;
import java.util.List;

@Controller
public class NewsSearchController {

    @Inject
    IF_newsSearchService newsSearchService;

    @GetMapping("/searchNews")
    public String searchNews(
            @RequestParam("type") String type,      // �˻� ����
            @RequestParam("keyword") String keyword, // �˻���
            @RequestParam(value = "page", defaultValue = "1") int page, // ������ ��ȣ (�⺻�� 1)
            Model model) {

        try {
            // ���� �˻� ��� ��ȸ
            List<NewsVO> searchResults = newsSearchService.searchNews(keyword, type, page);
            // ����¡ ���� ��ȸ
            PageVO pageVO = newsSearchService.searchPagingParam(keyword, type, page);

            // �𵨿� �˻� ����� ����¡ ���� �߰�
            model.addAttribute("newsList", searchResults); // ���� ����Ʈ
            model.addAttribute("paging", pageVO);           // ����¡ ����
            model.addAttribute("isSearchResult", true);     // �˻� ��� ���� ǥ��
            model.addAttribute("searchType", type);         // �˻� ����
            model.addAttribute("searchKeyword", keyword);   // �˻���
            model.addAttribute("totalCount", newsSearchService.getSearchTotalCount(keyword, type)); // ��ü �˻� ��� ��

            return "getuseds/itNews"; // itNews.jsp�� �̵�
        } catch (Exception e) {
            // �˻� �� ���� �߻� �� ���� �޽��� ����
            model.addAttribute("error", "���� �˻� �� ������ �߻��Ͽ����ϴ�.");
            return "getuseds/itNews";
        }
    }
}

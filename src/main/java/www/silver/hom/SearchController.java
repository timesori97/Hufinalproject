package www.silver.hom;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import www.silver.service.IF_searchService;
import www.silver.vo.BoardVO;
import www.silver.vo.PageVO;
import javax.inject.Inject;
import java.util.List;

@Controller
public class SearchController {
    @Inject
    IF_searchService searchService;

    @GetMapping("/searchContent")
    public String searchContent(
            @RequestParam("type") String type,      // �˻� ����(�ۼ���, ���� ��)
            @RequestParam("keyword") String keyword, // �˻���
            @RequestParam(value = "page", defaultValue = "1") int page, // ������ ��ȣ(�⺻ 1)
            Model model) {

        try {
            // �˻� ��� ��ȸ
            // type: �ۼ��� or ���� �˻�
            // �Խñ� ����Ʈ ��ȸ
            List<BoardVO> searchResults = searchService.searchContent(keyword, type, page);

            // ����¡ ���� ��ȸ
            PageVO pageVO = searchService.searchPagingParam(keyword, type, page);

            // �˻� ��� �� ����¡ ������ �𵨿� ��� ��� ����
            model.addAttribute("contentlist", searchResults); // �˻��� �Խñ� ���
            model.addAttribute("paging", pageVO);             // ����¡ ����
            model.addAttribute("isSearchResult", true);       // �˻� ��� ���� ǥ��
            model.addAttribute("searchType", type);           // �˻� ����
            model.addAttribute("searchKeyword", keyword);     // �˻���
            model.addAttribute("totalCount", searchService.getSearchTotalCount(keyword, type)); // ��ü ��� ����

            // �Խ��� ��� �̵�
            return "getuseds/board";

        } catch (Exception e) {
            // �˻� �� ���� �߻� �� ���� �޽��� ����
            model.addAttribute("error", "�˻� �� ������ �߻��Ͽ����ϴ�.");
            return "getuseds/board";
        }
    }
}

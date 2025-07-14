package www.silver.hom;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.silver.service.IF_likesService;
import www.silver.vo.LikeVO;
import www.silver.vo.MemberVO;

@Controller
public class PostLikeController {
    
    @Inject
    IF_likesService likesService;
    
    @PostMapping("/postLike")
    @ResponseBody
    public HashMap<String, Object> postLike(LikeVO likevo, HttpSession session) {
        HashMap<String, Object> data = new HashMap<>();
        // �α��� üũ�� ���ͼ��Ϳ��� �̹� ó����
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        likevo.setUserId(loginUser.getUserId());

        // ���� ������� ���ƿ� ���� ��ȸ
        int myLikeCount = likesService.getMyLikeCount(likevo);

        // ���ƿ� ��� ó��
        boolean isSuccess = likesService.toggleLike(likevo);

        // ���°�: ������ ���ƿ䰡 ������ "unlike", ������ "like"
        data.put("status", myLikeCount > 0 ? "unlike" : "like");
        System.out.println("postLike - postNum: " + likevo.getPostNum() + ", userId: " + likevo.getUserId());
        System.out.println(myLikeCount > 0 ? "unlike" : "like");

        if (isSuccess) {
            data.put("result", "success");
        } else {
            data.put("result", "error");
            data.put("reason", "databaseError");
        }

        return data;
    }

    @PostMapping("/getMyLikeStatus")
    @ResponseBody
    public HashMap<String, Object> getMyLikeStatus(LikeVO likevo, HttpSession session) {
        HashMap<String, Object> data = new HashMap<>();
        // �α��� üũ�� ���ͼ��Ϳ��� �̹� ó����
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        likevo.setUserId(loginUser.getUserId());
        int myLikeCount = likesService.getMyLikeCount(likevo);
        data.put("result", "success");
        data.put("status", myLikeCount > 0 ? "like" : "unlike");
        System.out.println("getMyLikeStatus - postNum: " + likevo.getPostNum() + ", userId: " + likevo.getUserId() + ", status: " + (myLikeCount > 0 ? "like" : "unlike"));

        return data;
    }

    
    @PostMapping("/getTotalLikeCount")
    @ResponseBody
    public HashMap<String, Object> getTotalLikeCount(int postNum) {
        HashMap<String, Object> data = new HashMap<>();
        int totalLikeCount = likesService.getTotalLikeCount(postNum);
        data.put("result", "success");
        data.put("totalLikeCount", totalLikeCount);
        System.out.println("getTotalLikeCount - postNum: " + postNum + ", totalLikeCount: " + totalLikeCount);
        return data;
    }
}

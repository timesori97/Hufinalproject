package www.silver.dao;

import www.silver.vo.LikeVO;

public interface IF_likesDAO {
    // �Խñ� ���ƿ� �߰�
    public void postLike(LikeVO likevo);
    
    // �Խñ� ���ƿ� ���� Ȯ�� (����ڰ� �ش� �Խñۿ� ���ƿ並 ��������)
    public int getMyLikeCount(LikeVO likevo);
    
    // �Խñ� ���ƿ� ����
    public void deleteLike(LikeVO likeVo);
    
    // �Խñ� ���ƿ� �� ���� ��ȸ
    public int getTotalLikeCount(int postNum);
}

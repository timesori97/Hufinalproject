package www.silver.service;

import www.silver.vo.LikeVO;

public interface IF_likesService {

	// �Խñۿ� ���� ���ƿ� ���¸� ���(�߰� �Ǵ� ���).
	// �̹� ���ƿ並 ���� ��� ���, ������ ���� ��� �߰�
	boolean toggleLike(LikeVO likevo);

	// Ư�� ����ڰ� Ư�� �Խñۿ� ���ƿ並 �������� ����(����)�� ��ȸ
	int getMyLikeCount(LikeVO likevo);

	// Ư�� �Խñ��� ��ü ���ƿ� ������ ��ȸ�մϴ�.
	int getTotalLikeCount(int postNum);
}

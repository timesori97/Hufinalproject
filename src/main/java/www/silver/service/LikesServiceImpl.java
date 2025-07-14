package www.silver.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import www.silver.dao.IF_likesDAO;
import www.silver.vo.LikeVO;

@Service
public class LikesServiceImpl implements IF_likesService {

	@Inject
	IF_likesDAO likesdao;

	// �Խù��� ���� ���ƿ� ���¸� ���
	// ����ڰ� �ش� �Խù��� ���ƿ並 ������ �ʾҴٸ� �߰�
	// �̹� �����ٸ� ����
	@Override
	public boolean toggleLike(LikeVO likevo) {
		try {
			if (getMyLikeCount(likevo) < 1) {
				// ���ƿ� �߰�
				likesdao.postLike(likevo);
				System.out.println("���ƿ� �߰� - postNum: " + likevo.getPostNum() + ", userId: " + likevo.getUserId());
			} else {
				// ���ƿ� ���
				likesdao.deleteLike(likevo);
				System.out.println("���ƿ� ��� - postNum: " + likevo.getPostNum() + ", userId: " + likevo.getUserId());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Ư�� ����ڰ� Ư�� �Խù��� ���ƿ並 �������� ���θ� ��ȸ
	@Override
	public int getMyLikeCount(LikeVO likevo) {
		return likesdao.getMyLikeCount(likevo);
	}

	// Ư�� �Խù��� ��ü ���ƿ� ������ ��ȸ
	@Override
	public int getTotalLikeCount(int postNum) {
		return likesdao.getTotalLikeCount(postNum);
	}
}
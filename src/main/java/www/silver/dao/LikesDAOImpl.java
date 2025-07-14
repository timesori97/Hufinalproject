package www.silver.dao;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import www.silver.vo.LikeVO;

@Repository // Spring���� �� Ŭ������ ������ ���� ����(DAO) ������Ʈ�� �ν�
public class LikesDAOImpl implements IF_likesDAO {

    @Inject // Spring�� ������ ������ ���� SqlSession ��ü�� �ڵ����� ����
    private SqlSession sqlSession;

    // �Խñ� ���ƿ� �߰�
    @Override
    public void postLike(LikeVO likevo) {
        // MyBatis�� insert �޼��带 ȣ���Ͽ� �����ͺ��̽��� ���ƿ� ������ ����
        sqlSession.insert("www.silver.dao.IF_likesDAO.postLike", likevo);
    }

    // �Խñ� ���ƿ� ���� Ȯ�� (����ڰ� �ش� �Խñۿ� ���ƿ並 ��������)
    @Override
    public int getMyLikeCount(LikeVO likevo) {
        // MyBatis�� selectOne �޼��带 ȣ���Ͽ� Ư�� ������� ���ƿ� ���¸� ī��Ʈ�� ��ȯ
        return sqlSession.selectOne("www.silver.dao.IF_likesDAO.getMyLikeCount", likevo);
    }

    // �Խñ� ���ƿ� ����
    @Override
    public void deleteLike(LikeVO likevo) {
        // MyBatis�� delete �޼��带 ȣ���Ͽ� �����ͺ��̽����� ���ƿ� ������ ����
        sqlSession.delete("www.silver.dao.IF_likesDAO.deleteLike", likevo);
    }

    // �Խñ� ���ƿ� �� ���� ��ȸ
    @Override
    public int getTotalLikeCount(int postNum) {
        // MyBatis�� selectOne �޼��带 ȣ���Ͽ� Ư�� �Խñ��� �� ���ƿ� ���� ��ȯ
        return sqlSession.selectOne("www.silver.dao.IF_likesDAO.getTotalLikeCount", postNum);
    }
}

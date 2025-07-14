package www.silver.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import www.silver.vo.NewsVO;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Repository
public class NewsSearchDAOImpl implements IF_newsSearchDAO {

	@Inject
	SqlSession sqlSession;

	// ���� ������ �������� �˻�� �ش��ϴ� ���� ����� ����¡�Ͽ� ��ȸ
	@Override
	public List<NewsVO> searchByTitle(Map<String, Object> params) {
		return sqlSession.selectList("www.silver.dao.IF_newsSearchDAO.searchByTitle", params);
	}

	// ���� ī�װ��� �������� �˻�� �ش��ϴ� ���� ����� ����¡�Ͽ� ��ȸ
	@Override
	public List<NewsVO> searchByCategory(Map<String, Object> params) {
		return sqlSession.selectList("www.silver.dao.IF_newsSearchDAO.searchByCategory", params);
	}

	// Ư�� Ű���尡 ���Ե� ���� ������ ��ü ������ ��ȸ
	@Override
	public int countByTitle(String keyword) {
		return sqlSession.selectOne("www.silver.dao.IF_newsSearchDAO.countByTitle", keyword);
	}

	// Ư�� Ű���尡 ���Ե� ���� ī�װ��� ��ü ������ ��ȸ
	@Override
	public int countByCategory(String keyword) {
		return sqlSession.selectOne("www.silver.dao.IF_newsSearchDAO.countByCategory", keyword);
	}
}

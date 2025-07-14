package www.silver.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import www.silver.vo.BoardVO;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDAOImpl implements IF_searchDAO {

    @Inject
    SqlSession sqlSession;

    // �˻� Ÿ���� �ۼ����� ��
    @Override
    public List<BoardVO> searchByWriter(Map<String, Object> params) {
        return sqlSession.selectList("www.silver.dao.IF_searchDAO.searchByWriter", params);
    }

    // �˻� Ÿ���� ��������� ��
    @Override
    public List<BoardVO> searchByProgrammingLanguage(Map<String, Object> params) {
        return sqlSession.selectList("www.silver.dao.IF_searchDAO.searchByProgrammingLanguage", params);
    }

    // �ۼ��ڸ� �������� �˻����� �� �˻��Ǵ� �Խñ� ���� ��ȯ
    @Override
    public int countByWriter(String keyword) {
        return sqlSession.selectOne("www.silver.dao.IF_searchDAO.countByWriter", keyword);
    }

    // ��� ������ �������� �˻����� �� �˻��Ǵ� �Խñ� ���� ��ȯ
    @Override
    public int countByProgrammingLanguage(String keyword) {
        return sqlSession.selectOne("www.silver.dao.IF_searchDAO.countByProgrammingLanguage", keyword);
    }

}

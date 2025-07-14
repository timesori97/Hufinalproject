package www.silver.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import www.silver.vo.NewsVO;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class NewsDAOImpl implements IF_newsDAO{

    @Inject
    private SqlSession sqlSession;

    // ���� ������ db ����
    @Override
    public void insertNews(NewsVO news) {
        sqlSession.insert("www.silver.dao.IF_newsDAO.insertNews", news);
    }

    // 2025�� IT ������ ����¡ ó���Ͽ� ��ȸ
    @Override
    public List<NewsVO> select2025ItNews(int start, int pageSize) {
        Map<String, Object> param = new HashMap<>();
        param.put("start", start);       // ���� ��ġ
        param.put("pageSize", pageSize); // �������� ��ȸ�� ���� ����
        return sqlSession.selectList("www.silver.dao.IF_newsDAO.select2025ItNews", param);
    }

    // ��ü ���� ������ ��ȸ�ϴ� �޼��� (����¡ ó���� ���� �� ���� ����)
    @Override
    public int selectTotalNewsCount() {
        return sqlSession.selectOne("www.silver.dao.IF_newsDAO.selectTotalNewsCount");
    }


    @Override
    public int deleteLowSimilarity(float threshold) {
        return sqlSession.delete(
                "www.silver.dao.IF_newsDAO.deleteLowSimilarity",
                threshold);
    }

}

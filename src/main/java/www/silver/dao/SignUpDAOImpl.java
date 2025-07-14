package www.silver.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import www.silver.vo.MemberVO;

@Repository
public class SignUpDAOImpl implements IF_signUpDAO {

    @Inject
    private SqlSession sqlSession;

    // 회원가입
    @Override
    public void insertAccount(MemberVO membervo) {
        // System.out.println("회원가입 DAO insertAccount 메서드 진입");
        sqlSession.insert("www.silver.dao.IF_signUpDAO.insertone", membervo);
    }

    // 아이디 중복 체크
    @Override
    public int duplicateCheckId(String userId) {
        // System.out.println("아이디 중복체크 DAO duplicateCheckId 메서드 진입");
        return sqlSession.selectOne("www.silver.dao.IF_signUpDAO.duplicateCheckId", userId);
    }

    // 로그인
    @Override
    public MemberVO loginUser(String userId, String userPassword) {
        // System.out.println("로그인 DAO 메서드 진입");
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("userPassword", userPassword);
        return sqlSession.selectOne("www.silver.dao.IF_signUpDAO.userLogin", userInfo);
    }

    // 비밀번호 변경
    @Override
    public void updateMemberPass(MemberVO membervo) {
        // System.out.println("비밀번호 변경 DAO 메서드 진입");
        sqlSession.update("www.silver.dao.IF_signUpDAO.updateMemberPass", membervo);
    }

    // 특정 회원 정보 조회
    @Override
    public MemberVO getMemberById(String userid) {
        // System.out.println("특정회원정보조회 DAO 메서드 진입");
        return sqlSession.selectOne("www.silver.dao.IF_signUpDAO.getMemberById", userid);
    }

    // 회원 정보 조회
    @Override
    public MemberVO selectByUserId(String userId) throws Exception {
        return sqlSession.selectOne("www.silver.dao.IF_signUpDAO.selectByUserId", userId);
    }

    // 회원 정보 수정
    @Override
    public int updateMember(MemberVO memberVO) throws Exception {
        return sqlSession.update("www.silver.dao.IF_signUpDAO.updateMember", memberVO);
    }

    // 회원 프로필 이미지 첨부
    @Override
    public void attachProfileImg(Map<String, Object> params) {
        sqlSession.insert("www.silver.dao.IF_signUpDAO.profile_attach", params);
    }

	@Override
	public void insertInterest(String userId, String interest) {
		Map<String, String> userInterest = new HashMap<>();
		userInterest.put("userId", userId);
		userInterest.put("interest", interest);
        sqlSession.insert("www.silver.dao.IF_signUpDAO.insertInterest", userInterest);
		
	}

	@Override
	public List<String> getProfileAttach(String userId) {
		return sqlSession.selectList("www.silver.dao.IF_signUpDAO.getProfileAttach", userId);
	}
	
	 @Override
	    public int memberreset(MemberVO memberVO){
	        return sqlSession.update("www.silver.dao.IF_signUpDAO.memberreset", memberVO);
	    }
}

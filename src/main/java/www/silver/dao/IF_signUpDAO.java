package www.silver.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import www.silver.vo.MemberVO;

public interface IF_signUpDAO {

	// ȸ������ �� ȸ���� ������ ����
	public void insertAccount(MemberVO membervo);

	// �־��� ����� ID�� �̹� �����ϴ��� �ߺ� ���θ� Ȯ��
	public int duplicateCheckId(String userId);

	// ����� ID�� ��й�ȣ�� ������� ȸ�� �α��� ������ ��ȸ
	public MemberVO loginUser(String userId, String userPassword);

	// ȸ���� ��й�ȣ�� ����
	public void updateMemberPass(MemberVO membervo);

	// ����� ID�� ȸ�� ������ ��ȸ
	public MemberVO getMemberById(String userid);

	public MemberVO selectByUserId(String userId) throws Exception;

	// ȸ�� ������ �����մϴ�.
	public int updateMember(MemberVO memberVO) throws Exception;

	// ȸ�� ������ �̹����� ÷��(���ε�)
	public void attachProfileImg(Map<String, Object> params);

	// ȸ�� �����±� ����
	public void insertInterest(String userId, String interest);

	public List<String> getProfileAttach(String userId);
	
	 public int memberreset(MemberVO memberVO);
}

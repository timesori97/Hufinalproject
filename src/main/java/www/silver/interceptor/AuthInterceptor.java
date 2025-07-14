package www.silver.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import www.silver.vo.MemberVO;

// AuthInterceptor Ŭ������ Spring MVC���� ��û�� ó���ϱ� ���� ����(�α���) ���θ� üũ�ϱ� ���� ���ͼ���
public class AuthInterceptor extends HandlerInterceptorAdapter {

	// preHandle �޼���� ��Ʈ�ѷ��� ���� �޼��尡 ȣ��Ǳ� ���� ����
	// �� �޼��忡�� ��û�� ���� ����(��: �α��� ���� Ȯ��)�� ó���� �� �ֽ��ϴ�.
	// request Ŭ���̾�Ʈ���� ���� HTTP ��û ��ü
	// response ������ Ŭ���̾�Ʈ�� ���� HTTP ���� ��ü
	// handler ȣ��� ��Ʈ�ѷ� �޼��� ��ü
	// return true�� ��ȯ�ϸ� ��û�� ��Ʈ�ѷ��� ���޵ǰ�, false�� ��ȯ�ϸ� ��û�� �ߴ�
	// throws Exception ���ܰ� �߻��� ��� ���� �����̳ʰ� ó��
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		System.out.println("���� ���ͼ��� ���� - ��û URI: " + requestURI);

		// 1�ܰ�: ���� ���� ���� Ȯ��
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("������ �������� �ʽ��ϴ�.");
			response.sendRedirect(request.getContextPath() + "/");
			return false; // ��û �ߴ�
		}

		// 2�ܰ�: �α��� ���� Ȯ��
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			System.out.println("���ǿ� �α��� ������ �������� �ʽ��ϴ�.");
			response.sendRedirect(request.getContextPath() + "/");
			return false; // ��û �ߴ�
		}

		// 3�ܰ�: ����� ID ��ȿ�� Ȯ��
		String userId = loginUser.getUserId();
		if (userId == null) {
			System.out.println("����� ID�� null�Դϴ�.");
			response.sendRedirect(request.getContextPath() + "/");
			return false; // ��û �ߴ�
		}

		System.out.println("���� �Ϸ� - ����� ID: " + userId);
		return true; // ��û ��� ����
	}
}

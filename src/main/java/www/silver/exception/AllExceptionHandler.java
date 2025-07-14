package www.silver.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = "www.silver")
public class AllExceptionHandler {

	// CustomException(����� ���� ����) �߻� �� ȣ��
	@ExceptionHandler(CustomException.class)
	public ModelAndView handleCustom(CustomException e, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("customError/error"); // error.jsp
		mav.addObject("exception", e);
		return mav;
	}

	// �� �� ��� ����(Exception) �߻� �� ȣ��
	// ���� ������ JSON ���·� ����
	// CustomErrorResponse ��ü�� ���� �ڵ�, ��û ���, �� �޽����� ��� ��ȯ
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomErrorResponse> handleUnknown(Exception e, HttpServletRequest request) {
		CustomErrorResponse response = CustomErrorResponse.builder().errorCode(ErrorCode.UNKNOWN)
				.path(request.getRequestURI()).detail(e.getMessage()).build();
		return ResponseEntity.status(ErrorCode.UNKNOWN.getStatus()).body(response);
	}
}

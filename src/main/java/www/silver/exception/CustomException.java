package www.silver.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode; // ���� �ڵ�(HTTP ����, �ڵ�, �޽��� ����)
	private final String detailMessage; // �� �޽���(�߰� ����)

	private CustomException(ErrorCode errorCode, String detail, Throwable cause) {
		// ���� �޽����� �� �޽���(detail)�� ������ �װɷ�, ������ ���� �ڵ��� �⺻ �޽����� ����
		super((detail == null) ? errorCode.getMessage() : detail, cause);
		this.errorCode = errorCode;
		this.detailMessage = detail == null ? "" : detail;
	}

	public CustomException(ErrorCode errorCode) {
		this(errorCode, errorCode.getMessage(), null);
	}

	public CustomException(ErrorCode errorCode, String detail) {
		this(errorCode, detail, null);
	}

	public CustomException(ErrorCode errorCode, Throwable cause) {
		this(errorCode, cause.getMessage(), cause);
	}

	public HttpStatus getStatus() {
		return errorCode.getStatus();
	}

	public String getCode() {
		return errorCode.getCode();
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getDetailMessage() {
		return detailMessage;
	}
}

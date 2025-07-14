package www.silver.exception;

import org.springframework.http.HttpStatus;

// ���ø����̼ǿ��� �߻��� �� �ִ� �ֿ� ���� ��Ȳ�� ������(enum)���� ����

public enum ErrorCode {

	// ���� ����
	NO_PERMISSION(HttpStatus.FORBIDDEN, "AUTH-001", "������ �����ϴ�."),

	// �Խñ� ���� ���ε� ����
	UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "FILE-001", "���� ���ε� ����"),

	// ��� ó�� ���� (��� �ۼ�, ����, ���� ��)
	COMMENT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "COMMENT-001", "��� ó�� ����"),

	// ���� ó�� ����
	REPLY_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "REPLY-001", "���� ó�� ����"),

	// �ý��ۿ��� ó������ ���� ���� �߻� �� Fallback �ڵ�
	UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "SYS-999", "�� �� ���� ����");

	private final HttpStatus status; // HTTP ���� �ڵ� (��: 403, 500 ��)
	private final String code; // ���ø����̼� ���� ���� �ڵ� (��: "AUTH-001")
	private final String message; // ����ڿ��� ������ ���� �޽���

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}

package www.silver.exception;

/**
 * API/�� ��û���� ���� �߻� �� Ŭ���̾�Ʈ���� ��ȯ�ϴ� ǥ�� ���� ���� ��ü�Դϴ�. - ���� ������ ����� �����ϰ� ������ �� �ֽ��ϴ�.
 */
public class CustomErrorResponse {
	private final long timestamp; // ���� �߻� �ð� (�и���)
	private final int status; // HTTP ���� �ڵ�
	private final String code; // ���ø����̼� ���� �ڵ�
	private final String message; // �⺻ ���� �޽���
	private final String path; // ��û�� �߻��� URL ���
	private final String detail; // �߰� �� �޽���

	// Builder ��ü�� �޾� ���� �ʵ带 �ʱ�ȭ
	private CustomErrorResponse(Builder b) {
		this.timestamp = System.currentTimeMillis();
		this.status = b.errorCode.getStatus().value(); // HTTP ���� �ڵ�
		this.code = b.errorCode.getCode(); // ���ø����̼� ���� �ڵ�
		this.message = b.errorCode.getMessage(); // �⺻ ���� �޽���
		this.path = b.path; // ��û URL
		this.detail = b.detail; // �� �޽���
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

	public String getDetail() {
		return detail;
	}

	// CustomErrorResponse ��ü ������ ���� Builder�� ��ȯ
	public static Builder builder() {
		return new Builder();
	}

	// CustomErrorResponse ��ü ������ ���� ���� Ŭ����
	public static class Builder {
		private ErrorCode errorCode;
		private String path = "";
		private String detail = "";

		public Builder errorCode(ErrorCode ec) {
			this.errorCode = ec;
			return this;
		}

		public Builder path(String path) {
			this.path = path;
			return this;
		}

		public Builder detail(String d) {
			this.detail = d;
			return this;
		}

		// CustomErrorResponse ��ü�� ����
		public CustomErrorResponse build() {
			return new CustomErrorResponse(this);
		}
	}
}

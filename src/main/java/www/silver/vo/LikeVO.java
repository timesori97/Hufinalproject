package www.silver.vo;

public class LikeVO {
    private int likeNum;   // ���ƿ� ���� ��ȣ
    private String userId; // ���ƿ並 ���� ������� ID
    private int postNum;   // ���ƿ䰡 �޸� �Խñ��� ��ȣ (�Խñ� ID)
    
    public int getLikeNum() {
        return likeNum;
    }
    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getPostNum() {
        return postNum;
    }
    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }
}

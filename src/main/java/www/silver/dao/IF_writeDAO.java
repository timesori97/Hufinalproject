package www.silver.dao;

import java.util.List;
import java.util.Map;
import www.silver.vo.BoardVO;
import www.silver.vo.CommentVO;

public interface IF_writeDAO {

	// 새로운 게시물을 저장
	public void insertWrite(BoardVO boardvo);

	// 게시물에 첨부된 파일명을 저장
	public void attachFname(Map<String, Object> params);

	// 모든 게시물 목록을 조회
	public List<BoardVO> selectall();

	// 페이지 처리를 위한 특정 페이지의 게시물 목록을 조회
	public List<BoardVO> pagingList(Map<String, Integer> pagingParams);

	// 전체 게시물의 개수를 반환한다. (페이징에 사용)
	public int boardCount();

	// 특정 게시물 번호에 해당하는 게시물 상세 정보를 조회
	public BoardVO selectOne(Long postNum);

	// 특정 게시물에 첨부된 파일명 리스트를 조회
	public List<String> getAttach(Long postNum);

	// 특정 게시물을 삭제
	public void deleteWrite(Long postNum);

	// 특정 게시물의 내용을 수정
	public void modifyWrite(BoardVO boardVO);

	// 특정 게시물에 첨부된 파일 정보를 삭제
	public void deleteAttach(Long postNum);

	// 특정 게시물의 조회수를 1 증가
	public void viewCount(Long postNum);

	// 새로운 댓글 저장
	public void insertCommentWrite(CommentVO commentvo);

	// 특정 게시물에 달린 모든 댓글 목록을 조회
	public List<CommentVO> selectAllComment(Long postNum);

	// 특정 댓글을 삭제 (작성자 확인 후 삭제)
	public boolean deleteComment(int commentId, String userId);

	// 특정 댓글의 내용을 수정
	public boolean updateComment(int commentId, String content, Long postNum);

	// 특정 댓글 ID에 해당하는 댓글 정보를 조회한다.
	public CommentVO getCommentById(int parentCommentId);

	// 대댓글 저장
	public void addCommentReply(CommentVO commentvo);

	// 특정 대댓글의 내용을 수정
	public boolean updateReplyComment(int commentId, String content, Long postNum);

	// 특정 회원의 전체 게시물 조회수 합산을 반환
	public int viewtotal(String id);

	// 특정 회원이 받은 전체 좋아요 수를 반환
	public int likestotal(String id);

	// 특정 회원이 좋아요를 누른 게시물 목록을 조회
	public List<BoardVO> likeboard(Map<String, Object> params);

	// 특정 회원이 누른 좋아요의 총 개수를 반환
	public int clicklikes(String id);

	// 특정 회원의 모든 게시물/댓글/정보를 삭제한다.
	public void deletemember(String userId);

	// 전체 게시글 조회
	public int getTotalCount();
}
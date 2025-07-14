package www.silver.service;

import java.util.List;

import www.silver.vo.BoardVO;
import www.silver.vo.CommentVO;
import www.silver.vo.PageVO;

public interface IF_writeService {

    // 새로운 게시물을 등록
    public void addWrite(BoardVO boardvo);

    // 모든 게시물 목록을 조회
    public List<BoardVO> boardAllView();

    // 페이지 처리를 위한 특정 페이지의 게시물 목록을 조회
    public List<BoardVO> pagingList(int page);

    // 페이지 정보를 계산하여 반환
    public PageVO pagingParam(int page);

    // 특정 게시물 번호에 해당하는 게시물 상세 정보를 조회
    public BoardVO textview(Long postNum);

    // 특정 게시물에 첨부된 파일명 리스트를 조회
    public List<String> getAttach(Long postNum);

    // 특정 게시물을 삭제
    public void deleteWrite(Long postNum);

    // 특정 게시물의 내용을 수정
    public void modifyWrite(BoardVO boardvo);

    // 특정 게시물의 조회수를 1 증가
    public void viewCount(Long postNum);

    // 새로운 댓글을 등록
    public void addCommentWrite(CommentVO commentvo);

    // 특정 게시물에 달린 모든 댓글 목록을 조회
    public List<CommentVO> getComments(Long postNum);

    // 특정 댓글을 삭제
    public boolean deleteComment(int commentId, String userId);

    // 특정 댓글의 내용을 수정
    public boolean updateComment(int commentId, String content, Long postNum);

    // 대댓글(댓글의 답글)을 추가
    public void addCommentReply(CommentVO commentVO);

    // 특정 대댓글의 내용을 수정
    public boolean updateReply(int commentId, String content, Long postNum);

    // 특정 회원의 전체 게시물 조회수를 합산하여 반환
    public int viewtotal(String id);

    // 특정 회원이 받은 전체 좋아요 수를 반환
    public int likestotal(String id);

    // 특정 회원이 좋아요를 누른 게시물 목록을 조회
    public List<BoardVO> likeboard(String id, int page);

    // 특정 회원이 누른 좋아요의 총 개수를 반환
    public int clicklikes(String id);

    // 회원 탈퇴 시 회원의 모든 게시물/댓글/정보를 삭제
    public void deletemember(String userId);

    // 전체 게시물 수를 반환
    public int getTotalCount();
}
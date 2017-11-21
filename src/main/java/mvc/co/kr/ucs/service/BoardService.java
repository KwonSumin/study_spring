package mvc.co.kr.ucs.service;

import java.util.List;

import mvc.co.kr.ucs.bean.BoardBean;

public interface BoardService {
	public int insertBoard(BoardBean bean);
	public List<BoardBean> getList(BoardBean bean);
	public BoardBean getBoard(BoardBean bean);
	public int getTotal(BoardBean bean);
}

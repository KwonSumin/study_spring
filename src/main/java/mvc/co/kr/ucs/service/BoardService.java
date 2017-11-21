package mvc.co.kr.ucs.service;

import java.util.List;

import co.kr.ucs.spring.bean.PagingBean;
import mvc.co.kr.ucs.bean.BoardBean;

public interface BoardService {
	public int insertBoard(BoardBean bean);
	public List<BoardBean> getList(PagingBean bean);
	public BoardBean getBoard(BoardBean bean);
}

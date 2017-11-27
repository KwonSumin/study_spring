package mvc.co.kr.ucs.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mvc.co.kr.ucs.bean.BoardBean;

public interface BoardService {
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int insertBoard(BoardBean bean);
	public List<BoardBean> getList(BoardBean bean);
	public BoardBean getBoard(BoardBean bean);
	public int getTotal(BoardBean bean);
}

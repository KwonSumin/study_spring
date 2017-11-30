package mvc.co.kr.ucs.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mvc.co.kr.ucs.bean.BoardBean;

public interface BoardService {
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int insertBoard(BoardBean bean)throws SQLException;
	public List<BoardBean> getList(BoardBean bean)throws SQLException;
	public BoardBean getBoard(BoardBean bean)throws SQLException;
	public int getTotal(BoardBean bean)throws SQLException;
}

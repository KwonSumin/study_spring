package mvc.co.kr.ucs.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mvc.co.kr.ucs.bean.BoardBean;
import mvc.co.kr.ucs.dao.StudyDAO;


@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private StudyDAO dao;
	
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int insertBoard(BoardBean bean)throws SQLException{
		int result = 0;
		result = dao.insert("board.insert",bean);
		//int test = Integer.parseInt("adsfdsf");
		return result;
	}

	public List<BoardBean> getList(BoardBean bean) throws SQLException{
		return (List<BoardBean>)dao.selectList("board.list",bean);
	}

	public BoardBean getBoard(BoardBean bean) throws SQLException{
		return (BoardBean)dao.SelectOne("board.getBoard", bean);
	}

	public int getTotal(BoardBean bean) throws SQLException{
		return (Integer)dao.SelectOne("board.total",bean);
	}
	
}

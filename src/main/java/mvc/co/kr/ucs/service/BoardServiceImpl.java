package mvc.co.kr.ucs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mvc.co.kr.ucs.bean.BoardBean;
import mvc.co.kr.ucs.dao.StudyDAO;


@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private StudyDAO dao;
	
	public int insertBoard(BoardBean bean) {
		return dao.insert("board.insert",bean);
	}

	public List<BoardBean> getList(BoardBean bean) {
		return (List<BoardBean>)dao.selectList("board.list",bean);
	}

	public BoardBean getBoard(BoardBean bean) {
		return (BoardBean)dao.SelectOne("board.getBoard", bean);
	}

	public int getTotal(BoardBean bean) {
		return (Integer)dao.SelectOne("board.total",bean);
	}
	
}

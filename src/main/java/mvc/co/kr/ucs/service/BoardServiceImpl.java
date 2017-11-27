package mvc.co.kr.ucs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import mvc.co.kr.ucs.bean.BoardBean;
import mvc.co.kr.ucs.dao.StudyDAO;


@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private StudyDAO dao;
	
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int insertBoard(BoardBean bean){
		int result = 0;
		try {
			result = Integer.parseInt(dao.insert("board.insert",bean) +"asdf");
		}catch(Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
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

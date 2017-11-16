package mvc.co.kr.ucs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mvc.co.kr.ucs.dao.StudyDAO;

@Service
public class BoardService implements CommonService {
	
	@Autowired
	private StudyDAO dao;

	public List BoardList() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updateBoard() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeBoard() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean writeBoard() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private int getTotalData() {
		return -1;
	}
}

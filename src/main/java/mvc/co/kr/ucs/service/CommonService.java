package mvc.co.kr.ucs.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CommonService {
	public List BoardList();
	public boolean updateBoard();
	public boolean removeBoard();
	public boolean writeBoard();
}

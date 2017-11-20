package mvc.co.kr.ucs.template.board.service;



import org.springframework.stereotype.Service;

import mvc.co.kr.ucs.common.bean.BoardTemplateBean;
import mvc.co.kr.ucs.common.bean.CommonQueryBean;
import mvc.co.kr.ucs.common.service.CommonService;
import mvc.co.kr.ucs.template.board.bean.BoardBean;

@Service
public abstract class AbstractBoardService extends CommonService{
	abstract public BoardTemplateBean getList(BoardTemplateBean template);
	abstract public int insertBoard(CommonQueryBean param);
	abstract public int removeBoard(CommonQueryBean param);
	abstract public int deleteBoard(CommonQueryBean param);
	abstract public BoardBean getBoard(CommonQueryBean param);
	abstract public int modifyBoard(CommonQueryBean param);
}

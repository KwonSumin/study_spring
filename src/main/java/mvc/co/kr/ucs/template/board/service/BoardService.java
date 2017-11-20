package mvc.co.kr.ucs.template.board.service;


import java.util.List;

import org.springframework.stereotype.Service;

import mvc.co.kr.ucs.common.bean.BoardTemplateBean;
import mvc.co.kr.ucs.common.bean.CommonQueryBean;
import mvc.co.kr.ucs.common.bean.PagingBean;
import mvc.co.kr.ucs.template.board.bean.BoardBean;

@Service
public class BoardService extends AbstractBoardService{
	
	private final String  tableName = "board";
	private final String seq = "seq";
	private final String primaryKey = "seq";
	
	@Override
	public BoardTemplateBean getList(BoardTemplateBean template) {
		PagingBean paging =  template.getPaging();
		if(paging.getSearchTarget()!=null && !paging.getSearchTarget().equals(""))
			paging.addIf(paging.getSearchTarget(), paging.getSearch(),"like");
		else
			paging.setIfStatement(null);
		setParam(template.getPaging());
		int total = (Integer)dao.SelectOne("common.total", template.getPaging());
		template.getPaging().setTotalData(total);
		List list = dao.selectList("common.list",template.getPaging());
		template.setList(list);
		return template;
	}

	@Override
	public int insertBoard(CommonQueryBean param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeBoard(CommonQueryBean param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBoard(CommonQueryBean param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BoardBean getBoard(CommonQueryBean param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int modifyBoard(CommonQueryBean param) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	private CommonQueryBean setParam(PagingBean param) {
		param.setTableName(tableName);
		param.setPrimaryKey(primaryKey);
		param.setSeqName(seq);
		param.setPaging();
		return param;
	}
	
}

package mvc.co.kr.ucs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mvc.co.kr.ucs.bean.BoardBean;
import mvc.co.kr.ucs.bean.PagingBean;

@Controller
public class MvcBoardCtrl {
	
	@RequestMapping(value="/mvc/board/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView mav,BoardBean bean,PagingBean paging) {
		//TODO : list
		return mav;
	}
	
	@RequestMapping(value="/mvc/board_json/list",method=RequestMethod.GET)
	public void jsonList(ModelAndView mav,PagingBean paging) {
		//TODO : list
	}
	
	@RequestMapping(value="/mvc/board/read",method=RequestMethod.GET)
	public ModelAndView readBoard(ModelAndView mav,BoardBean bean) {
		//TODO : readBoard
		return mav;
	}
	

	@RequestMapping(value="/mvc/board/insert",method=RequestMethod.GET)
	public ModelAndView insert(ModelAndView mav,BoardBean bean) {
		//TODO : insert
		return mav;
	}
}

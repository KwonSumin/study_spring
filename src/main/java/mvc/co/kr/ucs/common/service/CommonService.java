package mvc.co.kr.ucs.common.service;

import org.springframework.beans.factory.annotation.Autowired;

import mvc.co.kr.ucs.common.bean.CommonQueryBean;
import mvc.co.kr.ucs.common.dao.StudyDAO;

public class CommonService {
	@Autowired
	protected StudyDAO dao;
	
	protected int insertService(String namespace, CommonQueryBean bean) {
		return dao.insert(namespace, bean);
	}

	protected int removeService(String namespace, CommonQueryBean bean) {
		return dao.insert(namespace, bean);
	}

	protected int deleteService(String namespace, CommonQueryBean bean) {
		return dao.insert(namespace, bean);
	}

	protected int updateService(String namespace, CommonQueryBean bean) {
		return dao.insert(namespace, bean);
	}

	protected int selectListService(String namespace, CommonQueryBean bean) {
		return dao.insert(namespace, bean);
	}

	protected int selectOneService(String namespace, CommonQueryBean bean) {
		return dao.insert(namespace, bean);
	}
}

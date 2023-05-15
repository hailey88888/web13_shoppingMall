package com.ezenac.controller.admin.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.controller.action.Action;
import com.ezenac.dao.AdminDao;
import com.ezenac.dto.AdminVO;
import com.ezenac.dto.QnaVO;
import com.ezenac.util.Paging;

public class AdminQnaListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "/admin/qna/qnaList.jsp";
		
		HttpSession session = request.getSession();
		AdminVO avo = (AdminVO) session.getAttribute("loginAdmin");
		if( avo==null) url = "shop.do?command=admin";
		else {
			
			String sub=request.getParameter("sub");
			if(sub!=null) {    // if(sub.equals("y"))  이와 같이 쓰지 않는 이유는 sub  가  null  일때 에러를 발생하기 때문
				session.removeAttribute("key");
				session.removeAttribute("page");
			}
			
			String key="";
			if( request.getParameter("key")!=null) {
				key = request.getParameter("key");
				session.setAttribute("key", key);
			} else if( session.getAttribute("key")!=null) {
				key = (String)session.getAttribute("key");
			} else {
				session.removeAttribute("key");
			}
			
			int page=1;
			if( request.getParameter("page")!=null) {
				page = Integer.parseInt( request.getParameter("page") );
				session.setAttribute("page", page);
			} else if( session.getAttribute("page")!=null) {
				page = (int)session.getAttribute("page");
			} else {
				session.removeAttribute("page");
			}
			
			Paging paging = new Paging();
			paging.setPage(page);
			
			AdminDao adao = AdminDao.getInstance();
			int count = adao.getAllCountForQna(key);
			// int count = adao.getAllCount("qna", "subject", "content", key);
			paging.setTotalCount(count);
			
			ArrayList<QnaVO> list = adao.listQna( paging, key );
			
			request.setAttribute("qnaList", list);
			request.setAttribute("paging" , paging );
			request.setAttribute("key", key);
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
}















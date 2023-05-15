package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.dao.MemberDao;
import com.ezenac.dto.MemberVO;

public class FindIdStep1Action implements Action {
// 아이디 찾는 기능 구현 : 이름, 전화번호를 얻고 확인 
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "member/findIdForm.jsp";
		
		
		//request.getParameter(파라미터 );client 요청중 파라미터 값이 있으면 모두 불러오기 
	
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		
		// dao 생성 
		MemberDao mdao = MemberDao.getInstance();
		
		// dao.의 메소드 호출해서 name, phone을 얻고 mvo에 저장 
		MemberVO mvo = mdao.getMemberByname(name, phone);
		
		// request.setAttribute("이름",변수(혹은 파라미터)) : servlet에서 client요청 객체(request)에 데이터를 저장하기 
		// 												jsp 페이지에서 사용이 가능하게 해줌 
		request.setAttribute("name", name);
		request.setAttribute("phone", phone);
		if( mvo == null ) { // phone, name의 정보가 없는 경우 
			request.setAttribute("msg", "해당이름의 회원이 없습니다");
		} else {
			request.setAttribute("MemberVO", mvo);
			url = "member/findIdconfirmNumber.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);
		
		/*
		 [1] request.getRequestDispatcher(url).forward(request, response);
		 - request와 response 객체를 이용하여 요청된 url로 서블릿 컨테이너가 처리를 위임하는 역할
		 
		 [2] forward(request, response)
		 - 현재 실행 중인 서블릿에서 요청된 request와 response 객체를 다른 서블릿이나 JSP 등으로 전달
		 	=> 해당 리소스에 대한 처리를 위임
		 - 클라이언트 요청에 대한 처리를 현재 서블릿이 아닌 다른 리소스에서 처리하도록 위임
		 
		 [3]request.getRequestDispatcher(url)
		 - 현재 Servlet에서 다른 Servlet이나 JSP 페이지로 요청을 전달하기 위한 메소드
	 
		 */
		
		
	}

}













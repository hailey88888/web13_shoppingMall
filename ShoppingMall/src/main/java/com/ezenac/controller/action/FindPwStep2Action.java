package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindPwStep2Action implements Action {
// 비번 찾는 기능 : 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// client 로부터 요청정보를 얻고 string저장한후 
		String confirmNum = request.getParameter("confirmNum");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		
		// 요청 객체에 저장하고 
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("phone", phone);
		
		String url = "member/findPwconfirmNumber.jsp";
		
		// 확인인증번호가 0000인경우 비번 다시 설정하는 페이지로 이동 
		if( confirmNum.equals("0000") )
			url = "member/resetPw.jsp";
		
		// 그게 아닌경우 요청객체에 저장한 정보들을 가지고 비번확인페이지로 이동하기 
		request.getRequestDispatcher(url).forward(request, response);

	}

}

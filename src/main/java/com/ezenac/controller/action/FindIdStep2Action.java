package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.dto.MemberVO;

public class FindIdStep2Action implements Action {
// 아이디 찾는 기능 구현 : 회원정보를 얻고 전화번호 인증번호 확인하는 과정 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String confirmNum = request.getParameter("confirmNum");
		//request.getParameter(파라미터 );client 요청중 파라미터 값이 있으면 모두 불러오기 

		MemberVO mvo = new MemberVO();
		// client로 부터 얻은 요청정보를 set()를 통해 저장하기 
		mvo.setId( request.getParameter("id") );
		mvo.setName( request.getParameter("name") );
		mvo.setPhone( request.getParameter("phone") );
		request.setAttribute("name", mvo.getName());
		// request.setAttribute("이름",변수(혹은 파라미터)) : servlet에서 client요청 객체(request)에 데이터를 저장하기 
		// 												jsp 페이지에서 사용이 가능하게 해줌 
		request.setAttribute("phone", mvo.getPhone());
		request.setAttribute("id", mvo.getId());
		request.setAttribute("MemberVO", mvo);
		
		String url = "member/viewId.jsp";
		if(!confirmNum.equals("0000")) { //confirmNum라고 요청한 정보중 0000이라고 입력된 경우 
			request.setAttribute("msg", "인증번호가 맞지 않습니다");
			url = "member/findIdconfirmNumber.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);
	}//request와 response 객체를 이용(클라이언트 요청)하여 요청된 url로 서블릿 컨테이너가 처리를 위임하는 역할(다른 서블릿이나 JSP 등으로 전달)
	

}

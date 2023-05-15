package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.dao.MemberDao;
import com.ezenac.dto.MemberVO;

public class FindPwStep1Action implements Action {
// 비번 찾는 기능 구현 : 회원정보얻고 저장한후 전화번호와 일치하는지 확인 하는 과정 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.getParameter(파라미터 );client 요청중 파라미터 값이 있으면 모두 불러오기 

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		
		// dao 생성 
		MemberDao mdao = MemberDao.getInstance();
		// dao.getMember()호출, client로 부터 받은 값을 파라미터로 ->호출해서 얻은 정보를 mvo저장하기 
		MemberVO mvo = mdao.getMember(id);
		
		String url = "member/findPwForm.jsp";
		
		// request.setAttribute("이름",변수(혹은 파라미터)) : servlet에서 client요청 객체(request)에 데이터를 저장하기 
		// 												jsp 페이지에서 사용이 가능하게 해줌 
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("phone", phone);
		
		if( mvo == null ) {
			request.setAttribute("msg", "id와 이름과 번화번호가 일치하는 회원이 없습니다");
		} else if( ( !name.equals( mvo.getName() ) ) || ( !phone.equals( mvo.getPhone() ) ) ) {
			request.setAttribute("msg", "id와 이름과 번화번호가 일치하는 회원이 없습니다");
		} else {
			url = "member/findPwconfirmNumber.jsp";
		}			
		request.getRequestDispatcher(url).forward(request, response);
	}

}

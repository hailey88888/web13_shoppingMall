package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.dao.MemberDao;
import com.ezenac.dto.MemberVO;

public class IdCheckFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//client로 부터 요청객체 얻기 
		String id = request.getParameter("id");
		//dao생성하기 
		MemberDao mdao = MemberDao.getInstance();
		// dao.getMember()호출, client로 부터 받은 값을 파라미터로 ->호출해서 얻은 정보를 mvo저장하기 
		MemberVO mvo = mdao.getMember(id);
		
		// 회원정보를 토대로 만약에 null이 나온경우 -1을 요청객체에 저장 
		if( mvo == null ) request.setAttribute("result" , -1);
		else request.setAttribute("result", 1);
		
		// id를 요청객체에 저장 
		request.setAttribute("id", id);
		
		// 전달할 인터페이스 생성, 전달하는 메소드 호출, url 파라미터 
		RequestDispatcher dp =request.getRequestDispatcher("member/idcheck.jsp");
		// 요청객체에 들은 데이터를 전송하기위한 메소드 호출 
		dp.forward(request, response);
	}

}

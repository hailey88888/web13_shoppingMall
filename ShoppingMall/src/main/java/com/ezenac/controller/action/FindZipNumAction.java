package com.ezenac.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.dao.MemberDao;
import com.ezenac.dto.AddressVO;

public class FindZipNumAction implements Action {
// 주소찾는 기능 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// client로 부터 요청정보를 얻고 
		String dong=request.getParameter("dong");
		
		// 얻은 요청정보가 null이 아니고 
		if( dong != null) {
			// ""인 경우 false임 
			if( dong.equals("") == false ) {
				
					// 이안에 dao를 생성하는 이유는 오직 요청정보에 공백란이 생긴 경우에만 적용할상황이기 때문에 
					// 이안에 dao를 생성해야함
				
				//dao를 생성한뒤 
				MemberDao mdao = MemberDao.getInstance();
				// 요청정보를 파라미터로 해서 selectAddress를 호출해서 주소 정보를 얻고 => list에 저장하기 
				ArrayList<AddressVO> list = mdao.selectAddress( dong );
				//그걸 요청 객체에 저장하고 
				request.setAttribute("addressList", list);
			}
		}
		RequestDispatcher dp = request.getRequestDispatcher("member/findZipNum.jsp");
		// 정보를 전달할 수 있는 인터페이스를 생성 dp
		// 그리고 전달할 위치를 파라미터로 해서 현재 servlet에서 다른 servlet or JSP로 이동해주는 메서드를 호출 
		
		dp.forward(request, response); 
		// 요청객체에 저장되어있는 데이터를 그대로 써있는 url에 전달해줌 
	}
}

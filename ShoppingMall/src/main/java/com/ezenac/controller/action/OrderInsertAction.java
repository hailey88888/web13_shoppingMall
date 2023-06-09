package com.ezenac.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.dao.CartDao;
import com.ezenac.dao.OrderDao;
import com.ezenac.dto.CartVO;
import com.ezenac.dto.MemberVO;

public class OrderInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 로그인 유저의 아이디로 카드를 검색해서  그 목록을 오더테이블에 추가합니다
		String url = "";
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
		if (mvo == null) {
		    url = "shop.do?command=loginForm";
		}else {
			CartDao cdao = CartDao.getInstance();
			// 주문자 아이디로 검색한 카트 목록(지금 주문 처리할) 목록을 먼저 조회합니다
			ArrayList<CartVO> list = cdao.selectCart( mvo.getId() );
			
			OrderDao odao = OrderDao.getInstance();
			// 추출한 list 와 주문자의 아디를 갖고 OrderDao 에 가서 오더 와 오더 디테일에 데이터를 추가합니다.
			// 방금 추가한 주문의 주문번호를 리턴받습니다
			int Oseq = odao.insertOrder(list, mvo.getId());
			
			// 방금 주문에 성공한 주문 번호를 갖고 오더 리스트로 이동하여 주문번호로 주문 내역을 다시 조회하고jsp로 이동합니다
			url = "shop.do?command=orderList&oseq="+ Oseq;
		}
		response.sendRedirect(url);
	}
}

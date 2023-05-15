package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.dao.CartDao;
import com.ezenac.dto.CartVO;
import com.ezenac.dto.MemberVO;

public class CartInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// cartvo 에 전달 파라미터를 넣고, cart 에 레코드를 추가합니다.
		
		// 로그인 유저 점검 후, 아무도 로그인이 안되어 있다면 로그인 페이지로 이동
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		String url="";
		if( mvo == null ) {
			url = "shop.do?command=loginForm";
		}else {
			//로그인 유의 아이디, 전달된 파라미터의 수량과 상품번호를 cartvo 에 넣고 추가메서드를 호출
			CartVO cvo = new CartVO();
			cvo.setId( mvo.getId() );
			cvo.setPseq( Integer.parseInt( request.getParameter("pseq") ) );
			cvo.setQuantity( Integer.parseInt( request.getParameter("quantity") ) );
			CartDao cdao = CartDao.getInstance();
			cdao.insertCart( cvo );
			url = "shop.do?command=cartList";
			// insert 의 대상은 cart 테이블,  select 의 대상은 cart_view 뷰
		}
		// 추가한 후에, 카트 리스트를 조회해서 cartList.jsp 로 이동하도록  shop.do?command=cartList 로 이동 합니다
		response.sendRedirect(url);
	}

}

package com.ezenac.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.dao.OrderDao;
import com.ezenac.dto.MemberVO;
import com.ezenac.dto.OrderVO;

public class OrderAllAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "mypage/mypage.jsp";
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
	    if(mvo==null) {
	    	url = "shop.do?command=login_form";
	    }else {
	    	OrderDao odao = OrderDao.getInstance();
	    	ArrayList<OrderVO> finalList = new ArrayList<OrderVO>();  // 아래에서 언급한 별도의 리스트, mypage.jsp 에 전달될 리스트
	    	ArrayList<Integer> oseqList	= odao.selectOseqOrderAll( mvo.getId() );  // 주문번호들의 리스트, 중복을 없앤 검색
	    	for(Integer oseq : oseqList) { // 리스트에 담긴 주문 번호들을 하나씩 꺼내서 반복실행
	    		ArrayList<OrderVO> orderListByOseq = odao.listOrderByOseq( oseq );  // 주문 번호별 주문상품 리스트
	    		OrderVO ovo = orderListByOseq.get(0);
	    		ovo.setPname( ovo.getPname() + " 포함 " + orderListByOseq.size() + " 건");
	    		int totalPrice = 0;
	            for (OrderVO ovo1 : orderListByOseq)  
	            	totalPrice += ovo1.getPrice2() * ovo1.getQuantity();
	            ovo.setPrice2(totalPrice);
	            finalList.add(ovo);
	    	}
	    	request.setAttribute("orderList", finalList);
	    	request.setAttribute("title", "총 주문 내역");
	    }
	    request.getRequestDispatcher(url).forward(request, response);
	}
}

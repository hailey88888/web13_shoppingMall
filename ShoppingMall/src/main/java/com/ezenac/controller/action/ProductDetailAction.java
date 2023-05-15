package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.dao.ProductDao;
import com.ezenac.dto.ProductVO;

public class ProductDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 전달된 상품번호로 상품 내역을 조회합니다
		int pseq = Integer.parseInt( request.getParameter("pseq") );
		ProductDao mdao = ProductDao.getInstance();
		ProductVO pvo = mdao.getProduct(pseq);
		
		// 조회된 상품을 request 에 담고, productDetail.jsp  로 이동합니다
		request.setAttribute("productVO", pvo);
		//RequestDispatcher dp = request.getRequestDispatcher("product/productDetail.jsp");
		//dp.forward(request, response);
		String url = "product/productDetail.jsp";
		
		request.getRequestDispatcher(url).forward(request, response);
		
		
		
	}

}

package com.ezenac.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.dao.ProductDao;
import com.ezenac.dto.ProductVO;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 신상품 베스트 상품 네개씩 조회하고, request 에 다아서  index.jsp 로 이동합니다
		ProductDao pdao = ProductDao.getInstance();
		
		ArrayList<ProductVO> bestList = pdao.getBestList();
		ArrayList<ProductVO> newList = pdao.getNewList();
		
		request.setAttribute("bestList", bestList);
		request.setAttribute("newList", newList);
		
		//System.out.println(bestList.size());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);

	}

}

package com.ezenac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ezenac.dto.CartVO;
import com.ezenac.dto.OrderVO;
import com.ezenac.dto.ProductVO;
import com.ezenac.util.Dbman;

public class OrderDao {

	private OrderDao() {}
	private static OrderDao itc = new OrderDao();
	public static OrderDao getInstance() { return itc; }
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public int insertOrder(ArrayList<CartVO> list, String id) {
		int oseq = 0;
		con = Dbman.getConnection();
		// 1. 주문 번호(시퀀스 자동입력)와 구매자 아이디로 orders 테이블에 레코드 추가
		String sql = "insert into orders(oseq, id) values( orders_seq.nextVal , ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			// 2. Orders 테이블에 시퀀스로 입력된 가장 마지막(방금추가한) 주문 번호 조회
			pstmt.close();
			sql = "select max(oseq) as max_oseq from orders";
			pstmt = con.prepareStatement(sql); 
			rs = pstmt.executeQuery();
			if(rs.next()) oseq = rs.getInt("max_oseq");
			
			// 3. list 의 카트목록들을 Orders 에서 얻은 max_oseq 와 함꼐 order_detail 에 추가
			pstmt.close();
			for( CartVO cvo : list) {
				// 카트 목록을 하나씩 꺼내서  oseq 와 함께 order_detail 테이블에 추가하고
				sql = "insert into order_detail(odseq, oseq, pseq, quantity) "
						+ "values(order_detail_seq.nextVal, ?, ?, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, oseq);
				pstmt.setInt(2, cvo.getPseq() );
				pstmt.setInt(3, cvo.getQuantity() );
				pstmt.executeUpdate();
				
				// 4. order_detail 에 추가된 카트 내용은  cart 테이블에서 처리되었으므로 삭제 또는 result  를 2로 변경 또는 삭제
				pstmt.close();
				// sql = "delete from cart where cseq = ?"; 
				sql = "Update cart set result='2' where cseq=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cvo.getCseq());
				pstmt.executeUpdate();
				
			}
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}		
		// 5. 주문번호oseq 를 return
		return oseq;
	}

	public ArrayList<OrderVO> listOrderByOseq(int oseq) {
		ArrayList<OrderVO> list = new ArrayList<OrderVO>();
		String sql = "select * from order_view where oseq=?";
		
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, oseq);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				    OrderVO ovo = new OrderVO();
					ovo.setOdseq(rs.getInt("odseq"));
					ovo.setOseq(rs.getInt("oseq"));
					ovo.setId(rs.getString("id"));
					ovo.setIndate(rs.getTimestamp("indate"));
					ovo.setMname(rs.getString("mname"));
					ovo.setZip_num(rs.getString("zip_num"));
					ovo.setAddress(rs.getString("address"));
					ovo.setPhone(rs.getString("phone"));
					ovo.setPname(rs.getString("pname"));
					ovo.setPrice2(rs.getInt("price2"));
					ovo.setPseq(rs.getInt("pseq"));
					ovo.setQuantity(rs.getInt("quantity"));
					ovo.setResult(rs.getString("result"));
					list.add(ovo);
			}
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}
		return list;
	}

	public ArrayList<Integer> selectOseqOrderIng(String id) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String sql = "select distinct oseq from order_view where id=? and result='1'  order by oseq desc";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  id);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				list.add( rs.getInt(1) );
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}
		return list;
	}

	public ArrayList<Integer> selectOseqOrderAll(String id) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String sql = "select distinct oseq, result from order_view where id=?  order by result, oseq desc";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  id);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				list.add( rs.getInt(1) );
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}
		return list;
	}

	public int insertOrderOne(ProductVO pvo, String id, int quantity) {
		int oseq = 0;
		con = Dbman.getConnection();
		String sql = "insert into orders(oseq, id) values( orders_seq.nextVal , ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();

			sql = "select max(oseq) as max_oseq from orders";
			pstmt = con.prepareStatement(sql); 
			rs = pstmt.executeQuery();
			if(rs.next()) oseq = rs.getInt("max_oseq");
			pstmt.close();

			sql = "insert into order_detail(odseq, oseq, pseq, quantity) "
						+ "values(order_detail_seq.nextVal, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, oseq);
			pstmt.setInt(2, pvo.getPseq() );
			pstmt.setInt(3, quantity );
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}		
		// 5. 주문번호oseq 를 return
		return oseq;
	}

	public void deleteOrders(Integer oseq) {
		String sql = "delete from orders where oseq=?";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  oseq);
			pstmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}		
	}

	public void deleteOrder_detail(Integer oseq) {
		String sql = "delete from order_detail where oseq=?";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  oseq);
			pstmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}		
		
	}
	
	
}













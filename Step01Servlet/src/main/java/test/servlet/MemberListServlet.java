package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.dto.MemberDto;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<MemberDto> list=new ArrayList<>();
		list.add(new MemberDto(1, "김구라", "노량진"));
		list.add(new MemberDto(2, "해골", "행신동"));
		list.add(new MemberDto(3, "원숭이", "상도동"));
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter pw=resp.getWriter();
		
		pw.println("<!doctype html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset='utf-8'>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<table border='1'>");
		pw.println("<caption>회원 목록</caption>");
		pw.println("<colgroup>"
				+ "<col width='50'>"
				+ "<col width='200'>"
				+ "<col width='200'>"
				+ "</colgroup>");
		pw.println("<thead>");
			pw.println("<tr>");
				pw.println("<th>번호</th>");
				pw.println("<th>이름</th>");
				pw.println("<th>주소</th>");
			pw.println("</tr>");
		pw.println("</thead>");
		pw.println("<tbody>");
		for(MemberDto tmp:list) {
			pw.println("<tr>");
				pw.print("<td>"+tmp.getNum()+"</td>");
				pw.print("<td>"+tmp.getName()+"</td>");
				pw.print("<td>"+tmp.getAddr()+"</td>");
			pw.println("</tr>");
		}
		pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
		
	}
}

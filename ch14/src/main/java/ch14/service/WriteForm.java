package ch14.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch14.dao.Board;
import ch14.dao.BoardDao;

public class WriteForm implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		int num = 0, ref = 0, re_step = 0, re_level = 0;
		String pageNum = request.getParameter("pageNum");
		num = Integer.parseInt(request.getParameter("num"));
		if (num != 0) { // 0이면 처음 쓴글, 0이 아니면 답변글
			BoardDao bd = BoardDao.getInstance();
			Board board = bd.select(num);
			ref = board.getRef();   // 답변글 달 원글의 ref가 무었알기 위해
			re_step  = board.getRe_step();  // 원글의 re_step
			re_level = board.getRe_level(); //  "   re_level
		}
		request.setAttribute("num",num);
		request.setAttribute("pageNum",pageNum);
		request.setAttribute("ref",ref);
		request.setAttribute("re_step",re_step);
		request.setAttribute("re_level",re_level);
		return "writeForm";
	} 

}

package ch14.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch14.dao.Board;
import ch14.dao.BoardDao;

public class UpdateAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		Board board = new Board();
		int num = Integer.parseInt(request.getParameter("num"));
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		String password = request.getParameter("password");
		String pageNum = request.getParameter("pageNum");
		board.setNum(num);
		board.setContent(content);
		board.setSubject(subject);
		board.setPassword(password);
		board.setWriter(writer);
		BoardDao bd = BoardDao.getInstance();
		int result  = bd.update(board);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("result", result);
		return "update";
		
	}

}

package ch14.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch14.dao.Board;
import ch14.dao.BoardDao;

public class WriteAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		Board board = new Board();
		int num = Integer.parseInt(request.getParameter("num"));
		int ref = Integer.parseInt(request.getParameter("ref"));
		int re_step = Integer.parseInt(request.getParameter("re_step"));
		int re_level = Integer.parseInt(request.getParameter("re_level"));
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
		board.setRe_level(re_level);
		board.setRe_step(re_step);
		board.setRef(ref);
		
		BoardDao bd = BoardDao.getInstance();
		int result  = bd.insert(board);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("result", result);
		return "write";
	}

}

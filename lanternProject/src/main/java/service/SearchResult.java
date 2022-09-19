package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import dao.DisplayDao;
import model.Board;
import model.Display;

public class SearchResult implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		String srch = request.getParameter("srch");
		
		DisplayDao dd = DisplayDao.getInstance();
		List<Display> dpList = dd.search(srch);
		
		BoardDao bd = BoardDao.getInstance();
		List<Board> bdList = bd.search(srch);
		
		request.setAttribute("bdList", bdList);
		request.setAttribute("dpList", dpList);
	
		return "searchResult";
	}

}

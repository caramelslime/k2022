<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">@import url("common.css");</style></head><body>

<table><caption>게시글 목록</caption>
	<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일</th><th>조회수</th></tr>
<c:if test="${empty list }">
	<tr><th colspan="5">게시글이 없습니다</th></tr>
</c:if>
<c:if test="${not empty list }">
	<c:forEach var="board" items="${list }"><!-- EL에서 num--가 없다 -->
		<tr><td>${num }<c:set var="num" value="${num-1 }"></c:set><%-- ${board.num } --%></td>
		<c:if test="${board.del == 'y' }">
			<td colspan="4">삭제된 글입니다</td>
		</c:if>
		<c:if test="${board.del != 'y' }">
			<td title="${board.content }">
				<!-- 답변글 -->
				<c:if test="${board.re_level > 0 }">
					<img alt="" src="images/level.gif" height="5" width="${board.re_level * 10 }">
					<img alt="" src="images/re.gif">
				</c:if>
				<a href="content.do?num=${board.num }&pageNum=${currentPage}">${board.subject }</a>
				<c:if test="${board.readcount > 50 }">
					<img alt="" src="images/hot.gif">
				</c:if>
			</td>
			<td>${board.writer }</td>
			<td>${board.reg_date }</td>
			<td>${board.readcount }</td>
		</c:if>
		</tr>
	</c:forEach>
</c:if>
</table>
<div align="center">
	<!-- 앞에 보여줄 것이 있어 -->
	<c:if test="${startPage-PAGE_PER_BLOCK>0 }">
		<button onclick="location.href='list.do?pageNum=${startPage-1}'">이전</button>
	</c:if>
	<c:forEach var="i" begin="${startPage }" end="${endPage }">
<!-- 숫자를 클릭하면 그 숫자에 해당하는 page를 출력 -->
		<c:if test="${currentPage == i }">
			<button onclick="location.href='list.do?pageNum=${i}'"
				style="background: blue; color: white">${i }</button>
		</c:if>
		<c:if test="${currentPage != i }">
			<button onclick="location.href='list.do?pageNum=${i}'">${i }</button>
		</c:if>
	</c:forEach>
	<!-- 아직 보여줄 것이 남아있다 -->
	<c:if test="${endPage-totalPage<0 }">
		<button onclick="location.href='list.do?pageNum=${endPage+1}'">다음</button>
	</c:if>
</div>
<br><button onclick="location.href='writeForm.do?num=0&pageNum=1'">글 쓰기</button>
</body>
</html>
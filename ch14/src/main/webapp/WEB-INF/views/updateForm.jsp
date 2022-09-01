<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="common.css">
</head><body>
<form action="update.do" method="post" name="frm">
	<input type="hidden" name="num" value="${board.num}">
	<input type="hidden" name="pageNum" value="${pageNum }">
<%-- <input type="hidden" name="password2" value="${board.password}"> 암호가 소스 보기로 노출 --%>
<table><caption>게시글 수정</caption>
	<tr><th>제목</th><td><input type="text" name="subject" value="${board.subject }"
		required="required" autofocus="autofocus"></td></tr>
	<!-- 	작성자는 회원게시판에는 불필요 -->
	<tr><th>작성자</th><td><input type="text" name="writer" value="${board.writer }" 
		required="required"></td></tr>
	<tr><th>내용</th><td><pre><textarea rows="5" cols="40" required="required"
		name="content">${board.content }</textarea></pre></td></tr>
	<!-- 암호 회원게시판에는 불필요 비회원 게시판에는 암호를 알아야 수정 가능 -->
	<tr><th>암호</th><td><input type="password" name="password" required="required"></td></tr>
	<tr><th colspan="2"><input type="submit" value="확인"></th></tr>
</table>
</form>
</body>
</html>
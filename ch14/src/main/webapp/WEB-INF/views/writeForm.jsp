<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="common.css">
<script type="text/javascript">
	function chk() {
		if (frm.password.value != frm.confirmPass.value) {
			alert("암호와 암호확인이 다릅니다"); frm.password.focus();
			frm.password.value = ""; frm.confirmPass.value= ""; return false;
		}
	}
</script></head><body>
<form action="write.do" method="post" name="frm" onsubmit="return chk()">
	<input type="hidden" name="num" value="${num }">
	<input type="hidden" name="pageNum" value="${pageNum }">
	<!-- 처음 입력하면 ref/re_step/re_level 0인데 답변 게시글은 원본글의 ref/re_step/re_level을 값을 가지고 처리 -->
	<input type="hidden" name="ref" value="${ref }">
	<input type="hidden" name="re_step" value="${re_step }">
	<input type="hidden" name="re_level" value="${re_level}">
<table><caption>게시글 작성</caption>
<c:if test="${num==0 }">
	<tr><th>제목</th><td><input type="text" name="subject" required="required"
		autofocus="autofocus"></td></tr>
</c:if>
<c:if test="${num!=0 }">
	<tr><th>제목</th><td><input type="text" name="subject" required="required"
		autofocus="autofocus" value="답변) "></td></tr>
</c:if>
	<tr><th>작성자</th><td><input type="text" name="writer" required="required"></td></tr>
	<!-- 암호를 아는 사람만 글 작성, 수정하기 위한 것.  회원 게시판에서는 필요없음 -->
	<tr><th>암호</th><td><input type="password" name="password" required="required"></td></tr>
	<tr><th>암호확인</th><td><input type="password" name="confirmPass" required="required"></td></tr>
	<tr><th>내용</th><td><textarea rows="5" cols="40" name="content" 
		required="required"></textarea></td></tr>
	<tr><th colspan="2"><input type="submit" value="확인"></th></tr>
</table>
</form>
</body>
</html>
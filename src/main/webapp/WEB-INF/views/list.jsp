<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	LIST PAGE 유저정보<br>
	ID : ${sessionScope.login.id} <br>
	PW : ${sessionScope.login.pw} <br>
	NAME : ${sessionScope.login.name} <br>
	EMAIL : ${sessionScope.login.email} <br>
	PHONE : ${sessionScope.login.phone} <br>
	<br>
	<br>
	사진 업로드 :
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#errorPw{
		color:red;
		display:none;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
	//PW 체크
	function checkPw(){
		var pw = $("#pw").val();
		var pwConfirm = $("#pwConfirm").val();
		console.log("pw:"+pw);
		console.log("pwConfirm:"+pwConfirm);
				
		if(pw != pwConfirm)
			$("#errorPw").css("display","inline");
		else
			$("#errorPw").css("display","none");
	}
	
	//정규식 체크_임시
	function checkReg(){
		var id = $("#addId").val();
		var pw = $("#pw").val();
		var name = $("#name").val();
		
		var pname=/^[가-힣]{2,5}$/;
        var pid=/^[a-z]+[a-z0-9]{5,15}$/;
        var ppw =/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{7,20}$/;
        
        if(!pname.test(name)){
        	alert("이름은 2~5글자, 한글만 가능합니다.");
        	initInput();
        	return false;
        }
        if(!pid.test(id)){
        	alert("아이디는 6글자 이상, 영문/숫자만 가능합니다.");
        	initInput();
        	return false;
        }
        if(!ppw.test(pw)){
        	alert("비밀번호는 8~20글자, 영어/숫자/특수문자(~!@#$%^&*)만 가능합니다.");
        	initInput();
        	return false;
        }
        
        return true;
	}
	
	function initInput(){
		$("#addId").val("");
		$("#pw").val("");
		$("#name").val("");
	}
	
	$(function(){
		//ID 중복 체크
		$("#idChecker").on("click",function(){
			var targetId = $("#addId").val();
			$.ajax({
				url:"idCheck?id="+targetId,
				type:"get",
				success:function(result){
					if(result == "true"){
						alert("이미 존재하는 ID 입니다.");
						$("#addId").val("");
					}else{
						alert("사용가능한 ID 입니다.");
					}
				}
			});
		});
		
		//PW 동일 체크
		$("#pw").focusout(function(){
			checkPw();
		});
		$("#pwConfirm").focusout(function(){
			checkPw();
		});
		
		$("#userjoin").submit(function(){
			if(!checkReg())
				return false;
		});
		
	});
</script>
</head>
<body>
	<form action="join" method="post" id="userjoin">
		<label>아이디</label>
		<input type="text" name="id" id="addId"><input type="button" value="중복확인" id="idChecker">
		<br>
		<label>비밀번호</label>
		<input type="password" name="pw" id="pw">
		<br>
		<label>비밀번호 확인</label>
		<input type="password" name="pwConfirm" id="pwConfirm">
		<p id="errorPw">비밀번호가 일치하지 않습니다.</p>
		<br>
		<label>이름</label>
		<input type="text" name="name" id="name">
		<br>
		<label>이메일</label>
		<input type="email" name="email">
		<br>
		<label>전화번호</label>
		<input type="tel" name="phone">
		<br>
		<input type="submit" value="회원가입">
		<input type="button" value="취소">
	</form>
</body>
</html>
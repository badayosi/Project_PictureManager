<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.img{
		width:150px;
		position:relative;
		clear:both;
	}
	#del{
		position:absolute;
		top:5px;
		right:5px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
	function imgCheck(obj, len){
		var pathpoint
		var filepoint;
		var filetype;
		var checker = 0;
		
		if(len != 0){
			for(var i=0 ; i<len ; i++){
				pathpoint = obj.files[i].name.lastIndexOf(".");
				filepoint = obj.files[i].name.substring(pathpoint+1);
				filetype = filepoint.toLowerCase();
				if(filetype != "jpg" && filetype != "jpeg")
					checker++;
			}
			if(checker > 0)
				return false;
			else
				return true;	
		}
		else
			return false;
	}
	$(function(){
		$("#files").change(function(){
			var targetInput = $(this)[0];
			var fLength = $(this)[0].files.length;
			
			if(imgCheck(targetInput, fLength)){
				$("#imgPreview").empty();
				
				for(var i=0;i<$(this)[0].files.length;i++){
					var reader = new FileReader();
					reader.onload = function(e){
						var imgObj = $("<img>").attr("src", e.target.result);
						$("#imgPreview").append(imgObj);
					}
					reader.readAsDataURL(targetInput.files[i]);
				}
			}
			else
				alert("jpg 또는 jpe 파일만 업로드 가능합니다.");
		});
		$("#del").on("click",function(){
			if(confirm("정말 삭제하시겠습니까?")){
				var target = $(this);
				var path = $(this).attr("data-path");
				
				$.ajax({
					url:"deleteFile?filename="+path,
					type:"get",
					success:function(result){
						target.parent().remove();
					}
				});
			}else{
				return;
			}
		});
		
		$("#divZoom").attr("display","none");
		
		$(".img").on("click", function(){
			var path = $(this).find("input").attr("data-path");
			
			var path1 = path.substring(0,12);
			var path2 = path.substring(14);
			
			$("#imgZoom").attr("src", "displayFile?filename="+path1+path2);
			$("#divZoom").attr("display","block");
		});
	});
</script>
</head>
<body>
	LIST PAGE 유저정보 <a href="logout">로그아웃</a><br>
	ID : ${sessionScope.login.id} <br>
	PW : ${sessionScope.login.pw} <br>
	NAME : ${sessionScope.login.name} <br>
	EMAIL : ${sessionScope.login.email} <br>
	PHONE : ${sessionScope.login.phone} <br>
	<br>
	<br>
	<form action="upload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${sessionScope.login.id}">
		사진 업로드 : <input type="file" name="imageFiles" multiple="multiple" id="files" accept="image/*">
		<input type="submit" value="등록">
	</form>
	<div id="imgPreview">
	
	</div>
	<div id="divZoom">
		<img id="imgZoom">
	</div>
	<c:if test="${lists != null}">
		<div id="imgList">
			<c:forEach var="list" items="${lists}">
				<div class="img">
					<p>
						<script>
							var path = "${list.fullname}";
							var a = path.substring(51);				
							document.write(a);
						</script>							
					</p>
					<p><fmt:formatDate value="${list.regdate}" pattern="yyyy-MM-dd"/></p>
					<img src="displayFile?filename=${list.fullname}">
					<input type="button" id="del" value="X" data-path="${list.fullname}" data-user="${list.id}">
				</div>
			</c:forEach>
		</div>
	</c:if>
</body>
</html>
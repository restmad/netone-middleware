<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
	</head>

	<body style="font-size: 12px;margin: 22px">
		<c:if test="${result == 'y'}">
			<script type="text/javascript">
				alert("新建成功！")
				opener.search();
		</script>
		</c:if>
				<c:if test="${result == 'n'}">
			<script type="text/javascript">
				alert("${errorinfo}")
		</script>
		</c:if>
		<form action="" method="post" name="form1"
			enctype="multipart/form-data">
			<input type="hidden" name="pagename" value="${pagename}" />
			<input type="hidden" name="dirid" value="${dirid}">
			<input type="hidden" name="appid" value="${appid}">
			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="0" id="lie_table">
				<tr  style='display:none'>
					<td width="15%">
						有&nbsp;&nbsp;&nbsp;&nbsp;效
					</td>
					<td>
						<input type="checkbox" name="active" value="1" checked />
					</td>
				</tr>
				<tr>
					<td>
						图片上传
					</td>
					<td width="80%">
						<input type="file" onchange="autofile();" name="files">
					</td>
				</tr>
				<tr  style='display:none'>
					<td>
						名&nbsp;&nbsp;&nbsp;&nbsp;称
					</td>
					<td>
						<input type="text" name="naturalname" value=""
							class="textinput_td">
					</td>
				</tr>
				<tr>
					<td>
						中文名称
					</td>
					<td>
						<input type="text" name="filename" value="" class="textinput_td">
					</td>
				</tr>

				<tr style='display:none'>
					<td width="15%">
						分类
					</td>
					<td width='300'>
						<input type="hidden" name="objecttype" value="二进制流[BINARY]" />
					</td>
				</tr>

				<tr style='display:none'>
					<td width="15%">
						扩展属性
					</td>
					<td>
						<textarea rows="8" cols="60" name="extendattribute"></textarea>
					</td>
				</tr>
				<tr>
					<td width="15%">
						描述
					</td>
					<td>
						<textarea rows="2" cols="60" name="description"></textarea>
					</td>
				</tr>
			</table>
			<br>
			<div align="center">
				<input type="button" value="添加" onclick="uploadfile();" class="butt">
				&nbsp;
				<input type="button" value="取消" onclick="javascript:window.close();"
					class="butt">
			</div>
		</form>
	</body>
</html>

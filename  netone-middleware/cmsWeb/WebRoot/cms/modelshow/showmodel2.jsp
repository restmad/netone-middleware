
<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String sessionid = request.getRequestedSessionId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<SCRIPT type="text/javascript">
    	var contextpath = "<%=path%>";
    	var sessionid = "<%=sessionid%>";
    	var modelid = "${modelid}";
    </SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\util.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\modelshow\showmodel2.js"></SCRIPT>

		<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet" type="text/css">
		
	</head>

	<body>
		<div id="templatediv" name="templatediv" class="floatdiv" style="display:none ; width: 100%; height: 50px; overflow: visible; border: 0; margin: 1px; word-wrap: break-word; ">
			loading...
		</div>

		<div style="width:100% ; height: 100%; overflow: visible;">
			<c:forEach items="${listmap}" var="entry" varStatus="vs">
				<div id="${entry.key}" class="boxdiv" style="width:${widthlist[vs.index]};height:100%;float:left;overflow-x: auto;overflow-y: visible">
					<c:forEach items="${entry.value}" var="fdiv">
						<script type="text/javascript">
	  						addDiv("${fdiv.infoCellid}","${idnamemap[fdiv.infoCellid]}","${entry.key}");
	  					</script> 
					</c:forEach>
				</div>
			</c:forEach>
		</div>
	</body>
</html>


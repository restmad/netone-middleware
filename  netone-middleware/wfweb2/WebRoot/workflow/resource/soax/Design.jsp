<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML>
	<HEAD>
		<TITLE>步骤一</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<script type="text/javascript" src="include/js/workflow/soa4.js"></script>
		<script type="text/javascript" src="include/js/workflow/prototype.js"></script>
		<SCRIPT TYPE="text/javascript">
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		//下一步脚本
		function done(){
			//最终结束的时候相关的处理逻辑需要提交给Servlet来完成
			this.document.forms[0].action="Show4.do?task=final";
			//将相关的表单参数全部传递给上面的Servlet
			this.document.forms[0].submit();
		}
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid,extendattribute){
			document.getElementById('text0').value=name+'['+extendattribute+']';
			var paramer = "extend="+extendattribute+"&tmpcheck="+document.all.tmpcheck.value;
			var url = "<%=path%>/Show4.do";
			var parser = new Ajax.Request(url, {method:"get", parameters:"" + paramer, asynchronous:false});
			var restr = parser.transport.responseText;
			document.all.tmpselect.innerHTML = restr;
		}
		function sselected(text,value){
		var select=document.getElementById('tmpcheck').value;
		if(select=='bs'){
			document.getElementById('text0').value=value;
			return;
		}
			document.getElementById('text0').value=text+'['+value+']';
			var paramer = "extend="+value+"&tmpcheck="+document.all.tmpcheck.value;
			var url = "<%=path%>/Show4.do";
			var parser = new Ajax.Request(url, {method:"get", parameters:"" + paramer, asynchronous:false});
			var restr = parser.transport.responseText;
			document.all.tmpselect.innerHTML = restr;
		}
		function todo(){
			var select=document.getElementById('tmpcheck').value;
			if(select=='dy'){
				window.open('SSelectSvl?appname=BUSSFORM&pagename=applist');
			}
			if(select=='wf'){
				window.open('SSelectSvl?appname=BUSSWF&pagename=applist');
			}
			if(select=='be'){
				window.open('SSelectSvl?appname=BUSSBEAN&pagename=applist');
			}
			if(select=='bs'){
				window.open('/cmsWeb/SSelectSvl?pagename=template&appname=TEMPLATE');

			}
		}
		
		function addbs(){
			var bsname=document.getElementById("text0").value;
			if(bsname==null||bsname==''){
				alert('未选择业务元');
				return;
			}

			var url = "/cmsWeb/JppFuncSvl?name="+bsname;
			var parser = new Ajax.Request(url, {method:"get", parameters:"", asynchronous:false});
			var restr = parser.transport.responseText;
			document.all.cdata.value += "\n\r"+restr;
		}
		
		function selectu(){
			document.all.text0.value='';
			if(this.value=='bs'){
				document.getElementById('insertjs').style.display='none';
			}else{
			    document.getElementById('insertjs').style.display='';
			}
		}
		
		function createvalue(){
			var inout = document.all.inout.value;
			if(inout=="in"){
				var paramer = "task=inout&chkid="+document.all.chkid.value+"&inout="+document.all.inout.value;
				var url = "<%=path%>/Show4.do";
				var parser = new Ajax.Request(url, {method:"get", parameters:"" + paramer, asynchronous:false});
				var restr = parser.transport.responseText;
				document.all.cdata.value += restr;
			} else if(inout=="out"){
				var paramer = "task=inout&chkid="+document.all.chkid.value+"&inout="+document.all.inout.value;
				var url = "<%=path%>/Show4.do";
				var parser = new Ajax.Request(url, {method:"get", parameters:"" + paramer, asynchronous:false});
				var restr = parser.transport.responseText;
				document.all.cdata.value += restr;
			}
		}
		</SCRIPT>
		<script type="text/javascript"
			src="<%=basePath%>/include/js/calendar.js"></script>


	</HEAD>
	<c:if test="${result=='y'}">
		<script type="text/javascript">
			alert("流程结束!");
			window.close();
		</script>
	</c:if>
	<BODY bgcolor="#FFFFFF">
		<form action="" method="post" name="form1">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>

			<input type="hidden" name="chkid" value="${sf.chkid}">
			
			<input type="hidden" name="actionurl" value="${sf.actionurl}">
			<input type="hidden" name="title" value="${sf.title}">
			<input type="hidden" name="recevier" value="${sf.recevier}">
			<input type="hidden" name="extattr3" value="${sf.extattr3}">
			<input type="hidden" name="content" value="${sf.content}">
			<input type="hidden" name="show" value="${sf.show}">
			<input type="hidden" name="show2" value="${sf.show2}">
			
			<%@ include file="/include/page/PageHeadInfo.jsp"%>

			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD>
							<TABLE WIDTH="100%" BORDER="0" CELLPADDING="1" CELLSPACING="0">
								<TR CLASS="OecBgColorDark">
									<TD>
										&nbsp;
									</TD>
									<TD WIDTH="100%" valign="center">
										<FONT CLASS="OecGlobalPageTitle">SOA过程</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG SRC="<%=basePath%>/image/helpsb.gif"
												ALT="帮助" BORDER="0"> </A>
									</TD>
									<TD>
										&nbsp;
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%">
								<TR>
									<TD ROWSPAN="2" VALIGN="TOP">
										<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
											<TR>
												<TD VALIGN="TOP" CLASS="OecBgColorDark">

												</TD>
											</TR>
										</TABLE>
									</TD>
									<TD ROWSPAN="2" VALIGN="TOP">
										&nbsp;&nbsp;&nbsp;
									</TD>
									<TD ALIGN="RIGHT" valign="top" width="100%">
										<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
											<TR>
												<TD>
													<INPUT type='button' value='完成>'
														onClick='javascript:done()'>
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="取消"
														onClick="javascript:cancelsubmit()">
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD ALIGN="LEFT">
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;${param.pagepath}
										</FONT> &nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">第 1 步, 共 1 步&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">
				<!-- Start--Title----功能标题与提示---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						业务功能脚本定义（OeScript语法）
						
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 这里主要完成业务附加脚本的编写。</FONT>
				<!-- End--Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<DIV
					style="margin-left: 5%; margin-right: 5%; margin-top: 20px; margin-bottom: 20px;">
					<table>

						<tr>
							<td>
							    流程相关数据: 
								<select name="inout">
									<option value="in">输入</option>
									<option value="out">输出</option>
								</select>
								<input type='button' value='生成' onClick='createvalue();' class="butt">
								
							   &nbsp;&nbsp;&nbsp;								是否同步:
								<input type="checkbox" name="syn" value="${sf.syn}"
									onclick="checksyn();" ${sf.syn=='true'?"checked":""}>
								sync
							</td>
						</tr>
						<tr>
							<td>
								<textarea rows="16" cols="120" name="cdata">${sf.cdata}</textarea>
							</td>
						</tr>
						<tr>
							<td nowrap>
								参考参数：<select name="tmpcheck" onchange="selectu();">
									<option value="dy">
										动态表单
									</option>
									<option value="wf">
										工作流
									</option>
									<option value="be">
										业务Bean
									</option>

								</select>
								<input type="text" name="text0" value="">
								<input type='button' value='选择' onClick='todo()' class="butt">					
								<div id="tmpselect">
								</div>
							</td>
						</tr>
					</table>
				</DIV>
				<!-- End --Body---- 功能内容定义  --->
			</DIV>
			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD ALIGN="RIGHT" valign="top" width="100%">
							<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
								<TR>
									<TD>
										<INPUT type='button' value='完成' onClick='javascript:done()'>
									</TD>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="取消"
											onClick="javascript:cancelsubmit()">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>

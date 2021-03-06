<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//存放自定义的业务临时数据
	String entryvar = (String) request.getAttribute("entryvar");
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>应用开发框架资源管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript"
			src="rsinclude/pagelistpath/pagelistpath.js"></script>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>

		<script type="text/javascript">
		//单选择 需要根据应用的需要来订制
		function checkthis(id) {
			if(opener.selected==undefined){
				alert('非选择状态,父页面缺少回调函数');
				return;
			}
		}
		//多选择 需要根据应用的需要来订制
		function checkthese(){
			var values = null;
			var checkids = document.getElementsByTagName("input");
			for(var i=0 ; i<checkids.length ; i++){
				if(checkids[i].type=="checkbox"){
					if(checkids[i].checked && checkids[i].name!='checkall'){
						if(values == null){
							values = checkids[i].value;
						} else {
							values = values + "," + checkids[i].value;
						}
					}
				}
			}
			alert(values)
		}
		
		//查询逻辑可以自定义扩展条件,SQL的子条件,日期属性created 有效属性active 1/0 扩展属性extendattribute
		function search(){
			form1.action="PagelistpathRightSvl";
			form1.submit();
		}
		
		function cfg_p(id){
			window.open("CreateSubSvl?&pagename=${pagename}&chkid="+id);
		}
		function cfg_dy(id){
			window.open("CreateDySubSvl?&pagename=${pagename}&chkid="+id);
		}
		
		function man(name,value){
		    
		    var url='<portal:envget envkey="WEBSER_APPFRAME"/>frame.do?method=onMainView&naturalname='+name;
		    if(value=='-1')return;
		    if(value=='0'){
		    }else if(value=='1'){
			   url='<portal:envget envkey="WEBSER_APPFRAMEX"/>frame.do?method=onMainView&naturalname='+name;
			}else {
			   url='<portal:envget envkey="WEBSER_APPFRAMEX"/>frame.do?method=onMainView'+value+'&naturalname='+name+'&extmode=2';
			}
			
			window.open(url);
		}
		
		function manx(name,value){
			if(value=='0'){
				worklist(name);
				return;
			}
			if(value=='1'){
				worklistR(name);
				return;
			}
			if(value=='2'){
				worklistdone1(name);
				return;
			}
			if(value=='3'){
				worklistdone2(name);
				return;
			}				
			if(value=='4'){
				worklistdone3(name);return;
			}
		}
			
		
		function editx(id){
			var url='<portal:envget envkey="WEBSER_SpeedyForm"/>WConfigEngine.html?mode=edit&idcreated='+id;
			window.open(url);
		}
		function newx(name){
			var url='<portal:envget envkey="WEBSER_SpeedyForm"/>WConfigEngine.html?mode=new&parentdir='+name;
			window.open(url);
		}		

		
		function worklist(name){    
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/workList.do?method=onMainView2&mode=1&height=460&listtype=01&sortfield=&sort=&psize=20&appname='+name;
			window.open(url);
		}
		
		function worklistdone3(name){
				var urlx='<portal:envget envkey="WEBSER_APPFRAME"/>/workList.do?height=260&appname='+name+'&sort=&psize=20&sortfield=&listtype=00&mode=1&method=onMainView2';
			    window.open(urlx);	
		}
		
		function worklistR(name){    
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/workList.do?method=onMainView2&mode=0&height=460&listtype=01&sortfield=&sort=&psize=20&appname='+name;
			window.open(url);
		}
		function worklistdone1(name){
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/workList.do?method=onMainView2&mode=1&height=460&listtype=02&sortfield=&sort=&psize=20&appname='+name;
			window.open(url);
		}
		function worklistdone2(name){
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/workList.do?method=onMainView2&mode=1&height=460&listtype=03&sortfield=&sort=&psize=20&appname='+name;
			window.open(url);
		}
		function init(id){
			var url='InitCfgSvl?id='+id;
			window.open(url);
		}	
		function init2(id){
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/InitDataSvl?name='+id;
			window.open(url);
		}		
		</script>
	</head>
	
	<body style="font-size: 12px; margin: 22px">
		<div style="width: 100%; height: 100%">
			<form action="" method="post" name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="parentdir" value="${upo.parentdir}" />
				<input type="hidden" name="appid" value="${upo.appid}" />
				<input type="hidden" name="ou" value="${upo.ou}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<input type="hidden" name="inclusion" value="${upo.inclusion}" />
				<input type="hidden" name="extendattribute"
					value="${upo.extendattribute}" />
				<input type="hidden" name="ext" value="${ext}" />
				<input type="hidden" name="aggregation" value="${upo.aggregation}" />
				<input type="hidden" name="created" value="${upo.created}" />
				<input type="hidden" name="entryvar" value="<%=entryvar%>" />
				<table width="96%" border="0" align="center" cellpadding="5"
					cellspacing="0">
					<tr>
						<td nowrap>
							名称:
						</td>
						<td width="100">
							<input type="text" name="snaturalname" value="${snaturalname}"
								class="textinput_td">
						</td>
						<td nowrap>
							中文名称:
						</td>
						<td width="100">
							<input type="text" name="sname" value="${sname}"
								class="textinput_td">
						</td>
						<td nowrap>
							开始时间:
						</td>
						<td width="100">
							<input type="text" name="begintime" value="${begintime}"
								class="textinput_td" onfocus="calendar()">
						</td>
						<td nowrap>
							结束时间:
						</td>
						<td width="100">
							<input type="text" name="endtime" value="${endtime}"
								class="textinput_td" onfocus="calendar()">
						</td>
						<td>
							<input type="button" name="searchbtn" value="查 询"
								onclick="search();" class="butt">
						</td>
					</tr>
					<tr>
						<td nowrap>
							路径:
						</td>
						<td colspan='6'>
							${link}
							<!-- 真正的路径naturalname -->
							<!-- ${pathreal} -->
						</td>
						<td colspan='1'>
							<a href='javascript:newInclusion();'><font color='blue'>新建目录</font>
							</a> &nbsp;&nbsp;

						</td>
					</tr>
					<tr>
						<td colspan='9' align='right' nowrap>
							&nbsp;&nbsp;
							<input type="button" value="新建应用" onclick="newx('${upo.naturalname}');"
								class="butt">
							<input type="button" value="新建应用Old" onclick="newElemnt();"
								class="butt">

							&nbsp;&nbsp;
							<input type="button" name="btndelete" value="删 除"
								onclick="del();" class="butt">
							&nbsp;&nbsp;
							<input type="button" name="btncheck" value="全 选"
								onclick="checkthese();" class="butt">
						</td>
					</tr>
				</table>
				<br>
				<table id="hang_table" width="96%" border="0" align="center"
					cellpadding="0" cellspacing="1">
					<tr>
						<td width="70" class="td_titt_bg" nowrap>
							<input type="checkbox" name="checkall" onclick="allcheck();">
							选择
						</td>
						<td class="td_titt_bg" nowrap>
							名称中文名称
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							名称
						</td>

						<td class="td_titt_bg" width="70" nowrap>
							日期
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							有效
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							操作
						</td>
					</tr>
					<c:forEach items="${list}" var="list">
						<tr>
							<td>
								<input type="checkbox" name="chkid" value="${list.id}">
							</td>

							<td align='left' nowrap>
								<c:if test="${list.inclusion == '1'}">
									<a href="javascript:inclusionlink('${list.id}','${list.ou}');"><img
											BORDER='0' src='<%=basePath%>rsinclude/images/folder.png'>
									</a>
									<a href="javascript:view('${list.id}');">${list.name}</a>
								</c:if>
								<c:if test="${list.inclusion != '1'}">
									<a href="javascript:view('${list.id}');">${list.name}</a>
								</c:if>
							</td>
							<td nowrap>
								${list.naturalname}
							</td>

							<td nowrap>
								${list.created}
							</td>
							<td nowrap>
								<c:if test="${list.active=='1'}">
									YES
								</c:if>
								<c:if test="${list.active=='0'}">
									NO
								</c:if>
							</td>

							<td nowrap>
								<c:if test="${list.inclusion!='1'}">
									 <a href="javascript:editx('${list.id}');"><font color='red'>[框架配置]</font></a>
									 <a
										href="javascript:edit('${list.id}');"><font color='#999999'>[框架]</font></a> <a
										href="javascript:cfg_p('${list.id}');"><font color='#999999'>[参与者]</font></a> <a
										href="javascript:cfg_dy('${list.id}');"><font color='#999999'>[表单]</font></a> 
									
									
										<select id='appmode' name='appmode'  onchange="javascript:man('${list.naturalname}',this.value);">
									        <option value="-1">-应用入口-</option>
											<option value="0">过程</option>
											<option value="1">通用</option>
											<option value="2">带子表单</option>
											<option value="3">带汇总</option>
											<option value="4">带子表单+汇总</option>
										</select>

										<select id='flowmode' name='flowmode'  onchange="javascript:manx('${list.naturalname}',this.value);">
									        <option value="-1">-流程相关-</option>
											<option value="0" >待办应用</option>
											<option value="1">待阅应用</option>
											<option value="2">已办未归档</option>
											<option value="3">已办且归档</option>
											<option value="4">管理员视图</option>
										</select>
																			
									<a href="javascript:worklist('${list.naturalname}');"><font color='green'></font></a>
									<a href="javascript:worklistR('${list.naturalname}');"><font color='green'></font></a>
									<a href="javascript:worklistdone1('${list.naturalname}');"><font color='green'></font></a>
									<a href="javascript:worklistdone2('${list.naturalname}');"><font color='green'></font></a>
									<a href="" target='_blank'><font color='green'></font></a>
									
									<a href="javascript:init('${list.id}');">初始化框架</a>
									<a href="javascript:init2('${list.naturalname}');">初始化数据</a>
									<a href="javascript:del('${list.id}');">删除</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<HR>
			</form>
		</div>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("修改失败！");
				form1.action="PagelistpathRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'n'}">
			<script type="text/javascript">
				alert("该节点下存在子节点,不允许删除！");
				form1.action="PagelistpathRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DelSuccess == 'y'}">
			<script type="text/javascript">
				alert("删除成功！");
			</script>
		</c:if>
	</body>
</html>

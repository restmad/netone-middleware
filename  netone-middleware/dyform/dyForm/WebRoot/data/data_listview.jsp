<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<html>
	<head>
		<h:meta title="OESEE ��̬����" />
		<h:css src="/include/css/oe.css" />
		<h:javascript src="/include/js/data/view.js" />
		<style type="text/css">

</style>
	</head>
	<body>
		<h:messageDialog />
		<h:dyform action="/data/showdata" />
	</body>
</html>
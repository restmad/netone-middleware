[域管理]
/ApplistRightSvl?pagename=applist
［资源管理］
/PagelistpathRightSvl?appname=&pagename=
［聚合资源］
/PagelistRightSvl?appname=&pagename=
［目录树］
/SelectSvl?appname=&pagename=fcklist
［多选资源］
/MSelectSvl?appname=&pagename=
［单选资源］
/SSelectSvl?appname=&pagename=

其中pagename=fcklist
appname为服务器资源的响应的域，可以使用appname=FCK

如下：
http://10.192.15.80:8080/fck/PagelistpathRightSvl?appname=FCK&pagename=fcklist

该工程下可以使用3个pagename，
1、fcklist	------公共调用的时候，具有普通性，包含名字，中文名字和FCK编辑器中的内容。
2、simplefcklist   -----只有FCK编辑器的内容，这时候该工程主要承担编辑的内容，创建资源时，需要为资源设定好名字和中文名字，如pms调用在工程时，在创建资源时，已经指定了名字和中文名字。
3、pagelist ---------   一般的显示方式


浏览方式有两种
浏览1：包含名称，中文名称，有效和内容浏览
浏览2：（简洁）内容浏览


关于中文乱码的几点说明：
1、fck上传的文件被放到UserFiles文件夹下
2、所有上传的文件类型分为4类，
	A、文件（以链接形式展现）
	B、图片
	C、flash
	D、media
3、注意，flash不能以中文命名，否则不能正常显示。其余文件类型支持中文
	
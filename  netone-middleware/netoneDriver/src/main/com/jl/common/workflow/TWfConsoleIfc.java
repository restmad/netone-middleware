package com.jl.common.workflow;

import java.util.List;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * DRP工作流应用接口 <br>
 * 该接口作为一个适配器存在，为了支持DRP应用外部独立的工作流引擎服务<br>
 * 
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public interface TWfConsoleIfc {

	String OPE_TIP_SUCCESS = "SUCCESS:";
	String OPE_TIP_ERROR = "ERROR:";
	
	
	String _ACT_EXT_DELAY="delay";// 流程节点延迟激活的配置
	String _ACT_EXT_DELAY_TRUE="true";//流程节点延迟激活的配置 为真
	
	// 业务流程参考相关变量命名
	String _DEFAULT_REV_KEY_CUSTOMER = "customer"; // 参与者
	String _DEFAULT_REV_KEY_BUSSID = "bussid"; // 业务ID
	String _DEFAULT_REV_KEY_BUSSURL = "bussurl"; // 业务地址
	String _DEFAULT_REV_KEY_BUSSTYPE = "busstype"; // 业务模式
	String _DEFAULT_REV_KEY_BUSSTIP = "busstip"; // 业务提示
	String _DEFAULT_REV_KEY_STATUS = "status";// 流程环节，该变量是工作流内部控制表示当前的业务环节值
	String _DEFAULT_REV_KEY_WORKCODE = "workcode";
	String _DEFAULT_REV_KEY_BUSSSTATUS = "bussstatus";// 业务状态，该字段表示流程的环节计数
	
	/**
	 * 业务锁定，在流程应用中，如果表单已经提交审批那么系统需要
	 * 在管理窗口中锁定表单不允许修改作废等操作
	 * @param lsh 业务表单某条记录的lsh
	 * @return
	 */
	boolean bussFormLock(String lsh)throws Exception;
	
	/**
	 * 业务锁定，在流程应用中，如果表单已经提交审批那么系统需要
	 * 在管理窗口中锁定表单不允许修改作废等操作
	 * @param workcode 工作流对象的workcode
	 * @return
	 */
	boolean bussFormLockByWorkcode(String workcode)throws Exception;
	
	/**
	 * 获得流程首节点
	 * @param processid 流程资源名
	 * @return
	 */
	String fetchFirstActivityId(String processid);
	

	/**
	 * 超期告警
	 * 
	 * @return 告警记录数据
	 */
	int outTimeAlarm();

	/**
	 * 获得所有运行中的活动节点
	 * 
	 * @param runtimeid 流程实例id
	 * @return
	 */
	List<TWfWorklist> listAllRunningWorklistByRuntimeid(String runtimeid)
			throws Exception;

	/**
	 * 获得所有运行中的活动节点
	 * 
	 * @param runtimeid 流程实例id
	 * @return
	 */
	List<TWfWorklist> listAllDoneWorklistByRuntimeid(String runtimeid)
			throws Exception;

	/**
	 * 获得所有运行中的活动 workcode
	 * 
	 * @param runtimeid 流程实例id
	 * @return workcode数组，是流程中所有的活动实例的ID数组
	 */
	String[] getRunningWorkCodeByRuntimeid(String runtimeid) throws Exception;

	/**
	 * 根据流程runtimeid获得其流程ID
	 * 
	 * @param runtimeid  流程实例id
	 * @return  流程资源名
	 * @throws Exception
	 */
	String getProcessIdByRuntimeId(String runtimeid) throws Exception;

	/**
	 * 根据当前用户返回代办任务
	 * 
	 * @param userid 用户id
	 * @return
	 * 
	 */
	List<TWfWorklistExt> worklist(String userid) throws Exception;

	/**
	 * 根据当前用户返回已经办理过的但是未归档过的流程任务
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	List<TWfWorklistExt> worklistDone(String userid) throws Exception;

	/**
	 * 根据当前用户返回已经办理过且归档过的流程任务
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	List<TWfWorklistExt> worklistDoneAndProcessDone(String userid)
			throws Exception;

	/**
	 * 根据当前用户返回活动列表
	 * 
	 * @param userid
	 *            客户ID
	 * @param processid
	 *            流程ID
	 * @param mode
	 *            业务模式 待办、抄送、抄阅
	 * @param limit
	 *            显示个数限制 limit
	 * @param needRunning
	 *            是否运行中
	 * @param listtype
	 *            待办任务类型 01 是运行中、02是结束但流程未结束、03是结束且流程也结束
	 * 
	 * @return
	 * @throws Exception
	 */
	List<TWfWorklistExt> worklist(String userid, String processid,
			boolean mode, int limit, String listtype) throws Exception;

	/**
	 * 创建流程实例
	 * 
	 * @param processid
	 *            流程id
	 * @param userid
	 *            启动流程者
	 * @param busstype
	 *            业务参数，业务类型 通常对应着应用框架的natualname
	 * @param busstype
	 *            业务参数，提示
	 * @param bussid
	 *            业务参数，表单id
	 * @param bussurl
	 *            业务参数，表单访问地址
	 * @return
	 * @throws Exception
	 * 
	 * 补充说明：流程创建实例时，必须将业务参数传入这样在代办任务中，才能衔接上业务
	 */
	String newProcess(String processid, String userid, String busstype,
			String busstip, String bussid, String bussurl) throws Exception;

	/**
	 * 启动流程
	 * 
	 * @param runtimeid
	 *            流程实例id
	 */
	void runProcess(String runtimeid) throws Exception;

	/**
	 * 停止流程
	 * @param runtimeid
	 *            流程实例id
	 */
	String stopProcess(String runtimeid) throws Exception;

	/**
	 * delete流程
	 * @param runtimeid
	 *            流程实例id
	 */
	String deleteProcess(String runtimeid) throws Exception;
	
	/**
	 * 流程扭转,自动扭转由工作流的路由逻辑控制
	 * 
	 * @param workcode 活动实例id
	 * @param userid 用户id
	 * 
	 * @return
	 * @throws Exception
	 */
	String nextByAuto(String workcode, String userid) throws Exception;

	/**
	 * 流程扭转，直接结束流程
	 * 
	 * @param workcode
	 * @param userid
	 * 
	 * @return
	 * @throws Exception
	 */
	String nextToEnd(String workcode, String userid) throws Exception;

	/**
	 * 转办
	 * 
	 * @param workcode 活动实例id
	 * @param userid 用户id
	 * 
	 * @return
	 * @throws Exception
	 */
	String nextToZhuanbang(String workcode, String userid) throws Exception;

	/**
	 * 流程扭转，由人工指派下一个环节的活动节点
	 * 
	 * @param workcode  活动实例id
	 * @param actid 活动的资源名
	 * @param userid 用户id
	 * @return
	 * @throws Exception
	 */
	String nextByManual(String workcode, String actid, String userid)
			throws Exception;

	/**
	 * 业务通知
	 * 
	 * @param fromuser
	 *            从某个用户
	 * @param touser
	 *            给某个用户
	 * @param message
	 *            通知内容
	 * @param workcode
	 *            流程环节 workcode（可以为空，表示独立的效能督办）
	 * @param dyform_lsh
	 *            动态表单数据lsh dyform_lsh（可以为空，表示独立的效能督办）
	 * @param appname
	 *            应用资源名 appname（可以为空，表示独立的效能督办）
	 */
	void notice(String fromuser, String touser, String message,
			String workcode, String dyform_lsh, String appname);

	/**
	 * 根据活动workcode获得流程runtimeid
	 * 
	 * @param workcode
	 * @return
	 */
	String getRuntimeIdByWorkcode(String workcode) throws Exception;

	/**
	 * 装载出流程节点
	 * 
	 * @param processid
	 *            流程id
	 * @param activeid
	 *            节点ID
	 * @param appid
	 *            应用ID
	 * @param commiter
	 *            提交者
	 * @param runtimeid
	 *            流程运行ID
	 * @return
	 * @throws Exception
	 * 
	 * 注：应该方法应用在需要做流程调整应用中使用到，比如：流程回退就属于跳转的一个应用
	 * 
	 */
	TWfActive loadRuntimeActive(String processid, String activeid,
			String appid, String commiter, String runtimeid) throws Exception;

	/**
	 * 获得流程中的相关变量值
	 * 
	 * @param runtimeid
	 *            流程运行实例ID
	 * @param revid
	 *            相关变量ID
	 * @return 相关变量值
	 * @throws Exception
	 */
	String getVarByRuntimeId(String runtimeid, String revid) throws Exception;

	/**
	 * 获得应用名，该方法是扩展 getVarByRuntimeid，主要是读取 busstype属性，但是 做了缓存处理
	 * 
	 * @param runtimeid
	 * @return
	 * @throws Exception
	 */
	String getAppNameByRuntimeId(String runtimeid) throws Exception;

	/**
	 * 添加相关变量的值
	 * 
	 * @param runtimeid
	 *            流程运行实例ID
	 * @param revid
	 *            相关变量ID
	 * @param value
	 *            相关变量值
	 * @throws Exception
	 */
	void setVarValue(String runtimeId, String revId, String value)
			throws Exception;

	/**
	 * 判断流程是否在运行中
	 * 
	 * @param runtimeId 流程运行实例ID
	 * @return
	 */
	boolean isWorkflowRunning(String runtimeId) throws Exception;

	/**
	 * 判断流程是否已经执行结束
	 * 
	 * @param runtimeId 流程运行实例ID
	 * @return
	 */
	boolean isWorkflowDone(String runtimeId) throws Exception;

	/**
	 * 判断流程是否出现异常
	 * 
	 * @param runtimeId 流程运行实例ID
	 * @return 
	 */
	boolean isWorkflowException(String runtimeId) throws Exception;

	/**
	 * 获得子流程运行ID
	 * 
	 * @param parentRuntimeid 
	 *            父流程ID
	 * @return
	 * @throws Exception
	 */
	String[] subFlowRuntimeId(String parentRuntimeid) throws Exception;

	/**
	 * 根据业务ID获得流程ID
	 */
	String getSession(String key);
	/**
	 * 根据业务ID 和 应用ID 获得流程ID，有的时候一个表单可能在多个流程中扭转
	 */
	String getSession(String key,String appname);
	/**
	 * 重新激活执行过的某节点
	 * 
	 * @param workcode 活动实例id
	 * @return
	 * @throws Exception
	 */
	String reActive(String workcode) throws Exception;

	/**
	 * 指定下一个环节的执行者
	 * @param commiter
	 *            活动提交者 
	 * @param workcode
	 *            活动实例 id
	 * @param participant
	 *            参与者
	 * @param sync
	 *            是否需要同步
	 * @param opemode
	 *            操作模式 01 正常下一步，02退办/特送 03 归档 ,04 催办
	 * @throws Exception
	 */
	void specifyParticipantByWorkcode(String commiter, String workcode,
			String participant, boolean sync, String opemode) throws Exception;

	/**
	 * 指定本环节的转办信息
	 * @param commiter
	 *            活动提交者 
	 * @param workcode
	 *            活动id
	 * @param participant
	 *            参与者
	 * 
	 * @throws Exception
	 */
	void specifyzhuanbangByWorkcode(String commiter, String workcode,
			String participant) throws Exception;

	/**
	 * 指定下一个环节的催办信息
	 * @param commiter
	 *            活动提交者 
	 * @param workcode
	 *            活动id
	 * @param participant
	 *            参与者
	 * 
	 * @throws Exception
	 */
	void specifyCuibangByWorkcode(String commiter, String workcode,
			String participant) throws Exception;

	/**
	 * 指定下一个环节的抄送者
	 * 
	 * @param commiter
	 *            提交者
	 * 
	 * @param workcode
	 *            活动id
	 * @param participant
	 *            抄送者实际上为协办者，它也可以参与提交处理任务
	 * @param sync
	 *            是否需要同步
	 * @param opemode
	 *            操作模式 01 正常下一步，02退办/特送 03 归档 ,04 催办
	 * @throws Exception
	 */
	void specifyAssistantByWorkcode(String commiter, String workcode,
			String participant, boolean sync, String opemode) throws Exception;

	/**
	 * 指定下一个环节的抄阅者
	 * @param commiter
	 *            活动提交者 
	 * @param workcode
	 *            活动id
	 * @param participant
	 *            抄送者实际上为协办者，它也可以参与提交处理任务
	 * @param opemode
	 *            操作模式 01 正常下一步，02退办/特送 03 归档 ,04 催办
	 * @throws Exception
	 */
	void specifyReaderByWorkcode(String commiter, String workcode,
			String participant, String opemode) throws Exception;
	
	/**
	 * 分布式提交业务
	 * @param commiter 提交者
	 * @param workcode 活动实例id
	 * @param participant 参与者
	 * @param opemode 操作模式 
	 * @throws Exception
	 */
	void distributedSubmit(String commiter, String workcode,
			String participant, String opemode) throws Exception;

	/**
	 * 自动路由下一个环节的执行者
	 * 
	 * @param commiter 提交者
	 * @param workcode 活动实例id
	 * 
	 * @throws Exception
	 */
	void specifyParticipantAutoByWorkcode(String commiter, String workcode)
			throws Exception;

	/**
	 * 获得流程中所有完成的节点
	 * 
	 * @param runtimeid 流程实例id
	 * @return
	 * @throws Exception
	 */
	List<TWfActivePass> listNextBackActive(String runtimeid) throws Exception;

	/**
	 * 获得当前转办的节点
	 * 
	 * @param workcode
	 *            活动code
	 * @param commiter
	 *            上一个环节的提交者，注意这个需要在待办任务中 带过来
	 * @return
	 * @throws Exception
	 */
	TWfActive listNextZhuanbangActive(String workcode, String commiter)
			throws Exception;

	/**
	 * 获得当前的节点
	 * 
	 * @param appname
	 *            应用框架名
	 * @param workcode
	 *            活动code
	 * @param commiter
	 *            上一个环节的提交者，注意这个需要在待办任务中 带过来
	 * @return
	 * @throws Exception
	 */
	TWfActive listCurrentActive(String appname, String workcode, String commiter)
			throws Exception;

	/**
	 * 获得流程设计中默认的下一步节点 注意：流程图中设计的下一步节点不涉及路由选择，系统会忽略路径上 的路由选择条件。
	 * 
	 * @param processid
	 *            流程ID
	 * @param activeid
	 *            活动ID
	 * @param runtimeid
	 *            流程实例id
	 * @param commiter
	 *            流程提交者
	 * @return
	 */
	List<TWfActive> listNextDesignActive(String processid, String activeid,
			String runtimeid, String commiter) throws Exception;

	/**
	 * 获得流程设计中默认的下一步节点 注意：流程图中设计的下一步节点不涉及路由选择，系统会忽略路径上 的路由选择条件。
	 * 
	 * @param processid
	 *            流程ID
	 * @param activeid
	 *            活动ID
	 * @param runtimeid
	 *            流程实例id
	 * @param commiter
	 *            流程提交者
	 * @return
	 */
	List<TWfActive> listNextRouteActive(String processid, String activeid,
			String runtimeid, String commiter) throws Exception;

    /**
     *  填写审批意见
     * @param workcode 活动实例id
     * @param participant 参与者
     * @param note 意见说明
     * @return
     * @throws Exception
     */
	public Integer saveAuditNote(String workcode, String participant, String note)
			throws Exception;

	
	/**
	 * 装载活动
	 * 
	 * @param workcode 活动实例id
	 * @return
	 */
	public TWfWorklist loadWorklist(String workcode) throws Exception;

	/**
	 * 装载活动描述
	 * 
	 * @param appname
	 *            应用程序名
	 * @param workcode
	 *            活动实例id
	 * @return
	 * @throws Exception
	 */
	public TWfActive loadActive(String appname, String workcode)
			throws Exception;

	/**
	 * 获得流程核心的流程控制视图句柄
	 * 
	 * @return
	 */
	public WorkflowView useCoreView() throws Exception;

	/**
	 * 获得流程核心的流程控制句柄
	 * 
	 * @return
	 */
	public WorkflowConsole useCoreConsole() throws Exception;

	/**
	 * 获得所有流程
	 * @param 流程资源名
	 * @return
	 * @throws Exception
	 */
	public WorkflowProcess loadProcess(String processid) throws Exception;

	/**
	 * 获得所有流程办理信息
	 * 
	 * @param runtimeid 流程实例id
	 * @param onlyDone  是否仅显示所有已经完成的
	 * @return
	 * @throws Exception
	 */
	public List<TWfParticipant> listAllParticipantinfo(String runtimeid,boolean onlyDone)
			throws Exception;

	/**
	 * 获得所有流程办理信息
	 * 
	 * @param workcode 活动实例id
	 * @param participant 参与者
	 * @return
	 * @throws Exception
	 */
	public TWfParticipant loadParticipantinfo(String workcode,
			String participant) throws Exception;

	/**
	 * 任务期限
	 * 
	 * @param workcode 活动实例id
	 * @param participant 参与者
	 * @param limit 期限
	 * @throws Exception
	 */
	public void specifyLimit(String workcode, String participant, long limit)
			throws Exception;

	/**
	 * 检查当前节点的下一步分支是否为同步模式
	 * 
	 * @param processid 流程实例id
	 * @param activeid 活动实例id
	 * @return
	 */
	public boolean isAndBranchMode(String processid, String activeid)
			throws Exception;

	/**
	 * 检查是否为尾节点
	 * 
	 * @param workcode 活动实例id
	 * @return
	 */
	public boolean checkFinalAct(String workcode) throws Exception;
	
	/**
	 * 检查是否为首节点
	 * 
	 * @param workcode 活动实例id
	 * @return
	 */
	public boolean checkFirstAct(String workcode) throws Exception;

	/**
	 * 获得活动名名
	 * 
	 * @param appid
	 *            应用框架ID
	 * @param workcode
	 *            活动ID (可为空,此时是获得首节点的名字)
	 * @return
	 */
	public String getActivityName(String appid, String workcode)
			throws Exception;
	
	/**
	 * 挂起活动支持延迟激活
	 * @param runtimeid 流程实例id
	 */
	public void pendingProcess(String runtimeid)throws Exception;
	/**
	 * 唤醒被挂起的活动
	 * @param runtimeid流程实例id
	 */
	public void WakeUpProcess(String runtimeid)throws Exception;
	
	/**
	 * 寻找所有异常的流程(工作流引擎是异步工作，在操作过过程中可能会出现活动的参与者分配丢失的情况)
	 * @param fromTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public String[] errorProcess(String fromTime,String endTime);
	
    /**
     * 回滚失效的操作

     * @param runtimeid 流程实例id
     * @param workcode 当前活动节点
     * @param actid 需要回滚到的节点，由用户在流程轨迹界面自行选定 
     * @return
     */
	public int rollbackErrorProcess(String runtimeid,String workcode,String actid);
	
   /**
    * 修复失效的操作
    * @param runtimeid 流程实例id
    * @param commiter 提交者
    * @param operater 执行者
    * @return
    */
	public int repairErrorProcess(String runtimeid,String commitercode,String operatercode);

	/**
	 * 检查用户的待办任务
	 * @param userid
	 * @param workcode
	 * @return
	 */
	public boolean checkUserworklist(String userid,String workcode);
	
	/**
	 * 检查用户是否参与过某流程
	 * @param userid 用户登录名 
	 * @param runtimeid 具体流程的实例ID
	 * @return 如果参与过返回true
	 */
	public boolean checkIfUserJoinProcess(String userid, String runtimeid);
}

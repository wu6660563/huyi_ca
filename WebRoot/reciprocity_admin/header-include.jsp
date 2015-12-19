<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<%
	String currentClass = request.getParameter("currentClass");
 %>

<script type="text/javascript">
	function logout() {
		if(confirm("你确定要退出吗？")) {
			window.document.location = "${ctx}/login.action?operType=logout";
		}
	}
</script>

<div id="header">
	<div class="header-m clearfix">
		<div class="head-left clearfix">
			<img src="${ctx}/reciprocity_admin/images/logo.png" />
			<span>互易客服中心</span>
		</div>
		<div class="head-right">
			<ul class="nav-list">
				<li <%if("index".equals(currentClass)) {out.print("class='current'");}%>>
					<a href="${ctx}/reciprocity_admin/index.jsp"><span>首页</span>
					</a>
				</li>
				<li <%if("feedback".equals(currentClass)) {out.print("class='current'");}%>>
					<a href="${ctx}/feedback.action?operType=list"><span>服务进程</span>
					</a>
				</li>
				<li <%if("contract".equals(currentClass)) {out.print("class='current'");}%>>
					<a href="${ctx}/contract.action?operType=list"><span>合同列表</span>
					</a>
				</li>
				<li <%if("workorder".equals(currentClass)) {out.print("class='current'");}%>>
					<a href="${ctx}/workorder.action?operType=list"><span>工单列表</span>
					</a>
				</li>
				<li <%if("producttype".equals(currentClass)) {out.print("class='current'");}%>>
					<a href="${ctx}/producttype.action?operType=list"><span>产品类型</span>
					</a>
				</li>
				<li <%if("info_diss".equals(currentClass)) {out.print("class='current'");}%>>
					<a href="${ctx}/home.action?operType=info_diss"><span>业务资讯</span>
					</a>
				</li>
				<li <%if("employee".equals(currentClass)) {out.print("class='current'");}%>>
					<a href="${ctx}/employee.action?operType=list"><span>帐户管理</span>
					</a>
				</li>
				<li>
					<a href="javascript:logout();"><span>退出</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/console/include/base.jsp" %>

<nav class="ym-vlist">
	<h6 class="ym-vtitle">管理控制台</h6>
	<ul>
		<li>
			<span>主面板</span>
			<ul>
				<li><a href="javascript:;">统计表盘</a></li>
				<li><a href="javascript:;">家居官网</a></li>
				<li><a href="javascript:;">行业资讯</a></li>
			</ul>
		</li>
		<li>
			<span>生产/成本</span>
			<ul>
				<li<c:if test="${param.vListActive eq 'CostList'}"> class="active"</c:if>><a href="${basePath}/product/list">成本管理</a></li>
				<li<c:if test="${param.vListActive eq 'PlanList'}"> class="active"</c:if>><a href="${basePath}/info/list">生产计划</a></li>
				<li<c:if test="${param.vListActive eq 'ProcedureList'}"> class="active"</c:if>><a href="${basePath}/info/list" >生产工序</a></li>
			</ul>
		</li>
		<li>
			<span>营业/销售</span>
			<ul>
				<li<c:if test="${param.vListActive eq 'OrderList'}"> class="active"</c:if>><a href="${basePath}/product/list">订单管理</a></li>
				<li<c:if test="${param.vListActive eq 'DeliverList'}"> class="active"</c:if>><a href="${basePath}/info/list" >出货管理</a></li>
			</ul>
		</li>
		<li>
			<span>商品/库存</span>
			<ul>
				<li<c:if test="${param.vListActive eq 'ProductList'}"> class="active"</c:if>><a href="${basePath}/product/list">产品列表</a></li>
				<li<c:if test="${param.vListActive eq 'RepertoryList'}"> class="active"</c:if>><a href="${basePath}/info/list">库存管理</a></li>
			</ul>
		</li>
		<li>
			<span>财务/会计</span>
			<ul>
				<li<c:if test="${param.vListActive eq 'CreditList'}"> class="active"</c:if>><a href="${basePath}/product/list">赊账管理</a></li>
				<li<c:if test="${param.vListActive eq 'FinancialList'}"> class="active"</c:if>><a href="${basePath}/info/list">财务报表</a></li>
				<li<c:if test="${param.vListActive eq 'FixedAssetList'}"> class="active"</c:if>><a href="${basePath}/info/list" >固定资产管理</a></li>
			</ul>
		</li>
		<li>
			<span>系统设置</span>
			<ul>
				<c:if test="${sessionScope.SESSION_USER_TYPE eq '1'}">
				<li<c:if test="${param.vListActive eq 'InfoList'}"> class="active"</c:if>><a href="${basePath}/info/list">访问信息</a></li>
				<li<c:if test="${param.vListActive eq 'UserList'}"> class="active"</c:if>><a href="${basePath}/user/list">用户管理</a></li>
				</c:if>
				<li<c:if test="${param.vListActive eq 'passwordList'}"> class="active"</c:if>><a href="${basePath}/user/password">修改密码</a></li>
				<li><a href="javascript:;" onclick="doSignout();">退出</a></li>
			</ul>
		</li>
	</ul>
</nav>

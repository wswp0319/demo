<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords"
	content="html5,jquery,ui,widgets,ajax,ria,shopping,buy">
<meta name="description" content="sss">
<title>demo系统</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/themes/css/kube.css" type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/themes/css/main.css" type="text/css" />
<link id="dlink" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/color.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/validator.js"></script>
	
</head>
<body>
	<div class="easyui-layout" id="main" >
        <div data-options="region:'north'" style="height:70px;">
        		<div class="content" heigth="100%" width="100%" >
			<div class="navbar navbar-left">
				<ul>
					<li><a style="text-decoration:none;font-size:28px;" >demo系统</a></li>
				</ul>
			</div>
			<div class="navbar navbar-right">
				<ul>
					<li><a style="text-decoration:none" >欢迎 ${sessionScope.user.systemUser.loginName}</a></li>
					<li><a style="text-decoration:none" href="javascript:setting();" >设置</a></li>
					<li><a style="text-decoration:none" 
						href="${pageContext.request.contextPath}/m!/logout.htm">退出</a></li>
				</ul>
			</div>
			</div>
        </div>
        
        <div data-options="region:'west',split:true" title="菜单栏" style="width:200px;">
        		<div class="easyui-accordion" data-options="fit:true,border:false">								
				<c:forEach items="${sessionScope.user.parentMenuList}"
					var="parentMenu">
					<div title="${parentMenu.menuDesc}" style="padding: 10px;"
						data-options="selected:true">
						<ul class="easyui-tree">
							<c:forEach items="${sessionScope.user.allMenuList}"
								var="childMenu">
								<c:if test="${childMenu.parentId eq parentMenu.menuId}">
									<li><a href="javascript:void(0);"
										style="text-decoration: none;"
										onclick="addIframe('tabs','${childMenu.menuDesc}','${pageContext.request.contextPath}${childMenu.menuUrl}');">${childMenu.menuDesc}</a>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>
				<div title="MongoDB" style="padding: 10px;"
						data-options="selected:true">
						<ul class="easyui-tree">
							<li><a href="javascript:void(0);"
								style="text-decoration: none;"
								onclick="addIframe('tabs','MongoDB demo','${pageContext.request.contextPath}/mongo!/init.htm');">MongoDB demo</a>
							</li>
						</ul>
					</div>
				<div title="呼叫专车" style="padding: 10px;"
						data-options="selected:true">
						<ul class="easyui-tree">
							<li><a href="javascript:void(0);"
								style="text-decoration: none;"
								onclick="addIframe('tabs','呼叫专车 demo','${pageContext.request.contextPath}/d!/init.htm');">呼叫专车 demo</a>
							</li>
						</ul>
					</div>
				<div title="Websocket" style="padding: 10px;"
						data-options="selected:true">
						<ul class="easyui-tree">
							<li><a href="javascript:void(0);"
								style="text-decoration: none;"
								onclick="addIframe('tabs','Websocket demo','${pageContext.request.contextPath}/w!/init.htm');">Websocket demo</a>
							</li>
						</ul>
					</div>
			</div>
        </div>
        <div data-options="region:'center'">
            <div id="tabs" class="easyui-tabs"
				data-options="tools:'#tab-tools',fit:true">
				<!--div title="公告栏" style="padding: 1px">
				<table id="data-grid" >
					<thead>
						<tr>
							<th data-options="field:'department',width:80,sortable:true">部门</th>
							<th data-options="field:'category',width:80,formatter:formatCategory">类别</th>
							<th data-options="field:'fileName',width:80,sortable:true,formatter:formatOper">文件名</th>
							<th data-options="field:'version',width:80">版本</th>
							<th data-options="field:'createTime',width:80,formatter:ww3">发布时间</th>
							<th data-options="field:'safety',width:80,formatter:formatSafety">涉密级别</th>
						</tr>
					</thead>
				</table>
				<div id="tb">
					<div>
						类别: <select class="easyui-combobox" id="qcategory" style="width:100px;">
				                <option value="1">制度</option>
				                <option value="3">规范</option>
				              </select>
						文件名: <input id="qfileName" class="easyui-textbox" style="width:100px" /> 
						<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="dosearch();">查询</a>
					</div>
				</div>
				</div-->
			</div>
			<div id="tab-tools">
				<a href="#" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-cancel'"
					onclick="closeAll('tabs')">关闭所有页面</a>
			</div>
        </div>
        <div data-options="region:'south',split:true" style="height:50px;"></div>
    </div>
	<!--div id="header" class="group wrap header">
		<div class="content">
			<div class="navigation-toggle" data-tools="navigation-toggle"
				data-target="#navbar-1">
				<span>EasyUI</span>
			</div>
			<div id="elogo" class="navbar navbar-left">
				<ul>
					<li><a style="text-decoration:none;"><span style="color:#FFFFFF;font-size:24px;">易酷管理系统</span></a></li>
				</ul>
			</div>
			<div id="navbar-1" class="navbar navbar-right">
				<ul>
					<li><a style="text-decoration:none;">欢迎 ${sessionScope.user.systemUser.loginName}</a></li>
					<li><a href="javascript:setting();" >设置</a></li>
					<li><a
						href="${pageContext.request.contextPath}/m!/logout.htm">退出</a></li>
				</ul>
			</div>
			<div style="clear: both"></div>
		</div>
	</div>
	
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'west',split:true" title="菜单栏" split="true" style="width: 200px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">								
				<c:forEach items="${sessionScope.user.parentMenuList}"
					var="parentMenu">
					<div title="${parentMenu.menuDesc}" style="padding: 10px;"
						data-options="selected:true">
						<ul class="easyui-tree">
							<c:forEach items="${sessionScope.user.allMenuList}"
								var="childMenu">
								<c:if test="${childMenu.parentId eq parentMenu.menuId}">
									<li><a href="javascript:void(0);"
										style="text-decoration: none;"
										onclick="addIframe('tabs','${childMenu.menuDesc}','${pageContext.request.contextPath}${childMenu.menuUrl}');">${childMenu.menuDesc}</a>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>
			</div>
		</div>
		<div data-options="region:'center'" style="padding: 1px">
			<div id="tabs" class="easyui-tabs"
				data-options="tools:'#tab-tools',fit:true">
				<div title="公告栏" style="padding: 1px">
				<table id="data-grid" >
					<thead>
						<tr>
							<th data-options="field:'department',width:80,sortable:true">部门</th>
							<th data-options="field:'category',width:80,formatter:formatCategory">类别</th>
							<th data-options="field:'fileName',width:80,sortable:true,formatter:formatOper">文件名</th>
							<th data-options="field:'version',width:80">版本</th>
							<th data-options="field:'createTime',width:80,formatter:ww3">发布时间</th>
							<th data-options="field:'safety',width:80,formatter:formatSafety">涉密级别</th>
						</tr>
					</thead>
				</table>
				<div id="tb">
					<div>
						类别: <select class="easyui-combobox" id="qcategory" style="width: 00px;">
				                <option value="1">制度</option>
				                <option value="3">规范</option>
				              </select>
						文件名: <input id="qfileName" class="easyui-textbox" style="width:100px" /> 
						<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="dosearch();">查询</a>
					</div>
				</div>
				</div>
			</div>
			<div id="tab-tools">
				<a href="#" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-cancel'"
					onclick="closeAll('tabs')">关闭所有页面</a>
			</div>
		</div>
		<div data-options="region:'south',split:true" style="height:18%;" >  

        </div>  
	</div-->


	<div id="info-window" class="easyui-window" title="用户信息"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 500px; height: 400px; padding: 10px;">
		<div class="easyui-tabs" style="width: 100%; height: 100%">
			<div title="About" style="padding: 5px">
				<div style="padding: 10px 0 10px 60px">
					<h5 id="utip" style="color: red;"></h5>
					<form id="update-form" class="easyui-form" method="post"
						data-options="novalidate:true">
						<table>
							<tr>
								<td>登陆名:</td>
								<td><label id="uu_account">${sessionScope.user.systemUser.loginName}</label></td>
							</tr>
							<tr>
								<td>姓名:</td>
								<td><input class="easyui-textbox" type="text" value=""
									name="userName" style="width: 200px;" maxlength="20"
									data-options="required:true,validType:'useranme'"></input></td>
							</tr>
							<tr>
								<td>Email:</td>
								<td><input class="easyui-textbox" type="text" value=""
									name="email" style="width: 200px;" maxlength="64"
									data-options="required:true,validType:'email'"></input></td>
							</tr>
							<tr>
								<td>移动电话:</td>
								<td><input class="easyui-textbox" type="text" value=""
									name="mobilePhone" style="width: 200px;" maxlength="20"
									data-options="required:true,validType:'mobile'"></input></td>
							</tr>
						</table>
						<input type="hidden" name="roleId"
							value="${sessionScope.user.systemUser.systemRole.id}" /> 
						<input type="hidden" name="id"
							value="${sessionScope.user.systemUser.id}" /> 
					</form>
				</div>
				<div style="text-align: center; padding: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton c6"
						iconCls="icon-ok" onclick="updateform()">提交</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel" onclick="$('#info-window').window('close')">关闭</a>
				</div>
			</div>
			<div title="修改密码" style="padding: 10px">
				<div style="padding: 10px 0 10px 60px">
					<h5 id="ptip" style="color: red;"></h5>
					<form id="changepswd-form" class="easyui-form" method="post"
						data-options="novalidate:true">
						<table>
							<input type="hidden" name="loginName"
								value="${sessionScope.user.systemUser.loginName}" />
							<tr>
								<td>旧密码:</td>
								<td><input class="easyui-textbox" type="password"
									id="oldPassword" name="oldPassword" style="width: 200px;"
									maxlength="20" data-options="required:true"></input></td>
							</tr>
							<tr>
								<td>新密码:</td>
								<td><input class="easyui-textbox" type="password"
									id="newPassword" name="newPassword" style="width: 200px;"
									maxlength="20"
									data-options="required:true,validType:'safepass'"></input></td>
							</tr>
							<tr>
								<td>确认密码:</td>
								<td><input class="easyui-textbox" type="password"
									id="repassword" style="width: 200px;" maxlength="20"
									required="true" validType="equalTo['#newPassword']"></input></td>
							</tr>
						</table>
					</form>
				</div>
				<div style="text-align: center; padding: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton c6"
						iconCls="icon-ok" onclick="modifypassword()">提交</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel"
						onclick="$('#info-window').window('close')">关闭</a>
				</div>
			</div>
		</div>
	</div>
	
</body>
<script type="text/javascript">

		function setting() {
			//alert('dsf');
			$
					.ajax({
						url : "${pageContext.request.contextPath}/u!/findSystemUserByName.htm",
						type : 'post',
						dataType : 'json',
						data : {
							'loginName' : '${sessionScope.user.systemUser.loginName}'
						},
						error : function() {// 请求失败处理函数
							//$.messager.alert('提示框', '请求失败', 'error');
						},
						success : function(data) {
							//alert(data);
							//var obj = jQuery.parseJSON(data);
							$('#update-form')
									.form(
											'load',
											{
												'loginName' : data.loginName,
												'userName' : data.userName,
												'email' : data.email,
												'mobilePhone' : data.mobilePhone
											});
							$('#info-window').window('open');
						}
					});
		}

		function updateform() {
			submitForm('update-form',
					'${pageContext.request.contextPath}/u!/updateSystemUser.htm');
		}

		function modifypassword() {
			submitForm('changepswd-form',
					'${pageContext.request.contextPath}/u!/modifyPassword.htm');
		}
		
		$("#data-grid").datagrid({
			rownumbers:true,
			fit:true,
			fitColumns:true,
			nowrap: false,
			striped:false,
			singleSelect:true,
			remoteSort:false,
			idField:'id',
			pageSize:20,
			pageList:[10,20,50],
			type : 'POST',
			url:'${pageContext.request.contextPath}/document/queryAffiche.htm',
			toolbar:'#tb',
			pagination:true
		});
		
		function dosearch() {
			var category = $('#qcategory').val();
			var fileName = $('#qfileName').val();
			var query = {
				'category' : category,
				'fileName' : fileName
			}; //把查询条件拼接成JSON
			$('#data-grid').datagrid('options').queryParams = query; //把查询条件赋值给datagrid内部变量
			$('#data-grid').datagrid('reload'); //重新加载
		}
		
		function formatOper(val,row,index) {  
		    return '<a href=${pageContext.request.contextPath}/document/download.htm?id='+row.id+' target=_blank style=text-decoration:none>'+row.fileName+'</a>';
		    //return '<a href=javascript:download("'+row.id+'") >下载</a>';
		} 
		
		function download(id) {
			$.ajax({
		           type: 'GET',
		           url: '${pageContext.request.contextPath}/document/download.htm',
		           contentType:'application/json',
		           data: {'id':id},//参数列表
		           dataType:'json',
		           success: function(result){
		              //请求正确之后的操作
		           },
		           error: function(result){
		              //请求失败之后的操作
		           }
		    });
		}
		
		$(document).ready(function(){  
	        var height1 = $(window).height()-5;  
	        $("#main").attr("style","width:100%;height:"+height1+"px");  
	        $("#main").layout("resize",{  
	            width:"100%",  
	            height:height1+"px"  
	        });  
	    });    
	      
	    $(window).resize(function(){  
	        var height1 = $(window).height()-10;  
	        $("#main").attr("style","width:100%;height:"+height1+"px");  
	        $("#main").layout("resize",{  
	            width:"100%",  
	            height:height1+"px"  
	        });  
	    });   
	</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MongoDB demo</title>
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
	<table id="data-grid" class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id',width:80,checkbox:true"></th>
				<th data-options="field:'username',width:80,sortable:true">姓名</th>
				<th data-options="field:'age',width:80,sortable:true">年龄</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div style="margin-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" 
			iconCls="icon-add" plain="true" onclick="adduser();">添加</a>| <a
				href="javascript:void(0)" class="easyui-linkbutton" 
				iconCls="icon-remove" plain="true" onclick="deluser();">删除</a>| 
																  
				<!-- <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="update();">修改</a>|
				 -->
		</div>
		<div>
			姓名: <input id="quserName" class="easyui-textbox" style="width: 100px"> 
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="dosearch();">查询</a>
		</div>
	</div>

	<!-- 添加用户窗体 -->
	<div id="add-window" class="easyui-window" title="添加用户信息"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 800px; height: 400px; padding: 10px;">
		<div style="padding: 10px 0 10px 0px">
			<h5 id="tip" style="color: red;"></h5>
			<form id="add-form" class="easyui-form" method="post" data-options="novalidate:true">
				<table>
					<tr>
						<td style="min-width:85px;text-align:right;"><span style="color:red">*</span>姓名:</td>
						<td><input class="easyui-textbox" type="text"
							name="username" style="width: 200px;" maxlength="20"
							data-options="required:true,validType:'useranme'"></input></td>
						<td style="min-width:85px;text-align:right;"><span style="color:red">*</span>年龄:</td>
						<td><input class="easyui-textbox" type="text"
							name="age" style="width: 200px;" maxlength="20"
							data-options="required:true,validType:'number'"></input></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="oprDialog-buttons" style="text-align: center; padding: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="submitform()">提交</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="$('#add-window').window('close')">关闭</a>
		</div>
	</div>

	<div id="update-window" class="easyui-window" title="修改用户信息"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width: 800px; height: 400px; padding: 10px;">
		<div style="padding: 10px 0 10px 0px">
			<h5 id="utip" style="color: red;"></h5>
			<form id="update-form" class="easyui-form" method="post" data-options="novalidate:true">
				<table>
					<tr>
						<td style="min-width:85px;text-align:right;">姓名:</td>
						<td><input class="easyui-textbox" type="text"
							name="username" style="width: 200px;" maxlength="20"
							data-options="required:true,validType:'useranme'"></input></td>
						<td style="min-width:85px;text-align:right;">年龄:</td>
						<td><input class="easyui-textbox" type="text"
							name="age" style="width: 200px;" maxlength="64"
							data-options="required:true,validType:'number'"></input></td>
					</tr>
				</table>
				<input type="hidden" name="id" />
			</form>
		</div>
		<div style="text-align: center; padding: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateform()">提交</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#update-window').window('close')">关闭</a>
		</div>
	</div>
	
	<script type="text/javascript">
	function adduser() {
		$('#tip').html('');
		clearForm('add-form');
		$('#add-window').window('open');
	}

	function submitform(frm) {
		submitForm('add-form','${pageContext.request.contextPath}/mongo!/addUser.htm','data-grid');
	}

	function deluser() {
		var row = $('#data-grid').datagrid('getSelected');
		if (!row) {
			$.messager.alert('提示框', '请选择要删除的记录!', 'error');
			return;
		}
		var ids = [];
		var selectedRow = $('#data-grid').datagrid('getSelections');
		for (var i = 0; i < selectedRow.length; i++) {
			ids.push(selectedRow[i].id);
		}
		var delIds = ids.join(',');
		var input = {
			'id' : delIds
		};
		
		deleteByParam('data-grid','${pageContext.request.contextPath}/mongo!/deleteUser.htm',input);
	}

	function updateform() {
	    
		submitForm('update-form','${pageContext.request.contextPath}/mongo!/updateUser.htm','data-grid');
	}

	function dosearch() {
		var username = $('#quserName').val();
		var query = {
			'username' : username
		}; //把查询条件拼接成JSON
		$('#data-grid').datagrid('options').queryParams = query; //把查询条件赋值给datagrid内部变量
		$('#data-grid').datagrid('reload'); //重新加载
	}
	
	function formatRole(obj) {
		if(obj==null) {
			return "";
		}
		return obj.roleName;
	}
	
	$("#data-grid").datagrid({
		rownumbers:true,
		fit:true,
		fitColumns:true,
		nowrap: false,
		striped:true,
		singleSelect:false,
		remoteSort:false,
		pageSize:20,
		pageList:[10,20,50],
		type : 'POST',
		url:'${pageContext.request.contextPath}/mongo!/findAll.htm',
		toolbar:'#tb',
		onDblClickRow: function(index,row){			
			if (row.id == null || row.id == '') {
				$.messager.alert('提示框', '请选择一条记录', 'error');
			} else {
				//传入用户id，根据用户id查询用户部门解决get传中文乱码问题
				/*$('#uu_department').combobox(
						'reload',
						'${pageContext.request.contextPath}/p!/findSystemByField.htm?paramField=department&id='
								+ row.id);*/
				$.ajax({ 
					type:'POST', 
					url: '${pageContext.request.contextPath}/p!/findSystemByField.htm', 
					data: { 
						paramField:'department',
						id:row.id
					}, 
					success: function(data) { 
						
					} 
				}); 
								
				
				//$("#update-form input[id=hoid]").attr("value",selectedRow[0].oprId);
				//alert($("#update-form input[id=hoid]").val());
				$('#update-form').form('load',{
	                'id':row.id,
	                'username':row.username,
	                'age':row.age
	            });
				
				$('#update-window').window('open');
			}
		},
		pagination:true
	});
</script>
</body>
</html>

$(function(){  
    $.ajaxSetup({  
        contentType: "application/x-www-form-urlencoded;charset=utf-8",  
        cache: false,  
        complete: function(XHR, TS){  
            var resText = XHR.responseText;  
            var sessionstatus = XHR.getResponseHeader("sessionstatus");  
            var loginPath = XHR.getResponseHeader("loginPath");  
            //console.log(resText);
            //console.log(sessionstatus);
            //console.log(loginPath);
            if (403 == XHR.status && "timeout" == sessionstatus) {  
                // 此处使用了开源的消息确认框   
               /* $.messager.confirm('session过期', '您的会话已经过期，请重新登陆后继续操作！', function(confirm){  
                    if (confirm) {  
                        window.location.replace(loginPath);  
                    }  
                });  */
                // 也可以使用下面的原生js的确认框，如果确认则跳转  
                //if(window.confirm('session过期', '您的会话已经过期，请重新登陆后继续操作！')) {  
                		window.top.location.replace(loginPath);  
                //}  
                return;  
            }  
        }  
    });  
});  
function ww2(d) {
	if(date==null) {
		return "";
	}
	return d.getFullYear() + '-' +(d.getMonth()+1);
}
function w2(s) {
	if (!s) return new Date();
    var arr = s.split('-');
    return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
}
function ww3(date) {
	if(date==null) {
		return "";
	}
	// alert(date.year);
	var y = parseInt(date.year) + 1900;
	var m = parseInt(date.month) + 1;
	var d = parseInt(date.date);
	var h = parseInt(date.hours);
	var min = parseInt(date.minutes);
	var sec = parseInt(date.seconds);
	var str = y + '-' + (m < 10 ? ('0' + m) : m) + '-'
			+ (d < 10 ? ('0' + d) : d); /*+ ' ' + ' ' + (h < 10 ? ('0' + h) : h)
			+ ':' + (min < 10 ? ('0' + min) : min) + ':'
			+ (sec < 10 ? ('0' + sec) : sec);*/
	return str;
}
function checkdate(date) {
	if(date==null) {
		return "";
	}
	// alert(date.year);
	var y = parseInt(date.year) + 1900;
	var m = parseInt(date.month) + 1;
	var d = parseInt(date.date);
	var h = parseInt(date.hours);
	var min = parseInt(date.minutes);
	var sec = parseInt(date.seconds);
	var str = y + '-' + (m < 10 ? ('0' + m) : m) + '-'
	+ (d < 10 ? ('0' + d) : d)  ;
return str;
}
function w3(s) {
	if (!s)
		return new Date();
	var y = s.substring(0, 4);
	var m = s.substring(5, 7);
	var d = s.substring(8, 10);
	var h = s.substring(11, 14);
	var min = s.substring(15, 17);
	var sec = s.substring(18, 20);
	
	if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min)
			&& !isNaN(sec)) {
		return new Date(y, m-1, d, h, min, sec);
	} 
	else {
		return new Date();
	}
}

//是否开票
function isInvoice(val,row) {
	if(val==1) {
		return '已开票';
	}else{
		return '未开票';
	}
}

//是否回款
function isPay(val,row) {
	if(val==1) {
		return '已回款';
	}else{
		return '未回款';
	}
}

function formatCategory(val, row) {
	if (val == 1) {
		return "制度";
	} else if (val == 2) {
		return "合同";
	} else if (val == 3) {
		return "规范";
	}
}

function formatSafety(val, row) {
	if (val == 7) {
		return "绝密";
	} else if (val == 6) {
		return "机密";
	} else if (val == 5) {
		return "易酷文档";
	} else if (val == 4) {
		return "易软文档";
	}
}

function formatGender(val,row){
	if (val == 0) {
		return "女";
	} else if (val == 1) {
		return "男";
	} else if (val == 2) {
		return "不限";
	}
}

//1阶段验收单 2月结单
function formatPayCondition(val,row){
	//console.log('formatPayCondition: '+val);
	if(val==null) {
		return '';
	}
	
	if(val==1) {
		return '阶段验收单';
	}else if(val==2){
		return '月结单';
	}else{
		return '';
	}
}

//1销售合同 2采购合同
function formatContractType(val,row){
	//console.log('formatContractType: '+val);
	if(val==null) {
		return '';
	}
	if (val == '1') {
		return '销售合同';
	} else if (val == '2') {
		return '采购合同';
	} 
}

//1非框架合同 2框架合同
function formatContractCategory(val,row){
	//console.log('formatContractCategory: '+val);
	if (val == 1) {
		return '非框架合同';
	} else if (val == 2) {
		return '框架合同';
	} 
}

//1增值税专用发票 2增值税普通发票
function formatInvoiceType(val,row){
	if (val == 1) {
		return '增值税专用发票';
	} else if (val == 2) {
		return '增值税普通发票';
	} 
}

function resetContent(formId) {
	var clearForm = document.getElementById(formId);
	if (null != clearForm && typeof (clearForm) != "undefined") {
		clearForm.reset();
	}
}

function flashTable(dataTableId) {
	$('#' + dataTableId).datagrid('reload');
}

function clearSelections(dataTableId) {
	$('#' + dataTableId).datagrid('clearSelections');
	// 取消选择DataGrid中的全选
	$("input[type='checkbox']").eq(0).attr("checked", false);
}

function closeDialog(dialogId) {
	$('#' + dialogId).dialog('close');
}

function fillsize(percent) {
	var bodyWidth = document.body.clientWidth;
	return (bodyWidth - 90) * percent;
}

function getSingleSelectRow(dataTableId, errorMessage) {
	var rows = $('#' + dataTableId).datagrid('getSelections');
	var num = rows.length;
	if (num == 1) {
		return rows[0];
	} else {
		$.messager.alert('提示消息', errorMessage, 'info');
		return null;
	}
}

function getSelectRows(dataTableId, errorMessage) {
	// var row = $('#'+dataTableId).datagrid('getSelected');
	var ids = [];
	var selectedRow = $('#' + dataTableId).datagrid('getSelections');

	for (var i = 0; i < selectedRow.length; i++) {
		ids.push(selectedRow[i].CId);
	}
	var strIds = ids.join(',');
	return strIds;
}

function deleteByParam(grid, url, input) {

	$.messager.confirm('提示框', '确定要删除吗?', function(r) {
		if (r) {
			var messgage = '删除成功!';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				dataType : 'json',
				data : input,
				url : url,// 请求的action路径
//				error : function() {// 请求失败处理函数
//					$.messager.alert('提示框', '请求失败', 'error');
//					flashTable(grid);
//
//				},
				success : function(data) {
					$.messager.alert('提示框', data.message, 'info');
					flashTable(grid);
					clearSelections(grid);
				}
			});
		}
	});
}

function clearForm(id) {
	$('#' + id).form('clear');
}

/**
 * 刷新tab
 * 
 * @cfg example: {tabTitle:'tabTitle',url:'refreshUrl'}
 *      如果tabTitle为空，则默认刷新当前选中的tab 如果url为空，则默认以原来的url进行reload
 */
function refreshTab(cfg) {
	var refresh_tab = cfg.tabTitle ? $('#tabs').tabs('getTab', cfg.tabTitle)
			: $('#tabs').tabs('getSelected');
	if (refresh_tab && refresh_tab.find('iframe').length > 0) {
		var _refresh_ifram = refresh_tab.find('iframe')[0];
		var refresh_url = cfg.url ? cfg.url : _refresh_ifram.src;
		// _refresh_ifram.src = refresh_url;
		_refresh_ifram.contentWindow.location.href = refresh_url;
	}
}

function addIframe(tabId, title, url) {
	// closeAll(tabId);
	if ($('#' + tabId).tabs('exists', title)) {
		$('#' + tabId).tabs('close', title);
		// refreshTab({tabTitle:title,url:url});
	}
	var content = '<iframe scrolling="auto" frameborder="0" src="' + url
			+ '" style="width:100%; height:100%"></iframe>';

	$('#' + tabId).tabs('add', {
		title : title,
		content : content,
		style : "overflow:hidden;padding:5px",
		closable : true
	});
}

// 除首页tab外，关闭所有已打开的tab页
function closeAll(tabId) {
	var ts = [];
	$.each($('#' + tabId).tabs('tabs'), function(i, n) {
		if (n.panel('options').title != '公告栏') {
			ts.push(n.panel('options').title);
		}
	});
	for (var j = 0; j < ts.length; j++) {
		$('#' + tabId).tabs('close', ts[j]);
	}
}

function submitForm(form, url, grid) {
	$('#' + form).form('submit', {
		async : false,
		cache : false,
		type : 'POST',
		dataType : 'json',
		url : url,// 请求的action路径
//		error : function() {// 请求失败处理函数
//			$.messager.alert('提示框', '请求失败', 'error');
//		},
		success : function(data) {
			if(data==null || $.trim(data)=='') {
				$.messager.alert('消息', '服务器内部错误！');
				return;
			}
			console.log(data);
			var obj = jQuery.parseJSON(data);
			$.messager.alert('消息', obj.message);
			if (grid) {
				flashTable(grid);
			}
		},
		onSubmit : function() {
			return $('#' + form).form('enableValidation').form('validate');
		}
	});
}
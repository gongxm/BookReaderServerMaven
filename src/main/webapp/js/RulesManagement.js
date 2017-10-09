// 校验数据
function checkData(id) {
	var node = document.getElementById(id);
	var value = node.value;
	if (id == "book_source") {
		if (value == "") {
			alert("请输入数据源!");
		} else {
			return value;
		}
	} else if (id == "flag") {
		if (value == "") {
			alert("请输入通配符!");
		} else {
			return value;
		}
	} else if (id == "baseUrl") {
		if (value == "") {
			alert("请输入采集URL!");
		} else {
			return value;
		}
	} else if (id == "rulesName") {
		if (value == "") {
			alert("请输入规则名称");
		} else {
			return value;
		}
	} else if (id == "startIndex") {
		if (value == "") {
			alert("请输入开始索引!");
			return;
		}
		var reg = /^\d+$/;
		if (!reg.test(value)) {
			alert("请输入正确的开始索引,必须是整数!");
		} else {
			return value;
		}
	} else if (id == "endIndex") {
		if (value == "") {
			alert("请输入结束索引!");
			return;
		}
		var reg = /^\d+$/;
		if (!reg.test(value)) {
			alert("请输入正确的结束索引,必须是整数!");
		} else {
			return value;
		}
	} else if (id == "contentDivClass") {
		if (value == "") {
			alert("请输入内容区域class标签!");
		} else {
			return value;
		}
	} else if (id == "regex") {
		if (value == "") {
			alert("请输入正则表达式!");
		} else {
			return value;
		}
	}
}



// 定义居中显示方法
$(document).ready(
		function() {
			jQuery.fn.extend({
				center : function(width, height) {
					return $(this).css(
							"left",
							($(window).width() - width) / 2
									+ $(window).scrollLeft()).css(
							"top",
							($(window).height() - height) / 2
									+ $(window).scrollTop())
							.css("width", width).css("height", height);
				}
			});
		});

// 悬浮框居中显示
$(document).ready(function() {
	$("#add").click(function() {
		$("#operate").text("添加新规则");
		$("#bt_submit").val("添加新规则");
		$("#id").val('');
		$("#rulesName").val('');
		$("#book_source").val('');
		$("#baseUrl").val('');
		$("#flag").val('');
		$("#startIndex").val('');
		$("#endIndex").val('');
		$("#contentDivClass").val('');
		$("#regex").val('');
		$("#concatUrl").val('');
		$("#over").show();// 展现遮罩层屏幕
		$("#floatdiv").show().center(800, 650);// 展现悬浮框
	});
});

// 悬浮框隐藏
$(document).ready(function() {
	$("#close").click(function() {
		$("#floatdiv").hide();
		$("#over").hide();
	});
});

//打开编辑书籍信息规则页面
function editBookRules(thisObj){
	var $td = $(thisObj).parents('tr').children('td');

	var id = $td.eq(0).text();
	
	// 根据ID获取到当前要编辑的规则内容
	window.location.href = 'showBookRules?id='+id;
}



// 表单提交
$(document).ready(function() {

	$("form[name=rulesform]").submit(function() { // 

		// 表单内容校验
		var rulesName = checkData("rulesName");
		if (rulesName == undefined) {
			return false;
		}
		var book_source = checkData("book_source");
		if (book_source == undefined) {
			return false;
		}
		var url = checkData("baseUrl");
		if (url == undefined) {
			return false;
		}
		var flag = checkData("flag");
		if (flag == undefined) {
			return false;
		}
		var startIndex = checkData("startIndex");
		if (startIndex == undefined) {
			return false;
		}
		var endIndex = checkData("endIndex");
		if (endIndex == undefined) {
			return false;
		}

		var contentDivClass = checkData("contentDivClass");
		if (contentDivClass == undefined) {
			return false;
		}
		var regex = checkData("regex");
		if (regex == undefined) {
			return false;
		}

		var FormData = $("form[name=rulesform]").serializeArray(); // 转换为Json格式数据

		$.ajax({
			url : $("form[name=rulesform]").attr('action'),
			type : "POST",
			data : FormData,
			success : function(data, textStatus) {
				if ("success" == textStatus) {
					if (data.errcode == 1) {
						alert("操作成功!")
					} else {
						alert("操作失败!")
					}
					window.location.reload();
				} else {
					alert("请求失败,请重试!")
				}
			}
		});
		return false; // 设置为 false 这样表单提交就不会页面跳转
	});
});

// 编辑规则内容
function editRules(thisObj) {
	var $td = $(thisObj).parents('tr').children('td');

	var id = $td.eq(0).text();
	
	// 根据ID获取到当前要编辑的规则内容
	$.ajax({
		url : "getRules",
		type : "POST",
		data : "{id:" + id + "}",
		contentType : 'application/json',// 请求的内容类型
		success : function(data, textStatus) {
			if ("success" == textStatus) {
				if (data.errcode == 1) {
					$("#over").show();// 展现遮罩层屏幕
					$("#floatdiv").show().center(800, 700);// 展现悬浮框
					
					$("#operate").text("修改规则");
					$("#bt_submit").val("修改规则");
					
					$("form[name=rulesform]").attr('action', 'updateRules');
					var rules = data.result;
					$("#id").val(rules.id);
					$("#rulesName").val(rules.rulesName);
					$("#book_source").val(rules.book_source);
					$("#baseUrl").val(rules.baseUrl);
					$("#flag").val(rules.flag);
					$("#startIndex").val(rules.startIndex);
					$("#endIndex").val(rules.endIndex);
					$("#contentDivClass").val(rules.contentDivClass);
					$("#regex").val(rules.regex);
					var repeat = rules.isRepeat
					$("#repeat").find("option[value='"+repeat+"']").attr("selected",true);

				} else {
					alert(textStatus)
				}
			} else {
				alert("请求失败!")
			}
			
		}
	});

}

//删除规则
function deleteRules(thisObj) {
	var $td = $(thisObj).parents('tr').children('td');

	var id = $td.eq(0).text();
	
	if (confirm("是否删除该规则？")) {
		// 根据ID删除要编辑的规则内容
		$.ajax({
			url : "delRules",
			type : "POST",
			data : "{id:" + id + "}",
			contentType : 'application/json',// 请求的内容类型
			success : function(data, textStatus) {
				if ("success" == textStatus) {
					if (data.errcode == 1) {
						alert("删除规则成功!")
					} else {
						alert(data.errmsg)
					}
				} else {
					alert("请求失败!")
				}
				window.location.reload();//刷新当前页面
			}
		});
	}
}



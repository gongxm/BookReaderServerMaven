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

// 表单提交
$(document).ready(function() {

	$("#close").click(function() {
		$("#floatdiv").hide();
		$("#over").hide();
		$("#datasource tr").empty(""); 
	});

	$("#edit").click(function() {
		
		var id = $("#id").val();
		
		// 根据ID获取到当前要编辑的规则内容
		$.ajax({
			url : "showAllBookRules",
			type : "POST",
			contentType : 'application/json',// 请求的内容类型
			success : function(data, textStatus) {
				if ("success" == textStatus) {
					if (data.errcode == 1) {
						var rules = data.result;
						var length = rules.length
						if(length>0){
							for(var i=0;i<length;i++){
								$("#datasource").prepend("<tr><td class='left td_source'><input type='radio' name='source' value='"+rules[i].id+"'/>"+rules[i].rulesName+"</td></tr>")
							}
							$("#datasource").append("<tr><td align='center'><input type='button' value='确定' onclick='selectSource()'/></td></tr>");
							
						}else{
							$("#datasource").prepend("<tr><td colspan='2' class='center red'>没有数据!!!</td></tr>")
						}
					}
					$("#over").show();// 展现遮罩层屏幕
					$("#floatdiv").show().center(800, 300);// 展现悬浮框
				}
			},
			error:function(data){
				alert("请求失败!")
			}
		});
	});
	

	$("#cancel").click(function() {
		window.location.href = 'showAllRules';
	});

	$("#bookRulesForm").submit(function() { // 

		var FormData = $("form[name=bookRulesForm]").serializeArray(); // 转换为Json格式数据
		$.ajax({
			url : "updateBookRules",
			type : "POST",
			data : FormData,
			success : function(data, textStatus) {
				if ("success" == textStatus) {
					if (data.errcode == 1) {
						alert("操作成功!")
						window.location.href = 'showAllRules';
					} else {
						alert("操作失败!")
					}
				} else {
					alert("请求失败,请重试!")
				}
			}
		});
		return false; // 设置为 false 这样表单提交就不会页面跳转
	});
	
/*	$("#contentRules").submit(function() { // 

		var FormData = $("form[name=contentRules]").serializeArray(); // 转换为Json格式数据

		$.ajax({
			url : "updateContentRules",
			type : "POST",
			data : FormData,
			success : function(data, textStatus) {
				if ("success" == textStatus) {
					if (data.errcode == 1) {
						alert("操作成功!")
						window.location.href = 'showAllRules';
					} else {
						alert("操作失败!")
					}
				} else {
					alert("请求失败,请重试!")
				}
			}
		});
		return false; // 设置为 false 这样表单提交就不会页面跳转
	});
	*/
});


function selectSource(){
	var id = $("input[name='source']:checked").val();
	if(typeof(id)=="undefined"){ 
		alert("请选择数据源!")
		return;
	}
	
	
	$.ajax({
		url : "getBookRules",
		type : "POST",
		contentType : 'application/json',// 请求的内容类型
		data : "{id:"+id+"}",
		success : function(data, textStatus) {
			if ("success" == textStatus) {
				if (data.errcode == 1) {
					var rules = data.result;
					if(rules){
						$("#id").val(rules.id);
						$("#rulesName").val(rules.rulesName);
						$("#titleRegex").val(rules.titleRegex);
						$("#authorRegex").val(rules.authorRegex);
						$("#categoryRegex").val(rules.categoryRegex);
						$("#statusRegex").val(rules.statusRegex);
						$("#coverRegex").val(rules.coverRegex);
						$("#shortIntroduceRegex").val(rules.shortIntroduceRegex);
						$("#contentDivClass").val(rules.contentDivClass);
						$("#contentDivRegex").val(rules.contentDivRegex);
					}
					$("#floatdiv").hide();
					$("#over").hide();
					$("#datasource tr").empty(""); 
				} else {
					alert("请求失败!")
				}
			} else {
				alert("请求失败,请重试!")
			}
		}
	});
}

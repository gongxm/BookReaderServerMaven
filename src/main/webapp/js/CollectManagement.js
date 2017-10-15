
//更新所有内容
function update(thisObj) {
	var $td = $(thisObj).parents('tr').children('td');
	var id = $td.eq(0).text();
	$(thisObj).attr('disabled', "true");// 添加disabled属性
	
	$.ajax({
		url : "collectBookInfo",
		type : "POST",
		data : "{id:" + id + ",update:true}",
		contentType : 'application/json',// 请求的内容类型
		success : function(data, textStatus) {
			if ("success" == textStatus) {
				if (!data.errcode) {
					alert("请求出错!")
				} else if (data.errcode == 1) {
					alert("已经开始更新了!")
				}else{
					alert(data.errmsg)
				}
			}
		}
	});
	
}

//采集书籍列表
function collectBookList(thisObj){
	var $td = $(thisObj).parents('tr').children('td');
	var id = $td.eq(0).text();
	$(thisObj).attr('disabled', "true");// 添加disabled属性

	$.ajax({
		url : "collectBookList",
		type : "POST",
		data : "{id:" + id + "}",
		contentType : 'application/json',// 请求的内容类型
		success : function(data, textStatus) {
			if ("success" == textStatus) {
				if (!data.errcode) {
					alert("请求出错!")
				} else if (data.errcode == 1) {
					alert("已经开始采集了!")
				}else{
					alert(data.errmsg)
				}
			}
		}
	});
}
//采书籍信息
function collectBookInfo(thisObj){
	var $td = $(thisObj).parents('tr').children('td');
	var id = $td.eq(0).text();
	$(thisObj).attr('disabled', "true");// 添加disabled属性
	
	$.ajax({
		url : "collectBookInfo",
		type : "POST",
		data : "{id:" + id + "}",
		contentType : 'application/json',// 请求的内容类型
		success : function(data, textStatus) {
			if ("success" == textStatus) {
				if (!data.errcode) {
					alert("请求出错!")
				} else if (data.errcode == 1) {
					alert("已经开始采集了!")
				}else{
					alert(data.errmsg)
				}
			}
		}
	});
}


var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("TT_TOKEN");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8084/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到PIONEER！<a href=\"http://localhost:8082/user/logout/"+_ticket+".html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
					$("#userId").attr("value",data.data.id);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});
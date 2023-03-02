
//同意实名认证协议
$(function() {
	$("#agree").click(function(){
		var ischeck = document.getElementById("agree").checked;
		if (ischeck) {
			$("#btnRegist").attr("disabled", false);
			$("#btnRegist").removeClass("fail");
		} else {
			$("#btnRegist").attr("disabled","disabled");
			$("#btnRegist").addClass("fail");
		}
	});
});
//打开注册协议弹层
function alertBox(maskid,bosid){
	$("#"+maskid).show();
	$("#"+bosid).show();
}
//关闭注册协议弹层
function closeBox(maskid,bosid){
	$("#"+maskid).hide();
	$("#"+bosid).hide();
}

//错误提示
function showError(id,msg) {
	$("#"+id+"Ok").hide();
	$("#"+id+"Err").html("<i></i><p>"+msg+"</p>");
	$("#"+id+"Err").show();
	$("#"+id).addClass("input-red");
}
//错误隐藏
function hideError(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id).removeClass("input-red");
}
//显示成功
function showSuccess(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id+"Ok").show();
	$("#"+id).removeClass("input-red");
}

$(function () {
	//手机格式验证
	var tag_phone = 0;
	//手机号有效性验证
	$("#phone").blur(function ( ) {
		tag_phone = 0;
		var phone = $.trim($("#phone").val())
		//1.不为空
		if (null == phone || "" == phone){
			showError("phone","手机号码不能为空")
			return;
		}
		//2.长度
		if (phone.length != 11){
			showError("phone","手机号码长度有误")
			return;
		}
		//3.格式
		if (!/^1[1-9]\d{9}$/.test(phone)){
			showError("phone","手机号码格式不正确")
			return;
		}

		$.get("/005-money-web/loan/page/checkPhone",{phone:phone},function (data) {
			if (data.code == 0){
				showError("phone","手机号码已被注册");
			}else {
				tag_phone = 1;
			}
		})

		showSuccess("phone");
	});

	//姓名验证

	//身份证号验证

	//提交


})
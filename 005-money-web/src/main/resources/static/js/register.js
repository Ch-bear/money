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

//注册协议确认
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

	var tag_phone = 0;
	//手机号有效性验证
	$("#phone").blur(function ( ) {
		tag_phone = 0;
		var phone = $.trim($("#phone").val());
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

//	loginPassword

	var tag_loginPassword = 0;
	//密码有效性验证
	$("#loginPassword").blur(function ( ) {
		tag_loginPassword = 0;
		var loginPassword = $.trim($("#loginPassword").val());
		//1.不为空
		if (null == loginPassword || "" == loginPassword){
			showError("loginPassword","密码不能为空")
			return;
		}
		//2.长度
		if (loginPassword.length < 6 || loginPassword.length >20){
			showError("loginPassword","密码长度应在6-20位之间")
			return;
		}
		//3.格式
		if (!/^[0-9a-zA-Z]+$/.test(loginPassword)){
			showError("loginPassword","密码应由数字与英文字母组成")
			return;
		}

		if (!/^(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))[a-zA-Z0-9]*/.test(loginPassword)){
			showError("loginPassword","密码应同时包含英文和数字")
			return;
		}
		tag_loginPassword = 1;
		showSuccess("loginPassword")
	});

	//点击获取验证码按钮
	$("#messageCodeBtn").click(function () {
		if (tag_phone == 0) {
			showError("phone","请检查手机号码有效性");
		}
		if (tag_loginPassword == 0) {
			showError("loginPassword","请检查密码有效性");
		}
		//通过检验后获取验证码
		if ( tag_phone == 1 && tag_loginPassword == 1 ) {
			var phone = $.trim($("#phone").val());
			var _this = $(this);
			if (!$(this).hasClass("on")) {
				$.get("/005-money-web/loan/page/messageCode",{phone:phone},
					function (data) {
						alert("data:"+data.message);
						if(data.code == 1){
								$.leftTime(60, function (d) {
									alert(d.status);
									if (d.status) {
										alert("尝试倒计时："+_this);
										_this.addClass("on");
										_this.html((d.s == "00" ? "60" : d.s) + "秒后重新获取");
									} else {
										_this.removeClass("on");
										_this.html("获取验证码");
									}
								});
							}


					});
				}
		}
	});

	var tag_messageCode = 0;
	//检查验证码格式
	$("#messageCode").blur(function ( ) {
		tag_messageCode = 0;
		var messageCode = $.trim($("#messageCode").val());
		if (/^[0-9]{4}$/.test(messageCode)) {
			tag_messageCode = 1;
			showSuccess("messageCode");
		}else{
			showError("messageCode","验证码填写不规范");
		}
	});

	//点击注册按钮
	$("#btnRegist").click(function () {
		if (tag_phone == 0) {
			showError("phone","请检查手机号码有效性");
		}
		if (tag_loginPassword == 0) {
			showError("loginPassword","请检查密码有效性");
		}
		if (tag_messageCode == 0) {
			showError("messageCode","验证码填写有误");
		}
		//通过检验后提交信息
		if ( tag_phone == 1 && tag_loginPassword == 1 && tag_messageCode == 1) {
			var phone = $.trim($("#phone").val());
			var loginPassword = $.trim($("#loginPassword").val());
			var messageCode = $.trim($("#messageCode").val());
			$.post("/005-money-web/loan/page/registerSubmit",{phone:phone,loginPassword:$.md5(loginPassword),messageCode:messageCode},function (data) {
				if (data.code == 0){
					alert(data.message);
				}
				if (data.code == 1){
					window.location.href="/005-money-web/loan/page/realName";
				}
			})
		}
	});




});

package com.ch.money.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ch.money.model.User;
import com.ch.money.service.RedisService;
import com.ch.money.service.UserService;
import com.ch.money.utils.Constants;
import com.ch.money.utils.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class UserController {
    @Reference(interfaceClass = UserService.class ,version = "1.0.0" , timeout = 2000)
    UserService userService;
    @Reference(interfaceClass = RedisService.class ,version = "1.0.0" , timeout = 2000)
    RedisService redisService;

    @GetMapping("/loan/page/register")
    public String register(){
        return "register";
    }

    //查手机号是否被注册
    @GetMapping("/loan/page/checkPhone")
    @ResponseBody
    public Object checkPhone(@RequestParam(name = "phone") String phone){
        int num = userService.checkPhone(phone);
        if (num > 0){
            return Result.error("号码已被注册");
        }
        return Result.success();
    }

    //进行手机号与密码注册
    @PostMapping("/loan/page/registerSubmit")
    @ResponseBody
    public Object registerSubmit(@RequestParam(name = "phone") String phone,
                                 @RequestParam(name = "loginPassword") String loginPassword,
                                 @RequestParam(name = "messageCode") String messageCode,
                                 HttpServletRequest request){
        String code = redisService.pop(phone);
        if (!StringUtils.equals(code, messageCode)){
            return Result.error("短信验证码错误！");
        }
        User user = userService.register(phone,loginPassword);
        if (!ObjectUtils.allNotNull(user)){
            return Result.error("注册失败");
        }

        //注册成功后，处于登录状态
        request.getSession().setAttribute(Constants.LOGIN_USER, user);
        return Result.success();
    }

    //发送短信验证码
    @GetMapping("/loan/page/messageCode")
    @ResponseBody
    public Object messageCode(@RequestParam(name = "phone") String phone, HttpServletRequest request ){
        HashMap<String, String> parasMap = new HashMap<>();
        parasMap.put("appkey", "3dc4526069604afb64be7b7c205b588a");
        parasMap.put("mobile", phone);
        //生成验证码并存进redis中
        String code = Result.generateCode(4);
        redisService.push(phone,code);
        parasMap.put("content", "【创信】你的验证码是："+code+"，3分钟内有效！");

        String result = "";
        //模拟报文
        result = "{\n" +
                "    \"code\": \"10000\",\n" +
                "    \"charge\": false,\n" +
                "    \"remain\": 0,\n" +
                "    \"msg\": \"查询成功\",\n" +
                "    \"result\": {\n" +
                "        \"ReturnStatus\": \"Success\",\n" +
                "        \"Message\": \"ok\",\n" +
                "        \"RemainPoint\": 780333,\n" +
                "        \"TaskID\": 82277979,\n" +
                "        \"SuccessCounts\": 1\n" +
                "    },\n" +
                "    \"requestId\": \"373dd413db32400d917f8059dcd53be5\"\n" +
                "}";
        try {
            //真实环境
//            result = HttpClientUtils.doGet("https://way.jd.com/chuangxin/dxjk", parasMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统繁忙...");
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        String resultCode = jsonObject.getString("code");
        if (!StringUtils.equals("10000", resultCode)) {
            return Result.error("通讯异常...");
        }
        JSONObject jsonObjectString = jsonObject.getJSONObject("result");
        String returnStatus = jsonObjectString.getString("ReturnStatus");
        if (!StringUtils.equals("Success", returnStatus)) {
            return Result.error("短信发送失败...");
        }
        return  Result.success(code);
    }

    //跳转至实名认证页面
    @GetMapping("/loan/page/realName")
    public String realName(){
        return "realName";
    }

    //进行实名认证
    @PostMapping("/loan/page/realNameSubmit")
    @ResponseBody
    public Object realNameSubmit(@RequestParam(name = "phone")String phone , @RequestParam(name = "realName")String realName,
                                 @RequestParam(name = "idCard")String idCard,@RequestParam(name = "messageCode")String messageCode,
                                 HttpServletRequest request){
        String code = redisService.pop(phone);
        //验证码不通过
        if (!StringUtils.equals(code, messageCode)){
            return Result.error("验证码有误，请检查验证码");
        }
        //api验证身份证号码是否存在
//        if (false){
//            return Result.error("身份证有误，请检查身份证号");
//        }

        return "login";
    }


    @GetMapping("/loan/page/login")
    public String login(String ReturnUrl, Model model){
        model.addAttribute("ReturnUrl", ReturnUrl);
        return "login";
    }

    @PostMapping("/loan/page/userLogin")
    @ResponseBody
    public Object userLogin(@RequestParam(name = "phone")String phone ,
                            @RequestParam(name = "loginPassword")String loginPassword,
                            HttpServletRequest request){
        //查询用户是否存在
        User user = userService.login(phone,loginPassword);
        if (!ObjectUtils.allNotNull(user)){
            return Result.error("用户名或密码错误");
        }
        request.getSession().setAttribute(Constants.LOGIN_USER, user);
        return Result.success();
    }

    //登陆后：进入小金库
    @GetMapping("/loan/page/myCenter")
    public String myCenter(){
        return "myCenter";
    }

    //登录后：登出
    @GetMapping("/loan/page/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/index";
    }

}

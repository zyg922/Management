package com.example.management.app.controller;


import com.example.management.entity.LoginParam;
import com.example.management.entity.LoginResponse;
import com.example.management.entity.ResultBody;
import com.example.management.service.LoginService;
import com.example.management.util.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    final
    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("check")
    public LoginResponse login(@Valid @RequestBody LoginParam param, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            LoginResponse loginResponse=new LoginResponse(1,0,0);
            loginResponse.setErrorMessage(3);
            return loginResponse;
        }
        return loginService.loginCheck(request, param);
    }

/*
    //为什么类表直接获得clubList？？不应该按角色来获取的吗？
    @GetMapping("/login/getList")
    public ResultBody getList(){
        return ResultBody.success(loginService.getList());
    }
*/

    //为什么类表直接获得clubList？？不应该按角色来获取的吗？
    @GetMapping("getList")
    public ResultBody getList( @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                               @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize){
        return ResultBody.success(loginService.getListByRole(pageNum,pageSize));
    }

    @GetMapping("getRole")
    public LoginResponse getRole(HttpServletRequest request){
        return loginService.getRole(request);
    }

    @GetMapping("logout")
    public ResultBody logout(HttpServletRequest request){
        String msg="退出失败";
        try {
            request.getSession().removeAttribute(ConstantUtils.USER_NAME);
            request.getSession().removeAttribute(ConstantUtils.USER_ROLE);
            msg="退出成功";
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultBody.state(msg);
    }



}
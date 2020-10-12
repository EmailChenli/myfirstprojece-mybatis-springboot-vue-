package com.eastcom.ripple.controller;


import com.eastcom.ripple.common.util.Encrypt;
import com.eastcom.ripple.entity.SysUser;
import com.eastcom.ripple.serviceinter.SysUserService;
import com.eastcom.ripple.util.BCrypt;
import com.eastcom.ripple.util.DataFormat;
import com.eastcom.ripple.util.PasswordUtil;
import com.eastcom.ripple.util.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/login")
@Slf4j
public class LoginController {
    @Autowired
    private SysUserService sysUserService;


    @PostMapping("sysuser")
    public ResultVO<DataFormat> userLogin(@RequestBody SysUser sysUser){
        //判断对象是否存在
        //判断密码是否正确
        //判断是否登录
        log.info("登录："+sysUser);
        try{
            ResultVO<DataFormat> resultVO = new ResultVO<>();
            //从数据库中查出账号信息
            SysUser userFromSql = sysUserService.findSysUser(sysUser);
            if(userFromSql == null){
                resultVO.setCode(200);
                resultVO.setMsg("没有该账号");
            }else{
               //验证账号密码是否正确
               boolean check = BCrypt.checkpw(sysUser.getSysuserPassword(),userFromSql.getSysuserPassword());
               if(check){
                   //创建生成token的对象
                   Encrypt encrypt = new Encrypt();
                   //将员工id传入，生成token
                   String token = encrypt.getToken(userFromSql.getSysuserId());
                   resultVO.setCode(200);
                   resultVO.setMsg("登录成功");
                   DataFormat<String,SysUser> dataFormat = new DataFormat<>();
                   dataFormat.setData(sysUser);
                   dataFormat.setToken(token);
                   resultVO.setData(dataFormat);
               }else{
                   resultVO.setCode(200);
                   resultVO.setMsg("密码错误");
               }
            }
            return resultVO;
        }catch (Exception e){
            ResultVO<DataFormat> resultVO = new ResultVO<>();
            resultVO.setCode(444);
            resultVO.setMsg("登录失败");
            return resultVO;
        }
    }

    @PostMapping("sysUserInfo")
    public ResultVO<SysUser> userInfo(@RequestBody String sysUserName) {
        log.info("个人信息："+sysUserName.substring(0,sysUserName.length()-1));
        try{
            ResultVO<SysUser> resultVO = new ResultVO<>();
            resultVO.setCode(200);
            resultVO.setMsg("获取成功");
            resultVO.setData(sysUserService.findSysUserInfo(sysUserName.substring(0,sysUserName.length()-1)));
            return resultVO;
        }catch (Exception e){
            ResultVO<SysUser> resultVO = new ResultVO<>();
            resultVO.setCode(444);
            resultVO.setMsg("获取失败");
            return resultVO;
        }
    }

    @PostMapping("changePassword")
    public ResultVO<Integer> passwordChange(@RequestBody PasswordUtil passwordUtil) {
        log.info("个人信息："+passwordUtil);
        try{
            SysUser sysUser = new SysUser();
            sysUser.setSysuserAccount(passwordUtil.getAccountName());
            SysUser sysUserCheck = sysUserService.findSysUser(sysUser);
            boolean check = BCrypt.checkpw(passwordUtil.getOldPassword(),sysUserCheck.getSysuserPassword());

            if(check){
                ResultVO<Integer> resultVO = new ResultVO<>();
                resultVO.setCode(200);
                resultVO.setMsg("修改成功，请重新登录");
                passwordUtil.setOldPassword(sysUserCheck.getSysuserPassword());
                passwordUtil.setNewPassword(BCrypt.hashpw(passwordUtil.getNewPassword(),BCrypt.gensalt(12)));
                resultVO.setData(sysUserService.updatePassword(passwordUtil));
                return resultVO;
            }else {
                ResultVO<Integer> resultVO = new ResultVO<>();
                resultVO.setCode(444);
                resultVO.setMsg("修改失败,密码错误");
                return resultVO;
            }

        }catch (Exception e){
            ResultVO<Integer> resultVO = new ResultVO<>();
            resultVO.setCode(444);
            resultVO.setMsg("修改失败");
            return resultVO;
        }
    }
}

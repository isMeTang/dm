package cn.tang.dm.admin;

import cn.tang.dm.domian.admin.Account;
import cn.tang.dm.domian.common.Result;
import cn.tang.dm.service.admin.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author tangzeqian
 * @create 2016-02-15  16:44
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    IAccountService accountService;

    @ResponseBody
    @RequestMapping("/login")
    public Result login(HttpServletRequest request,@RequestParam(required = true) String userName, @RequestParam(required = true) String passWord){
        HttpSession session = request.getSession(true);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name",userName);
        map.put("passWord",passWord);
        Account account = accountService.queryOne(map);
        if(account == null){
            return new Result("error",0,"用户名或密码错误");
        }
        if(account.getState() == 0){
            return new Result("error",0,"账号被禁用");
        }
        //创建用户session
        session.setAttribute("USERINFO", account.getPassWord());
        return new Result("success",1,account.getId());
    }

    @ResponseBody
    @RequestMapping("/quit")
    public Result quit(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        //删除用户session
        session.removeAttribute("USERINFO");
        return new Result("success",1,null);
    }
}

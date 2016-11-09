package cn.tang.dm.admin;

import cn.tang.dm.common.Tools;
import cn.tang.dm.domian.admin.Account;
import cn.tang.dm.domian.common.Result;
import cn.tang.dm.service.admin.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号控制器
 *
 * @author tangzeqian
 * @create 2016-03-07  14:20
 */
@Controller
@RequestMapping("/dm/account")
public class AccountController {
    @Autowired
    IAccountService accountService;

    /**
     * 查询总记录数
     * @return
     */
    @ResponseBody
    @RequestMapping("/count")
    public Result count(){
        Map<String,Object> map = new HashMap<String, Object>();
        long count = accountService.count(map);
        return new Result("success",1,count);
    }


    /**
     * 分页查询所有账号信息
     * @param pageNo 页数
     * @return
     */
    @ResponseBody
    @RequestMapping("/userList")
    public Result userList(@RequestParam(defaultValue = "1") int pageNo){
        Map<String,Object> map = new HashMap<String, Object>();
        long begin = (pageNo - 1) * 10;
        long lim = 10;
        List<Account> list = accountService.queryPage(map,"id","ASC",begin,lim);
        return new Result("success",1,list);
    }

    /**
     * 查询账号信息
     * @param id 用户id
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryUser")
    public Result queryUser(long id){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        Account account = accountService.queryOne(map);
        return new Result("success",1,account);
    }

    /**
     * 增加账号
     * @param account 参数
     * @return
     */
    @ResponseBody
    @RequestMapping("/addAccount")
    public Result addAccount(Account account){
        account.setDate(new Date());
        int result = accountService.add(account);
        if(result == 1){
            return new Result("success",1,null);
        } else{
            return new Result("error",0,null);
        }
    }

    /**
     * 修改账号
     * @param account 参数
     * @return
     */
    @ResponseBody
    @RequestMapping("/editAccount")
    public Result editAccount(Account account){
        Map<String,Object> map = Tools.toMap(account);
        int result = accountService.updateByMap(map);
        if(result == 1){
            return new Result("success",1,null);
        } else{
            return new Result("error",0,null);
        }
    }

    /**
     * 删除账号
     * @param id 参数
     * @return
     */
    @ResponseBody
    @RequestMapping("/delAccount")
    public Result delAccount(long id){
        int result = accountService.del(id);
        if(result == 1){
            return new Result("success",1,null);
        } else{
            return new Result("error",0,null);
        }
    }

    @ResponseBody
    @RequestMapping("/find")
    public Result find(String key){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name",key);
        List<Account> list =accountService.find(map);
        if(list != null && list.size() > 0){
            return new Result("success",1,list);
        } else{
            return new Result("error",0,null);
        }
    }
}

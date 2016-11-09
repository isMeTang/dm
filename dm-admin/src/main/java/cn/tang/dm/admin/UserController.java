package cn.tang.dm.admin;

import cn.tang.dm.domian.admin.Account;
import cn.tang.dm.domian.admin.Menu;
import cn.tang.dm.domian.admin.RoleMenu;
import cn.tang.dm.domian.common.Result;
import cn.tang.dm.domian.result.MenuResult;
import cn.tang.dm.service.admin.IAccountService;
import cn.tang.dm.service.admin.IMenuService;
import cn.tang.dm.service.admin.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 *
 * @author tangzeqian
 * @create 2016-03-13  11:07
 */
@Controller
@RequestMapping("/dm/user")
public class UserController {
    @Autowired
    IAccountService accountService;
    @Autowired
    IRoleMenuService roleMenuService;
    @Autowired
    IMenuService menuService;
    @ResponseBody
    @RequestMapping("/userInfo")
    public Result userInfo(@RequestParam(required = true) Long userId){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",userId);
        Account account = accountService.queryOne(map);
        if(account == null){
            return new Result("error",0,null);
        }
        return new Result("success",1,account);
    }

    @ResponseBody
    @RequestMapping("/menu")
    public Result menu(@RequestParam(required = true) Long roleId){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("roleId",roleId);
        List<RoleMenu> list = roleMenuService.queryList(map,"id","ASC");
        List<MenuResult> menuResults = new ArrayList<MenuResult>();
        List<Menu> mainMenu = new ArrayList<Menu>();
        List<Menu> subMenu = new ArrayList<Menu>();
        if(list != null && list.size() > 0){
            for(RoleMenu item : list){
                map.clear();
                map.put("id",item.getMenuId());
                map.put("state",1);
                Menu menu = menuService.queryOne(map);
                if(menu != null){
                    if(menu.getParentId() == -1){
                        mainMenu.add(menu);
                    } else{
                        subMenu.add(menu);
                    }
                }
            }
            for(Menu item : mainMenu){
                MenuResult menuResult = new MenuResult(item,null);
                List<Menu> temp = new ArrayList<Menu>();
                for(Menu sub : subMenu){
                    if(item.getId() == sub.getParentId()){
                        temp.add(sub);
                    }
                }
                menuResult.setSubMenu(temp);
                menuResults.add(menuResult);
            }
            return new Result("success",1,menuResults);
        }
        return new Result("error",0,null);
    }

}

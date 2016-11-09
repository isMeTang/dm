package cn.tang.dm.admin;

import cn.tang.dm.common.Tools;
import cn.tang.dm.domian.admin.Menu;
import cn.tang.dm.domian.admin.RoleMenu;
import cn.tang.dm.domian.common.Result;
import cn.tang.dm.service.admin.IMenuService;
import cn.tang.dm.service.admin.IRoleMenuService;
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
 * 菜单控制器
 *
 * @author tangzeqian
 * @create 2016-03-07  14:20
 */
@Controller
@RequestMapping("/dm/menu")
public class MenuController {
    @Autowired
    IMenuService menuService;
    @Autowired
    IRoleMenuService roleMenuService;

    /**
     * 查询总记录数
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/count")
    public Result count() {
        Map<String, Object> map = new HashMap<String, Object>();
        long count = menuService.count(map);
        return new Result("success", 1, count);
    }

    /**
     * 查询所有一级菜单
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/topMenu")
    public Result topMenu() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentId", -1);
        List<Menu> list = menuService.queryList(map, "id", "ASC");
        if (list != null && list.size() > 0) {
            return new Result("success", 1, list);
        } else {
            return new Result("error", 0, null);
        }
    }

    /**
     * 分页查询所有菜单信息
     *
     * @param pageNo 页数
     * @return
     */
    @ResponseBody
    @RequestMapping("/menuList")
    public Result menuList(@RequestParam(defaultValue = "1") int pageNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        long begin = (pageNo - 1) * 10;
        long lim = 10;
        List<Menu> list = menuService.queryPage(map, "id", "ASC", begin, lim);
        return new Result("success", 1, list);
    }

    /**
     * 查询菜单信息
     *
     * @param id 用户id
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryMenu")
    public Result queryMenu(long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        Menu menu = menuService.queryOne(map);
        return new Result("success", 1, menu);
    }

    /**
     * 增加账号
     *
     * @param menu 参数
     * @return
     */
    @ResponseBody
    @RequestMapping("/addMenu")
    public Result addMenu(Menu menu) {
        menu.setDate(new Date());
        int result = menuService.add(menu);
        if (result == 1) {
            return new Result("success", 1, null);
        } else {
            return new Result("error", 0, null);
        }
    }

    /**
     * 修改菜单
     *
     * @param menu 参数
     * @return
     */
    @ResponseBody
    @RequestMapping("/editMenu")
    public Result editMenu(Menu menu) {
        Map<String, Object> map = Tools.toMap(menu);
        int result = menuService.updateByMap(map);
        if (result == 1) {
            return new Result("success", 1, null);
        } else {
            return new Result("error", 0, null);
        }
    }

    /**
     * 删除菜单
     *
     * @param id 参数
     * @return
     */
    @ResponseBody
    @RequestMapping("/delMenu")
    public Result delMenu(long id) {
        int result = menuService.del(id);
        if (result == 1) {
            return new Result("success", 1, null);
        } else {
            return new Result("error", 0, null);
        }
    }

    /**
     * 查找菜单
     *
     * @param key 关键词
     * @return
     */
    @ResponseBody
    @RequestMapping("/find")
    public Result find(String key) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", key);
        List<Menu> list = menuService.find(map);
        if (list != null && list.size() > 0) {
            return new Result("success", 1, list);
        } else {
            return new Result("error", 0, null);
        }
    }

    @ResponseBody
    @RequestMapping("/getRoles")
    public Result getRoles(long menuId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("menuId", menuId);
        List<RoleMenu> list = roleMenuService.queryList(map, "id", "ASC");
        if (list != null && list.size() > 0) {
            return new Result("success", 1, list);
        } else {
            return new Result("error", 0, null);
        }
    }

    @ResponseBody
    @RequestMapping("/setRole")
    public Result setRole(long menuId, String roleIds) {
        String[] str = null;
        if(roleIds != null && !"".equals(roleIds)){
           str  = roleIds.split(",");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("menuId", menuId);
        List<RoleMenu> list = roleMenuService.queryList(map, "id", "ASC");
        if (list != null && list.size() > 0) {
            for (RoleMenu item : list) {
                roleMenuService.del(item.getId());
            }
        }
        if (str != null && str.length > 0) {
            for (String s : str) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setDate(new Date());
                roleMenu.setRoleId(Long.parseLong(s));
                roleMenu.setMenuId(menuId);
                roleMenuService.add(roleMenu);
            }
            return new Result("success", 1, null);
        } else {
            return new Result("error", 0, null);
        }
    }
}


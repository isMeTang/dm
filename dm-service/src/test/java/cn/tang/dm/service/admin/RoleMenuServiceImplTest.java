package cn.tang.dm.service.admin;

import cn.tang.dm.domian.admin.Role;
import cn.tang.dm.domian.admin.RoleMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色菜单测试类
 *
 * @author tangzeqian
 * @create 2016-02-27  12:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dm-service-test.xml")
public class RoleMenuServiceImplTest {
    @Autowired
    IRoleMenuService roleMenuService;

    @Test
    public void testAdd() {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenuId(33);
        roleMenu.setRoleId(11);
        roleMenuService.add(roleMenu);
    }

    @Test
    public void testDel() {
        long id = 14;
        roleMenuService.del(id);
    }

    @Test
    public void testUpdateByMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 15);
        map.put("roleId", 568);
        map.put("menuId", 123);
        roleMenuService.updateByMap(map);
    }

    @Test
    public void testQueryOne() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", 2);
        RoleMenu roleMenu = roleMenuService.queryOne(map);
        System.out.println(roleMenu.toString());
    }

    @Test
    public void testQueryList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("menuId", 1);
        List<RoleMenu> list = roleMenuService.queryList(map, "id", "asc");
        for (RoleMenu item : list) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void testQueryPage() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", 2);
        List<RoleMenu> list = roleMenuService.queryPage(map, "id", "asc",1l,2l);
        for (RoleMenu item : list) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void testCount(){
        Map<String,Object> map = new HashMap<String, Object>();
        System.out.println(roleMenuService.count(map));
    }

    @Test
    public void testfind(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name","1");
        List<RoleMenu> list = roleMenuService.find(map);
        for(RoleMenu item : list){
            System.out.println(item.toString());
        }
    }
}
package cn.tang.dm.service.admin;

import cn.tang.dm.domian.admin.Menu;
import cn.tang.dm.domian.admin.Role;
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
 * 系统角色测试类
 *
 * @author tangzeqian
 * @create 2016-02-27  12:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dm-service-test.xml")
public class RoleServiceImplTest {
    @Autowired
    IRoleService roleService;

    @Test
    public void testAdd() {
        Role role = new Role();
        role.setName("测试新add");
        role.setDate(new Date());
        roleService.add(role);
    }

    @Test
    public void testDel() {
        long id = 5;
        roleService.del(id);
    }

    @Test
    public void testUpdateByMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 4);
        map.put("name", "updaceshi");
        map.put("date", null);
        map.put("state", 3);
        roleService.updateByMap(map);
    }

    @Test
    public void testQueryOne() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        Role role = roleService.queryOne(map);
        System.out.println(role.toString());
    }

    @Test
    public void testQueryList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", 1);
        List<Role> list = roleService.queryList(map, "id", "asc");
        for (Role item : list) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void testQueryPage() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", 1);
        map.put("name", "管理员");
        List<Role> list = roleService.queryPage(map, "id", "asc",0l,2l);
        for (Role item : list) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void testCount(){
        Map<String,Object> map = new HashMap<String, Object>();
        System.out.println(roleService.count(map));
    }

    @Test
    public void testfind(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name","分析");
        List<Role> list = roleService.find(map);
        for(Role item : list){
            System.out.println(item.toString());
        }
    }
}
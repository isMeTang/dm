package cn.tang.dm.service.admin;

import cn.tang.dm.domian.admin.Account;
import cn.tang.dm.domian.admin.Menu;
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
 * 系统菜单测试类
 *
 * @author tangzeqian
 * @create 2016-02-27  12:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dm-service-test.xml")
public class MenuServiceImplTest {
    @Autowired
    IMenuService menuService;

    @Test
    public void testAdd(){
        Menu menu = new Menu();
        menu.setState(0);
        menu.setName("测试菜单名字");
        menu.setParentId(3);
        menu.setUrl("http://156454s4s46s46546.com/shoush");
        menuService.add(menu);
    }

    @Test
    public void testDel(){
        long id = 10;
        menuService.del(id);
    }

    @Test
    public void testUpdateByMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",11);
        map.put("name","updaceshi爱上的浪费就卡死");
        map.put("url","updateUpr://hadlsf/adsfhlk");
        map.put("date",new Date());
        map.put("state",3);
        map.put("parentId",11);
        menuService.updateByMap(map);
    }

    @Test
    public void testQueryOne(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("state",1);
        map.put("url",0);
        Menu menu = menuService.queryOne(map);
        System.out.println(menu);
    }

    @Test
    public void testQueryList(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("state",1);
        map.put("url",0);
        List<Menu> list = menuService.queryList(map,"id","asc");
        for(Menu item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testQueryPage(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("state",1);
        List<Menu> list = menuService.queryPage(map,"id","asc",1l,9l);
        for(Menu item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testCount(){
        Map<String,Object> map = new HashMap<String, Object>();
        System.out.println(menuService.count(map));
    }

    @Test
    public void testfindAccout(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name","分析");
        List<Menu> list = menuService.find(map);
        for(Menu item : list){
            System.out.println(item.toString());
        }
    }
}
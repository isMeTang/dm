package cn.tang.dm.service.admin;

import cn.tang.dm.domian.admin.Account;
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
 * 账号服务测试类
 *
 * @author tangzeqian
 * @create 2016-02-27  12:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dm-service-test.xml")
public class AccountServiceImplTest {
    @Autowired
    IAccountService accountService;

    @Test
    public void testGetAllAccount(){
        List<Account> list = accountService.getAllAccount();
        for(Account item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testAdd(){
        Account account = new Account();
        account.setName("测试add");
        account.setPassWord("123123");
        account.setSex("女");
        account.setRoleId(3);
        account.setState(1);
        accountService.add(account);
    }

    @Test
    public void testDel(){
        long id = 4;
        accountService.del(id);
    }

    @Test
    public void testUpdateByMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",74);
        map.put("name","张三update");
        map.put("passWord","123123update");
        map.put("email","123123update@qq.com");
        map.put("roleId",2);
        map.put("sex","男");
        map.put("phone",13631157951l);
        map.put("state",0);
        map.put("date",new Date());
        System.out.println(accountService.updateByMap(map));
    }

    @Test
    public void testQueryOne(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name","admin");
        map.put("passWord","admin");
        Account account = accountService.queryOne(map);
        System.out.println(account);
    }

    @Test
    public void testQueryList(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name","admin");
        List<Account> list = accountService.queryList(map,"id","asc");
        for(Account item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testQueryPage(){
        Map<String,Object> map = new HashMap<String, Object>();
        List<Account> list = accountService.queryPage(map,"id","asc",1l,2l);
        for(Account item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testCount(){
        Map<String,Object> map = new HashMap<String, Object>();
        System.out.println(accountService.count(map));
    }

    @Test
    public void testfindAccout(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name","1");
        List<Account> list = accountService.find(map);
        for(Account item : list){
            System.out.println(item.toString());
        }
    }
}
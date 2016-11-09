package cn.tang.dm.service.analyze;

import cn.tang.dm.dao.analyze.IRegisterDao;
import cn.tang.dm.domian.analyze.Active;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.domian.common.KPIConst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注册明细表测试类
 *
 * @author tangzeqian
 * @create 2016-02-27  12:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dm-service-test.xml")
public class RegisterServiceImplTest {
    @Autowired
    IRegisterService registerService;
    @Test
    public void testAdd() {
        Register register = new Register();
        register.setAge(35);
        register.setSex("1");
        register.setDate(20160222);
        register.setProvinceId(17);
        register.setTypeId(100);
        registerService.add(register);
    }


    @Test
    public void testQueryOne() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("kpi", KPIConst.DM_ANALYZE_REGISTER_DAY);
        map.put("startDate",20160201);
        Register register = registerService.queryOne(map);
        System.out.println(register.toString());

    }

    @Test
    public void testQueryList() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("kpi", KPIConst.DM_ANALYZE_REGISTER_DAY);
        map.put("startDate",20160201);
        List<Register> list = registerService.queryList(map,"id","ASC");
        for(Register item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testQueryPage() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("kpi", KPIConst.DM_ANALYZE_REGISTER_DAY);
        map.put("startDate",20160101);
        map.put("endDate",20160103);
        List<Register> list = registerService.queryPage(map,"id","ASC",0l,6l);
        for(Register item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testCount(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("kpi", KPIConst.DM_ANALYZE_REGISTER_DAY);
        map.put("startDate",20160101);
        map.put("endDate",20160103);
        System.out.println(registerService.count(map));
        map.put("sex",1);
        System.out.println(registerService.count(map));
        map.put("provinceId",15);
        System.out.println(registerService.count(map));
    }

    @Test
    public void testAllYear(){
        System.out.println(registerService.queryAllYear());
    }

    @Test
    public void testSumForMonth(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startDate",201601);
        System.out.println(registerService.querySumForMonth(map));
    }

}
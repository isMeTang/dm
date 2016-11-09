package cn.tang.dm.service.analyze;

import cn.tang.dm.domian.analyze.Active;
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
 * 活跃汇总表测试类
 *
 * @author tangzeqian
 * @create 2016-02-27  12:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dm-service-test.xml")
public class ActiveServiceImplTest {
    @Autowired
    IActiveService activeService;
    @Test
    public void testAdd() {
        Active active = new Active();
        active.setDate(20160205);
        active.setProvinceId(8);
        active.setValue(4778);
        activeService.add(active);
    }


    @Test
    public void testQueryOne() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("kpi","DM_001_001");
        map.put("provinceId",8);
        Active active = activeService.queryOne(map);
        System.out.println(active.toString());
        map.clear();
        map.put("provniceId",15);
        map.put("date","20160103");
        active = activeService.queryOne(map);
        System.out.println(active.toString());

    }

    @Test
    public void testQueryList() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("kpi","DM_001_001");
        /*map.put("startDate",20160104);
        map.put("endDate",20160105);*/
        List <Active> list = activeService.queryList(map,"value","DESC");
        for(Active item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testQueryPage() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("kpi","DM_001_001");
        map.put("startDate",20160104);
        map.put("endDate",20160105);
        List <Active> list = activeService.queryPage(map,"id","ASC",1l,10l);
        for(Active item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testCount(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("kpi","DM_001_001");
        System.out.println(activeService.count(map));
        map.put("provinceId",15);
        System.out.println(activeService.count(map));
        map.put("startDate",20160104);
        map.put("endDate",20160105);
        System.out.println(activeService.count(map));
    }

    @Test
    public void testAllYear(){
        System.out.println(activeService.queryAllYear());
    }

}
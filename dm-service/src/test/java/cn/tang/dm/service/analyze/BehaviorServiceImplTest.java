package cn.tang.dm.service.analyze;

import cn.tang.dm.domian.analyze.Behavior;
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
 * 行为明细表测试类
 *
 * @author tangzeqian
 * @create 2016-02-27  12:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dm-service-test.xml")
public class BehaviorServiceImplTest {
    @Autowired
    IBehaviorService behaviorService;
    @Test
    public void testAdd() {
        Behavior behavior = new Behavior();
        behavior.setDate(20160301);
        behavior.setIntoType(1);
        behavior.setIsUser(1);
        behavior.setOs("ios");
        behavior.setTime("1");
        behaviorService.add(behavior);
    }

    @Test
    public void testQueryList() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("date",20160101);
        List<Behavior> list = behaviorService.queryList(map,"id","ASC");
        for(Behavior item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testQueryPage() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startDate",20160101);
        map.put("endDate",20160103);
        List<Behavior> list = behaviorService.queryPage(map,"id","ASC",1l,6l);
        for(Behavior item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testCount(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startDate",20160101);
        map.put("endDate",20160103);
        System.out.println(behaviorService.count(map));
        map.put("os","ios");
        System.out.println(behaviorService.count(map));
        map.put("isUser",1);
        System.out.println(behaviorService.count(map));
    }

}
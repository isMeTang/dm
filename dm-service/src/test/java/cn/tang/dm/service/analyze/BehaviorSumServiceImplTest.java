package cn.tang.dm.service.analyze;

import cn.tang.dm.domian.analyze.BehaviorSum;
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
 * 行为汇总表测试类
 *
 * @author tangzeqian
 * @create 2016-02-27  12:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dm-service-test.xml")
public class BehaviorSumServiceImplTest {
    @Autowired
    IBehaviorSumService behaviorSumService;
    @Test
    public void testAdd() {
        BehaviorSum behaviorSum = new BehaviorSum();
        behaviorSum.setDate(201609);
        behaviorSum.setTime1("1688.1");
        behaviorSum.setTime2("2147");
        behaviorSum.setTime3("3011");
        behaviorSum.setTime4("4444.5");
        behaviorSumService.add(behaviorSum);
    }

    @Test
    public void testQueryOne() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startDate",201602);
        map.put("endDate",201605);
        BehaviorSum behaviorSum = behaviorSumService.queryOne(map);
        System.out.println(behaviorSum);
    }

    @Test
    public void testQueryList() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startDate",201602);
        map.put("endDate",201605);
        List<BehaviorSum> list = behaviorSumService.queryList(map,"id","ASC");
        for(BehaviorSum item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testQueryPage() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startDate",201603);
        map.put("endDate",201610);
        List<BehaviorSum> list = behaviorSumService.queryPage(map,"id","ASC",0l,6l);
        for(BehaviorSum item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testCount(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startDate",201601);
        map.put("endDate",201606);
        System.out.println(behaviorSumService.count(map));
    }

    @Test
    public void testAllYear(){
        System.out.println(behaviorSumService.queryAllYear());
    }

}
package cn.tang.dm.service.analyze;

import cn.tang.dm.domian.analyze.Flow;
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
 * 流量统计测试类
 *
 * @author tangzeqian
 * @create 2016-02-27  12:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dm-service-test.xml")
public class FlowServiceImplTest {
    @Autowired
    IFlowService flowService;
    @Test
    public void testAdd() {
        Flow flow = new Flow();
        flow.setDate(20160104);
        flow.setProvinceId(6);
        flow.setPageName("B");
        flow.setValue("214587963");
        flowService.add(flow);
    }

    @Test
    public void testQueryList() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startDate",20160101);
        map.put("endDate",20160101);
        List<Flow> list = flowService.queryList(map,"id","ASC");
        for(Flow item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testQueryPage() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("kpi", KPIConst.DM_ANALYZE_REGISTER_DAY);
        map.put("startDate",20160101);
        map.put("endDate",20160103);
    }

    @Test
    public void testCount(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("startDate",20160101);
        map.put("endDate",20160103);
        System.out.println(flowService.count(map));
        map.put("provinceId",1);
        System.out.println(flowService.count(map));
        map.put("pageName","A");
        System.out.println(flowService.count(map));
    }

    @Test
    public void testAllYear(){
        System.out.println(flowService.queryAllYear());
    }
}
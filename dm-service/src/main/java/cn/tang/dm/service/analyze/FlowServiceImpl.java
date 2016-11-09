package cn.tang.dm.service.analyze;

import cn.tang.dm.dao.analyze.IFlowDao;
import cn.tang.dm.dao.analyze.IRegisterDao;
import cn.tang.dm.domian.analyze.Flow;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.domian.common.KPIConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 流量统计服务类
 *
 * @author tangzeqian
 * @create 2016-03-14  21:38
 */
@Service("FlowServiceImpl")
public class FlowServiceImpl implements IFlowService {
    @Autowired
    private IFlowDao flowDao;


    @Override
    public List<Long> queryAllYear() {
        return flowDao.queryAllYear(KPIConst.DM_ANALYZE_FLOW_SUM_YEAR);
    }

    @Override
    public int add(Flow flow) {
        flow.setKpi(KPIConst.DM_ANALYZE_FLOW_SUM_DAY);
        return flowDao.insertOne(flow);
    }

    @Override
    public int addList(List<Flow> list) {
        return 0;
    }

    @Override
    public int del(Long id) {
        return 0;
    }

    @Override
    public int delList(List<Long> ids) {
        return 0;
    }

    @Override
    public int delByMap(Map<String, Object> condition) {
        return 0;
    }

    @Override
    public int update(Flow entity) {
        return 0;
    }

    @Override
    public int updateList(List<Flow> list) {
        return 0;
    }

    @Override
    public int updateByMap(Map<String, Object> condition) {
        return 0;
    }

    @Override
    public Flow queryOne(Map<String, Object> condition) {
        return null;
    }

    @Override
    public List<Flow> queryList(Map<String, Object> condition, String orderBy, String sortBy) {
        condition.put("kpi",KPIConst.DM_ANALYZE_FLOW_SUM_DAY);
        return flowDao.queryList(condition,orderBy,sortBy);
    }

    @Override
    public List<Flow> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end) {
        condition.put("kpi",KPIConst.DM_ANALYZE_FLOW_SUM_DAY);
        return flowDao.queryPage(condition,begin.intValue(),end.intValue(),orderBy,sortBy);
    }

    @Override
    public Long count(Map<String, Object> condition) {
        condition.put("kpi",KPIConst.DM_ANALYZE_FLOW_SUM_DAY);
        return flowDao.count(condition);
    }

    @Override
    public List<Flow> find(Map<String, Object> condition) {
        return null;
    }
}

package cn.tang.dm.service.analyze;

import cn.tang.dm.dao.analyze.IBehaviorDao;
import cn.tang.dm.dao.analyze.IRegisterDao;
import cn.tang.dm.domian.analyze.Behavior;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.domian.common.KPIConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 行为明细表服务类
 *
 * @author tangzeqian
 * @create 2016-03-14  21:38
 */
@Service("BehaviorServiceImpl")
public class BehaviorServiceImpl implements IBehaviorService {
    @Autowired
    private IBehaviorDao behaviorDao;


    @Override
    public int add(Behavior behavior) {
        behavior.setKpi(KPIConst.DM_ANALYZE_BEHAVIOR_DETAIL);
        return behaviorDao.insertOne(behavior);
    }

    @Override
    public int addList(List<Behavior> list) {
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
    public int update(Behavior entity) {
        return 0;
    }

    @Override
    public int updateList(List<Behavior> list) {
        return 0;
    }

    @Override
    public int updateByMap(Map<String, Object> condition) {
        return 0;
    }

    @Override
    public Behavior queryOne(Map<String, Object> condition) {
        return null;
    }

    @Override
    public List<Behavior> queryList(Map<String, Object> condition, String orderBy, String sortBy) {
        condition.put("kpi",KPIConst.DM_ANALYZE_BEHAVIOR_DETAIL);
        return behaviorDao.queryList(condition,orderBy,sortBy);
    }

    @Override
    public List<Behavior> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end) {
        condition.put("kpi",KPIConst.DM_ANALYZE_BEHAVIOR_DETAIL);
        return behaviorDao.queryPage(condition,begin.intValue(),end.intValue(),orderBy,sortBy);
    }

    @Override
    public Long count(Map<String, Object> condition) {
        condition.put("kpi",KPIConst.DM_ANALYZE_BEHAVIOR_DETAIL);
        return behaviorDao.count(condition);
    }

    @Override
    public List<Behavior> find(Map<String, Object> condition) {
        return null;
    }
}

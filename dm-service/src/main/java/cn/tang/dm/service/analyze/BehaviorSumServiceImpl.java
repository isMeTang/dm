package cn.tang.dm.service.analyze;

import cn.tang.dm.dao.analyze.IBehaviorSumDao;
import cn.tang.dm.dao.analyze.IRegisterDao;
import cn.tang.dm.domian.analyze.BehaviorSum;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.domian.common.KPIConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 行为汇总表服务类
 *
 * @author tangzeqian
 * @create 2016-03-14  21:38
 */
@Service("BehaviorSumServiceImpl")
public class BehaviorSumServiceImpl implements IBehaviorSumService {
    @Autowired
    private IBehaviorSumDao behaviorSumDao;


    @Override
    public List<Long> queryAllYear() {
        return behaviorSumDao.queryAllYear(KPIConst.DM_ANALYZE_BEHAVIOR_SUM_YEAR);
    }

    @Override
    public int add(BehaviorSum behaviorSum) {
        behaviorSum.setKpi(KPIConst.DM_ANALYZE_BEHAVIOR_SUM_MONTH);
        return behaviorSumDao.insertOne(behaviorSum);
    }

    @Override
    public int addList(List<BehaviorSum> list) {
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
    public int update(BehaviorSum entity) {
        return 0;
    }

    @Override
    public int updateList(List<BehaviorSum> list) {
        return 0;
    }

    @Override
    public int updateByMap(Map<String, Object> condition) {
        return 0;
    }

    @Override
    public BehaviorSum queryOne(Map<String, Object> condition) {
        condition.put("kpi",KPIConst.DM_ANALYZE_BEHAVIOR_SUM_MONTH);
        return behaviorSumDao.queryOne(condition);
    }

    @Override
    public List<BehaviorSum> queryList(Map<String, Object> condition, String orderBy, String sortBy) {
        condition.put("kpi",KPIConst.DM_ANALYZE_BEHAVIOR_SUM_MONTH);
        return behaviorSumDao.queryList(condition,orderBy,sortBy);
    }

    @Override
    public List<BehaviorSum> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end) {
        condition.put("kpi",KPIConst.DM_ANALYZE_BEHAVIOR_SUM_MONTH);
        return behaviorSumDao.queryPage(condition,begin.intValue(),end.intValue(),orderBy,sortBy);
    }

    @Override
    public Long count(Map<String, Object> condition) {
        condition.put("kpi",KPIConst.DM_ANALYZE_BEHAVIOR_SUM_MONTH);
        return behaviorSumDao.count(condition);
    }

    @Override
    public List<BehaviorSum> find(Map<String, Object> condition) {
        return null;
    }
}

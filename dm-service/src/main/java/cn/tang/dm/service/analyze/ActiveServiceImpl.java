package cn.tang.dm.service.analyze;

import cn.tang.dm.dao.analyze.IActiveDao;
import cn.tang.dm.domian.analyze.Active;
import cn.tang.dm.domian.common.KPIConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 活跃总表服务类
 *
 * @author tangzeqian
 * @create 2016-03-14  21:38
 */
@Service("ActiveServiceImpl")
public class ActiveServiceImpl implements IActiveService {
    @Autowired
    private IActiveDao activeDao;
    @Override
    public int add(Active active) {
        active.setKpi(KPIConst.DM_ANALYZE_ACTIVE_DAY);
        return activeDao.insertOne(active);
    }

    @Override
    public int addList(List<Active> list) {
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
    public int update(Active entity) {
        return 0;
    }

    @Override
    public int updateList(List<Active> list) {
        return 0;
    }

    @Override
    public int updateByMap(Map<String, Object> condition) {
        return 0;
    }

    @Override
    public Active queryOne(Map<String, Object> condition) {
        condition.put("kpi",KPIConst.DM_ANALYZE_ACTIVE_DAY);
        return activeDao.queryOne(condition);
    }

    @Override
    public List<Active> queryList(Map<String, Object> condition, String orderBy, String sortBy) {
        condition.put("kpi",KPIConst.DM_ANALYZE_ACTIVE_DAY);
        return activeDao.queryList(condition,orderBy,sortBy);
    }

    @Override
    public List<Active> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end) {
        condition.put("kpi",KPIConst.DM_ANALYZE_ACTIVE_DAY);
        return activeDao.queryPage(condition,begin.intValue(),end.intValue(),orderBy,sortBy);
    }

    @Override
    public Long count(Map<String, Object> condition) {
        condition.put("kpi",KPIConst.DM_ANALYZE_ACTIVE_DAY);
        return activeDao.count(condition);
    }

    @Override
    public List<Active> find(Map<String, Object> condition) {
        return null;
    }

    @Override
    public List<Long> queryAllYear() {
        return activeDao.queryAllYear(KPIConst.DM_ANALYZE_ACTIVE_YEAR);
    }
}

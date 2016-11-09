package cn.tang.dm.service.analyze;

import cn.tang.dm.dao.analyze.IRegisterDao;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.domian.common.KPIConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 注册明细表服务类
 *
 * @author tangzeqian
 * @create 2016-03-14  21:38
 */
@Service("RegisterServiceImpl")
public class RegisterServiceImpl implements IRegisterService {
    @Autowired
    private IRegisterDao registerDao;

    @Override
    public List<Long> queryAllYear() {
        return registerDao.queryAllYear(KPIConst.DM_ANALYZE_REGISTER_YEAR);
    }

    @Override
    public Long querySumForMonth(Map<String, Object> condition) {
        condition.put("kpi",KPIConst.DM_ANALYZE_REGISTER_SUM_MONTH);
        return registerDao.querySumForMonth(condition);
    }

    @Override
    public int add(Register register) {
        register.setKpi(KPIConst.DM_ANALYZE_REGISTER_DAY);
        return registerDao.insertOne(register);
    }

    @Override
    public int addList(List<Register> list) {
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
    public int update(Register entity) {
        return 0;
    }

    @Override
    public int updateList(List<Register> list) {
        return 0;
    }

    @Override
    public int updateByMap(Map<String, Object> condition) {
        return 0;
    }

    @Override
    public Register queryOne(Map<String, Object> condition) {
        condition.put("kpi",KPIConst.DM_ANALYZE_REGISTER_DAY);
        return registerDao.queryOne(condition);
    }

    @Override
    public List<Register> queryList(Map<String, Object> condition, String orderBy, String sortBy) {
        condition.put("kpi",KPIConst.DM_ANALYZE_REGISTER_DAY);
        return registerDao.queryList(condition,orderBy,sortBy);
    }

    @Override
    public List<Register> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end) {
        condition.put("kpi",KPIConst.DM_ANALYZE_REGISTER_DAY);
        return registerDao.queryPage(condition,begin.intValue(),end.intValue(),orderBy,sortBy);
    }

    @Override
    public Long count(Map<String, Object> condition) {
        condition.put("kpi",KPIConst.DM_ANALYZE_REGISTER_DAY);
        return registerDao.count(condition);
    }

    @Override
    public List<Register> find(Map<String, Object> condition) {
        return null;
    }
}

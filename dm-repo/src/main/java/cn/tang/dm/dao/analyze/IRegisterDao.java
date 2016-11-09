package cn.tang.dm.dao.analyze;

import cn.tang.dm.dao.common.IBaseDao;
import cn.tang.dm.domian.analyze.Active;
import cn.tang.dm.domian.analyze.Register;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by tang on 2016/3/14.
 */
@Repository
public interface IRegisterDao extends IBaseDao<Register> {
    List<Long> queryAllYear(@Param("kpi") String kpi);
    Long querySumForMonth(@Param("condition") Map<String, Object> condition);
}

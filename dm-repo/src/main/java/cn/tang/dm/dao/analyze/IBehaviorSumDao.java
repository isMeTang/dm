package cn.tang.dm.dao.analyze;

import cn.tang.dm.dao.common.IBaseDao;
import cn.tang.dm.domian.analyze.Active;
import cn.tang.dm.domian.analyze.BehaviorSum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tang on 2016/3/14.
 */
@Repository
public interface IBehaviorSumDao extends IBaseDao<BehaviorSum> {
    List<Long> queryAllYear(@Param("kpi") String kpi);
}

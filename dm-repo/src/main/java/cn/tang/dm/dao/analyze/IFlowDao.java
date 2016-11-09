package cn.tang.dm.dao.analyze;

import cn.tang.dm.dao.common.IBaseDao;
import cn.tang.dm.domian.analyze.Flow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tang on 2016/3/14.
 */
@Repository
public interface IFlowDao extends IBaseDao<Flow> {
    List<Long> queryAllYear(@Param("kpi") String kpi);
}

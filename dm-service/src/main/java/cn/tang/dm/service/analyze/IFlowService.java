package cn.tang.dm.service.analyze;

import cn.tang.dm.domian.analyze.Flow;
import cn.tang.dm.service.common.IBaseService;

import java.util.List;

/**
 * Created by tang on 2016/3/14.
 */
public interface IFlowService extends IBaseService<Flow> {
    /**
     * 查询所有年份
     */
    List<Long> queryAllYear();
}

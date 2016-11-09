package cn.tang.dm.service.analyze;

import cn.tang.dm.domian.analyze.BehaviorSum;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.service.common.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by tang on 2016/3/14.
 */
public interface IBehaviorSumService extends IBaseService<BehaviorSum> {
    /**
     * 查询所有年份
     */
    List<Long> queryAllYear();
}

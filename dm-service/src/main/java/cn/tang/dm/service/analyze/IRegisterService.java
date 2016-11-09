package cn.tang.dm.service.analyze;

import cn.tang.dm.domian.analyze.Active;
import cn.tang.dm.domian.analyze.Register;
import cn.tang.dm.service.common.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by tang on 2016/3/14.
 */
public interface IRegisterService extends IBaseService<Register> {
    /**
     * 查询所有年份
     */
    List<Long> queryAllYear();
    Long querySumForMonth(Map<String, Object> condition);
}

package cn.tang.dm.service.common;


import cn.tang.dm.domian.common.BaseDomain;

import java.util.List;
import java.util.Map;

/**
 * 通用服务接口
 * Created by tang on 2016/2/25.
 */
public interface IBaseService<T extends BaseDomain> {
    /**
     * 增加一条记录
     */
    int add(T entity);

    /**
     * 增加多条记录
     */
    int addList(List<T> list);

    /**
     * 删除一条记录
     */
    int del(Long id);

    /**
     * 删除多条记录
     */
    int delList(List<Long> ids);

    /**
     * 根据集合条件删除
     */
    int delByMap(Map<String, Object> condition);

    /**
     * 更新一条记录
     */
    int update(T entity);

    /**
     * 更新多条记录
     */
    int updateList(List<T> list);

    /**
     * 根据集合条件更新
     */
    int updateByMap(Map<String, Object> condition);

    /**
     * 根据实体查询
     */
    T queryOne(Map<String, Object> condition);

    /**
     * 根据多条件查询
     */
    List<T> queryList(Map<String, Object> condition, String orderBy, String sortBy);

    /**
     * 根据多条件分页查询
     */
    List<T> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end);

    /**
     * 根据多条件查询数量
     */
    Long count(Map<String, Object> condition);

    /**
     * 查找
     */
    List<T> find(Map<String, Object> condition);
}

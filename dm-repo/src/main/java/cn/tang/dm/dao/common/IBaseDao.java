package cn.tang.dm.dao.common;

import cn.tang.dm.domian.common.BaseDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通用接口
 * Created by tang on 2016/2/25.
 */
public interface IBaseDao<T extends BaseDomain> {

    /**
     * 保存单一对象
     */
    int insertOne(T entity);

    /**
     * 保存多个对象
     */
    int insertList(List<T> list);

    /**
     * 根据单个对象
     */
    int deleteById(Long id);

    /**
     * 根据list(ids)删除对象
     */
    int deleteByIds(List list);

    /**
     * 根据条件集合删除对象
     */
    int deleteByMap(@Param("condition") Map<String, Object> condition);

    /**
     * 更新对象
     */
    int updateOne(T entity);

    /**
     * 更新多个对象
     */
    int updateList(List<T> list);

    /**
     * 根据条件集合更新对象
     */
    int updateByMap(@Param("condition") Map<String, Object> condition);


    /**
     * 查询单个对象
     */
    T queryOne(@Param("condition") Map<String, Object> condition);

    /**
     * 根据多条件查询
     */
    List<T> queryList(@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);

    /**
     * 根据条件集合进行分页查询
     */
    List<T> queryPage(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
                             @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);

    /**
     * 根据条件进行数量的查询
     */
    long count(@Param("condition") Map<String, Object> condition);

    /**
     * 模糊查询
     */
    List<T> find(@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);

}


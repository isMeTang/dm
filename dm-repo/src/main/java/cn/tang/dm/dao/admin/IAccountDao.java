package cn.tang.dm.dao.admin;

import cn.tang.dm.dao.common.IBaseDao;
import cn.tang.dm.domian.admin.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 账号接口
 * Created by tang on 2016/2/25.
 */
@Repository
public interface IAccountDao extends IBaseDao<Account>{
    List<Account> queryAll(@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);
}

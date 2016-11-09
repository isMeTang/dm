package cn.tang.dm.service.admin;


import cn.tang.dm.domian.admin.Account;
import cn.tang.dm.service.common.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 账号逻辑服务接口
 * Created by tang on 2016/2/25.
 */
public interface IAccountService extends IBaseService<Account> {
    /**
     * 获取所有账号信息
     */
    List<Account> getAllAccount();
}

package cn.tang.dm.service.admin;

import cn.tang.dm.dao.admin.IAccountDao;
import cn.tang.dm.domian.admin.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 账号逻辑服务类
 * @author tangzeqian
 * @create 2016-02-25  22:29
 */
@Service("AccountServiceImpl")
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private IAccountDao accountDao;
    @Override
    public List<Account> getAllAccount() {
        return accountDao.queryAll(null,null,null);
    }

    @Override
    public int add(Account account) {
       return accountDao.insertOne(account);
    }

    @Override
    public int addList(List<Account> list) {
        return 0;
    }

    @Override
    public int del(Long id) {
        return accountDao.deleteById(id);
    }

    @Override
    public int delList(List<Long> ids) {
        return 0;
    }

    @Override
    public int delByMap(Map<String, Object> condition) {
        return 0;
    }

    @Override
    public int update(Account entity) {
        return 0;
    }

    @Override
    public int updateList(List<Account> list) {
        return 0;
    }

    @Override
    public int updateByMap(Map<String, Object> condition) {
        return accountDao.updateByMap(condition);
    }

    @Override
    public Account queryOne(Map<String, Object> condition) {
        return accountDao.queryOne(condition);
    }

    @Override
    public List<Account> queryList(Map<String, Object> condition, String orderBy, String sortBy) {
        return accountDao.queryList(condition,orderBy,sortBy);
    }

    @Override
    public List<Account> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end) {
        return accountDao.queryPage(condition,begin.intValue(),end.intValue(),orderBy,sortBy);
    }

    @Override
    public Long count(Map<String, Object> condition) {
        return accountDao.count(condition);
    }

    @Override
    public List<Account> find(Map<String, Object> condition) {
        return accountDao.find(condition,"id","ASC");
    }
}

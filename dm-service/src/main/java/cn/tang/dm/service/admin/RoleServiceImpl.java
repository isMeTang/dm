package cn.tang.dm.service.admin;

import cn.tang.dm.dao.admin.IMenuDao;
import cn.tang.dm.dao.admin.IRoleDao;
import cn.tang.dm.domian.admin.Menu;
import cn.tang.dm.domian.admin.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统角色服务接口
 * @author tangzeqian
 * @create 2016-02-25  22:29
 */
@Service("RoleServiceImpl")
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDao roleDao;


    @Override
    public int add(Role role) {
        return roleDao.insertOne(role);
    }

    @Override
    public int addList(List<Role> list) {
        return 0;
    }

    @Override
    public int del(Long id) {
        return roleDao.deleteById(id);
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
    public int update(Role role) {
        return 0;
    }

    @Override
    public int updateList(List<Role> list) {
        return 0;
    }

    @Override
    public int updateByMap(Map<String, Object> condition) {
        return roleDao.updateByMap(condition);
    }

    @Override
    public Role queryOne(Map<String, Object> condition) {
        return roleDao.queryOne(condition);
    }

    @Override
    public List<Role> queryList(Map<String, Object> condition, String orderBy, String sortBy) {
        return roleDao.queryList(condition,orderBy,sortBy);
    }

    @Override
    public List<Role> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end) {
        return roleDao.queryPage(condition,begin.intValue(),end.intValue(),orderBy,sortBy);
    }

    @Override
    public Long count(Map<String, Object> condition) {
        return roleDao.count(condition);
    }

    @Override
    public List<Role> find(Map<String, Object> condition) {
        return roleDao.find(condition,"id","ASC");
    }
}

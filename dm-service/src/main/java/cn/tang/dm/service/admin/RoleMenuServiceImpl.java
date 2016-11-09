package cn.tang.dm.service.admin;

import cn.tang.dm.dao.admin.IRoleDao;
import cn.tang.dm.dao.admin.IRoleMenuDao;
import cn.tang.dm.domian.admin.Role;
import cn.tang.dm.domian.admin.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色菜单服务接口
 * @author tangzeqian
 * @create 2016-02-25  22:29
 */
@Service("RoleMenuServiceImpl")
public class RoleMenuServiceImpl implements IRoleMenuService {
    @Autowired
    private IRoleMenuDao roleMenuDao;


    @Override
    public int add(RoleMenu roleMenu) {
        return roleMenuDao.insertOne(roleMenu);
    }

    @Override
    public int addList(List<RoleMenu> list) {
        return 0;
    }

    @Override
    public int del(Long id) {
        return roleMenuDao.deleteById(id);
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
    public int update(RoleMenu roleMenu) {
        return 0;
    }

    @Override
    public int updateList(List<RoleMenu> list) {
        return 0;
    }

    @Override
    public int updateByMap(Map<String, Object> condition) {
        return roleMenuDao.updateByMap(condition);
    }

    @Override
    public RoleMenu queryOne(Map<String, Object> condition) {
        return roleMenuDao.queryOne(condition);
    }

    @Override
    public List<RoleMenu> queryList(Map<String, Object> condition, String orderBy, String sortBy) {
        return roleMenuDao.queryList(condition,orderBy,sortBy);
    }

    @Override
    public List<RoleMenu> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end) {
        return roleMenuDao.queryPage(condition,begin.intValue(),end.intValue(),orderBy,sortBy);
    }

    @Override
    public Long count(Map<String, Object> condition) {
        return roleMenuDao.count(condition);
    }

    @Override
    public List<RoleMenu> find(Map<String, Object> condition) {
        return roleMenuDao.find(condition,"id","ASC");
    }
}

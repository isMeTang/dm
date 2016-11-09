package cn.tang.dm.service.admin;

import cn.tang.dm.dao.admin.IAccountDao;
import cn.tang.dm.dao.admin.IMenuDao;
import cn.tang.dm.domian.admin.Account;
import cn.tang.dm.domian.admin.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单服务接口
 * @author tangzeqian
 * @create 2016-02-25  22:29
 */
@Service("MenuServiceImpl")
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private IMenuDao menuDao;


    @Override
    public int add(Menu menu) {
        return menuDao.insertOne(menu);
    }

    @Override
    public int addList(List<Menu> list) {
        return 0;
    }

    @Override
    public int del(Long id) {
        return menuDao.deleteById(id);
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
    public int update(Menu entity) {
        return 0;
    }

    @Override
    public int updateList(List<Menu> list) {
        return 0;
    }

    @Override
    public int updateByMap(Map<String, Object> condition) {
        return menuDao.updateByMap(condition);
    }

    @Override
    public Menu queryOne(Map<String, Object> condition) {
        return menuDao.queryOne(condition);
    }

    @Override
    public List<Menu> queryList(Map<String, Object> condition, String orderBy, String sortBy) {
        return menuDao.queryList(condition,orderBy,sortBy);
    }

    @Override
    public List<Menu> queryPage(Map<String, Object> condition, String orderBy, String sortBy, Long begin, Long end) {
        return menuDao.queryPage(condition,begin.intValue(),end.intValue(),orderBy,sortBy);
    }

    @Override
    public Long count(Map<String, Object> condition) {
        return menuDao.count(condition);
    }

    @Override
    public List<Menu> find(Map<String, Object> condition) {
        return menuDao.find(condition,"id","ASC");
    }
}

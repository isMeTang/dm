package cn.tang.dm.domian.result;

import cn.tang.dm.domian.admin.Menu;

import java.util.List;

/**
 * 菜单返回实体
 *
 * @author tangzeqian
 * @create 2016-03-05  12:35
 */
public class MenuResult {

    /**
     * 一级菜单
     */
    private Menu mainMenu;

    /**
     * 子菜单
     */
    private List<Menu> subMenu;

    public MenuResult(Menu mainMenu, List<Menu> subMenu) {
        this.mainMenu = mainMenu;
        this.subMenu = subMenu;
    }

    public Menu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(Menu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public List<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Menu> subMenu) {
        this.subMenu = subMenu;
    }

    @Override
    public String toString() {
        return "MenuResult{" +
                "mainMenu=" + mainMenu +
                ", subMenu=" + subMenu +
                '}';
    }
}

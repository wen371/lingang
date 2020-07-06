package com.jw.sys.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jw.sys.bean.vo.MenuNode;
import com.jw.sys.bean.vo.MenuVo;
import com.jw.sys.bean.vo.ZTreeNode;
import com.jw.sys.dao.MenuDao;
import com.jw.sys.dao.RelationDao;
import com.jw.sys.bean.entity.Menu;
import com.jw.sys.bean.entity.Relation;
import com.jw.sys.api.IMenuService;
import com.jw.common.constant.state.IsMenu;
import com.jw.common.constant.state.MenuStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 菜单服务
 *
 * @author fengshuonan
 * @date 2017-05-05 22:20
 */
@Service("menuService")
public class MenuServiceImpl implements IMenuService {

        @Autowired
        private MenuDao menuDao ;

        @Autowired
        private RelationDao relationDao;

        @Override
        public List<MenuVo> selectMenus(String menuName, String level, Integer type) {
            return getListVo(menuDao.selectMenus(menuName,level,type));
    }

    @Override
    public List<MenuNode> getMenus() {
        return menuDao.getMenus();
    }

    @Override
    public MenuVo selectById(Integer menuId) {
        return getVo(menuDao.selectById(menuId)) ;
    }

    @Override
    public List<ZTreeNode> menuTreeList(Integer type) {
        return menuDao.menuTreeList(type) ;
    }

    @Override
    public void updateById(MenuVo menuVo) {
        menuDao.updateById(getEntity(menuVo));
    }

    @Override
    public void insert(MenuVo menuVo) {

        menuDao.insert(getEntity(menuVo));
    }

    @Override
    public String getMenuNameByCode(String code) {
        if(menuDao.getMenuNameByCode(code)== null){
            return null;
        }
        return menuDao.getMenuNameByCode(code).getName();
    }

    @Override
    public void delMenuContainSubMenus(Integer menuId,Integer mender) {
        Menu menu = menuDao.selectById(menuId);
        menuDao.delMenuById(menu.getId(),mender);
       /* List<Menu> subMenu = menuDao.selectSubmenu("");
        for(Menu sub :subMenu){
            menuDao.delMenuById(sub.getId(),mender);
        }*/

    }

    @Override
    public MenuVo selectPMenuByPcode(String pcode) {
        return getVo(menuDao.selectPMenuByPcode(pcode));
    }

    @Override
    public List<MenuNode> getMenusByRoleIds(List<Integer> roleList,Integer type) {
        Map<String ,Object> map = new HashMap<>();
        map.put("list",roleList);
        map.put("type",type);
        return menuDao.getMenusByRoleIds(map);
    }

    @Override
    public MenuVo getMenuByCode(String code) {
        return getVo(menuDao.getMenuNameByCode(code));
    }
    @Override
    public List<String> getResUrlsByRoleId(Integer roleId) {
           List<Menu> menus = menuDao.getResUrlsByRoleId(roleId);
           List<String> list = Lists.newArrayList();
           for(Menu menu :menus){
               list.add(menu.getUrl());
           }
        return list;
    }

    @Override
    public List<Integer> getMenuIdsByRoleId(Integer roleId,Integer type) {
    System.out.println("!!!!!"+JSON.toJSONString(menuDao.getMenuIdsByRoleId(roleId,type)));
        return menuDao.getMenuIdsByRoleId(roleId,type);
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds,Integer type) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",menuIds);
        map.put("type",type);
        return menuDao.menuTreeListByMenuIds(map);
    }

    @Override
    public void updateByCode(MenuVo menuVo) {
        menuDao.updateByCode(getEntity(menuVo));
    }
    

    @Override
    public List<MenuVo> selectNextLevelMenu(String pcode) {
        return getListVo(menuDao.selectNextLevelMenu(pcode));
    }

    private List<MenuVo> getListVo(List<Menu> entitys){
        List<MenuVo> result =  Lists.newArrayList();
        for(Menu menu:entitys){
            MenuVo menuVo = new MenuVo();
            menuVo.setCode(menu.getCode());
            menuVo.setStatus(menu.getStatus());
            menuVo.setIcon(menu.getIcon());
            menuVo.setIsmenu(menu.getIsMenu());
            menuVo.setId(menu.getId());
            menuVo.setLevels(menu.getLevel());
            menuVo.setName(menu.getName());
            menuVo.setNum(menu.getNumSort());
            menuVo.setPcode(menu.getPcode());
            menuVo.setTips(menu.getTips());
            menuVo.setUrl(menu.getUrl());
            menuVo.setType(menu.getType());
            menuVo.setStatusName(MenuStatus.valueOf(menu.getStatus()));
            String isMenu=menu.getIsMenu()+"";
            if("0".equals(isMenu)){
                menuVo.setIsMenuName("不是");
            }else{
                menuVo.setIsMenuName("是");
            }
            result.add(menuVo);
        }

        return result;
    }
    private MenuVo getVo(Menu menu){
        MenuVo menuVo = new MenuVo();
            if(null!=menu){
            menuVo.setCode(menu.getCode());
            menuVo.setStatus(menu.getStatus());
            menuVo.setIcon(menu.getIcon());
            menuVo.setIsmenu(menu.getIsMenu());
            menuVo.setId(menu.getId());
            menuVo.setLevels(menu.getLevel());
            menuVo.setName(menu.getName());
            menuVo.setNum(menu.getNumSort());
            menuVo.setPcode(menu.getPcode());
            menuVo.setTips(menu.getTips());
            menuVo.setUrl(menu.getUrl());
            menuVo.setType(menu.getType());
            if(null!=menu.getStatus()){
                if(menu.getStatus().equals(0)){
                    menuVo.setStatusName("禁用");
                }else{
                    menuVo.setStatusName("启用");
                }
            }
            menuVo.setIsMenuName(IsMenu.valueOf(menu.getStatus()));
            }
        return menuVo;
    }
    private Menu getEntity(MenuVo menuVo){
        Menu menu = new Menu();
        menu.setCode(menuVo.getCode());
        menu.setStatus(menuVo.getStatus());
        menu.setIcon(menuVo.getIcon());
        if(null!=menuVo.getIsmenu()){
            menu.setIsMenu(menuVo.getIsmenu());
        }
        menu.setId(menuVo.getId());
        menu.setLevel(menuVo.getLevels());
        menu.setName(menuVo.getName());
        menu.setNumSort(menuVo.getNum());
        menu.setPcode(menuVo.getPcode());
        menu.setTips(menuVo.getTips());
        menu.setUrl(menuVo.getUrl());
        menu.setCreator(menuVo.getCreator());
        menu.setMender(menuVo.getMender());
        menu.setType(menuVo.getType());
        return menu;
    }

    public void updateMenuRole(Integer menuId ,Integer isDelete){
        Relation relation = new Relation();
        relation.setMenuId(menuId);
        relation.setIsDelete(isDelete);
        relationDao.updateMenuRole(relation);
    }

}

package com.jw.sys.dao;

import com.jw.common.bean.BaseDao;
import com.jw.sys.bean.vo.MenuNode;
import com.jw.sys.bean.vo.ZTreeNode;
import com.jw.sys.bean.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MenuDao extends BaseDao {


    List<Menu> selectMenus(@Param("menuName") String menuName, @Param("level") String level, @Param("type") Integer type) ;

    List<MenuNode> getMenus();

    Menu selectById(Integer menuId);

    Menu selectOne(Menu temp);

    List<ZTreeNode> menuTreeList(Integer type);

    void updateById(Menu menu);

    void insert(Menu menu);

    Menu getMenuNameByCode(String code);

    List<Menu> selectSubmenu(String pcodes);

    void delMenuById(@Param("id") Integer id, @Param("mender") Integer mender);

    Menu selectPMenuByPcode(String pcode);

    List<MenuNode> getMenusByRoleIds(Map<String, Object> map);

    List<Menu> getResUrlsByRoleId(Integer roleId);

    List<Integer> getMenuIdsByRoleId(@Param("roleId") Integer roleId, @Param("type") Integer type);

    List<ZTreeNode> menuTreeListByMenuIds(Map<String, Object> map);

    void updateByCode(Menu entity);
    
    List<Menu> selectNextLevelMenu(@Param("pcode") String pcode);
}
package com.jw.sys.api;

import com.jw.sys.bean.vo.MenuNode;
import com.jw.sys.bean.vo.MenuVo;
import com.jw.sys.bean.vo.ZTreeNode;

import java.util.List;


/**
 * 菜单服务
 *
 * @author fengshuonan
 * @date 2017-05-05 22:19
 */

public interface IMenuService {


    List<MenuVo> selectMenus(String menuName, String level, Integer type);

    List<MenuNode> getMenus();

    MenuVo selectById(Integer menuId);

    List<ZTreeNode> menuTreeList(Integer type);

    void updateById(MenuVo menu);

    void insert(MenuVo menu);

    String getMenuNameByCode(String code);

    void delMenuContainSubMenus(Integer menuId, Integer mender);

    MenuVo selectPMenuByPcode(String pcode);


    List<MenuNode> getMenusByRoleIds(List<Integer> roleList, Integer type);

    MenuVo getMenuByCode(String code);

    List<String> getResUrlsByRoleId(Integer roleId);

    List<Integer> getMenuIdsByRoleId(Integer roleId, Integer type);

    List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds, Integer type);

    void updateByCode(MenuVo menuVo);

    List<MenuVo> selectNextLevelMenu(String pcode);

    public void updateMenuRole(Integer menuId, Integer isDelete);
}

package com.jw.common.constant;

/**
 * 系统常量
 *
 * @author fengshuonan
 * @date 2017年2月12日 下午9:42:53
 */
public interface Const {

    /**
     * 系统默认的管理员密码
     */
    String DEFAULT_PWD = "111111";

    /**
     * 出单员默认初始密码
     */
    String DEFAULT_PWD_AGENT = "As12345678";

    /**
     * 管理员角色的名字
     */
    String ADMIN_NAME = "administrator";

    /**
     * 管理员id
     */
    Integer ADMIN_ID = 1;

    /**
     * 超级管理员角色id
     */
    Integer ADMIN_ROLE_ID = 1;

    /**
     * 接口文档的菜单名
     */
    String API_MENU_NAME = "接口文档";

    /**
     * 网点状态 有效
     */
    Integer WORK_POINT_STATUS_YES = 0;

    /**
     * 网点状态 无效
     */
    Integer WORK_POINT_STATUS_NO = 1;

    /**
     * 用户类型 天安
     */
    Integer USER_TYPE_TIAN_AN = 0;

    /**
     * 用户类型 企业
     */
    Integer USER_TYPE_NET_WORK = 1;

    /**
     * 用户状态 启用
     */
    Integer USER_STATUS_OPEN = 1;

    /**
     * 用户状态 冻结
     */
    Integer USER_STATUS_CLOSE = 2;

    /**
     * 用户状态 删除
     */
    Integer USER_STATUS_DELETE = 3;

    /**
     * 通知公告 未发布
     */
    String OPT_NOTICE_STATUS_NO = "0";

    /**
     * 通知公告 发布
     */
    String OPT_NOTICE_STATUS_YES = "1";

    /**
     * 产品 初始状态
     */
    String PRODUCT_STATUS_DEFAULT = "0";

    /**
     * 产品 上架
     */
    String PRODUCT_STATUS_YES = "1";

    /**
     * 产品 下架
     */
    String PRODUCT_STATUS_NO = "2";
}

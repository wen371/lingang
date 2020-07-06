/**
 * 系统管理--用户管理的单例对象
 */
var Admin = {
    id: "adminTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
Admin.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '景区名称', field: 'scenicName', align: 'center', valign: 'middle'},
        {title: '用户名', field: 'name', align: 'center', valign: 'middle'},
        {title: '账户', field: 'account', align: 'center', valign: 'middle'}
        ];
    return columns;
};

/**
 * 检查是否选中
 */
Admin.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Admin.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
Admin.openAddAdmin = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['850px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/leaderMgr/user_add'     //+Admin.deptId
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
Admin.openChangeAdmin = function () {

    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/leaderMgr/leaderUser_edit/' + this.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};
/**
 * 重置密码
 */
Admin.resetPwd = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        parent.layer.confirm('是否重置密码为As12345678？', {
            btn: ['确定', '取消'],
            shade: false //不显示遮罩
        }, function () {
            var ajax = new $ax(Feng.ctxPath + "/mgr/reset", function (data) {
                Feng.success("重置密码成功!");
            }, function (data) {
                Feng.error("重置密码失败!");
            });
            ajax.set("userId", userId);
            ajax.start();
        });
    }
};


/**
 * 冻结管理员
 * @param userId
 */
Admin.freezeAccount = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/mgr/freeze", function (data) {
            Feng.success("冻结成功!");
            Admin.search();
        }, function (data) {
            Feng.error("冻结失败!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};

/**
 * 解除冻结管理员
 * @param userId
 */
Admin.unfreeze = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/mgr/unfreeze", function (data) {
            Feng.success("状态解除成功!");
            Admin.search();
        }, function (data) {
            Feng.error("状态解除失败!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
}


/**
 * 点击角色分配
 * @param
 */
Admin.roleAssign = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户
 */
Admin.delAdmin = function () {
    if (this.check()) {
        var operation = function(){
            var userId = Admin.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/admin/delete", function () {
                Feng.success("删除成功!");
                Admin.search();
            }, function (data) {
                Feng.error("删除失败!" );
            });
            ajax.set("userId", userId);
            ajax.start();
        };

        Feng.confirm("是否删除用户" + Admin.seItem.account + "?",operation);
    }
};

/**
 * 冻结用户账户
 * @param userId
 */
Admin.freezeAccount = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/mgr/freeze", function (data) {
            Feng.success("冻结成功!");
            Admin.search();
        }, function (data) {
            Feng.error(data.responseJSON.message);
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};

/**
 * 解除冻结用户账户
 * @param userId
 */
Admin.unfreeze = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/mgr/unfreeze", function (data) {
            Feng.success("状态解除成功!");
            Admin.search();
        }, function (data) {
            Feng.error(data.responseJSON.message);
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};

/**
 * 重置密码
 */


Admin.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#scenicId").val("");
    Admin.search();
};

/**
 * 点击角色分配
 * @param
 */
Admin.roleAssign = function () {




    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mgr/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户
 */
Admin.delMgrUser = function () {
    if (this.check()) {
        var operation = function(){
            var userId = Admin.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/leaderMgr/delete", function () {
                Feng.success("删除成功!");
                Admin.search();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", userId);
            ajax.start();
        };

        Feng.confirm("是否删除用户" + Admin.seItem.account + "?",operation);
    }
};


Admin.search = function () {
    var queryData = {};

    queryData['name'] = $("#name").val();
    queryData['scenicId'] = $("#scenicId").val();
    console.info(queryData);
    Admin.table.refresh({query: queryData});
};


$(function () {
    var defaultColunms = Admin.initColumn();
    var table = new BSTable("adminTable", "/leaderMgr/userList", defaultColunms);
    table.setPaginationType("server");
    Admin.table = table.init();
});

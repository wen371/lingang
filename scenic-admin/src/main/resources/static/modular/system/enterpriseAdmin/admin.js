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
        {title: '账号', field: 'account', align: 'center', valign: 'middle'},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle'},
        {title: '性别', field: 'sexName', align: 'center', valign: 'middle'},
        {title: '身份证号', field: 'idNo', align: 'center', valign: 'middle'},
        {title: '角色', field: 'roleName', align: 'center', valign: 'middle'},
        {title: '网点名称', field: 'networkName', align: 'center', valign: 'middle'},
        {title: '企业名称', field: 'agentName', align: 'center', valign: 'middle'},
        {title: '邮箱', field: 'email', align: 'center', valign: 'middle'},
        {title: '电话', field: 'phone', align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle'}];
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
    var agentId = $("#agentId").val();
    if(Admin.deptId==-1){
        Feng.info("请选择一个组织机构");
        return;
    }
    if(Admin.deptId==agentId){
        Feng.info("中介下不能新增管理员");
        return;
    }
    var index = layer.open({
        type: 2,
        title: '添加管理员',
        area: ['850px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/enterpriseAdmin/admin_add?deptId='+Admin.deptId
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
Admin.openChangeAdmin = function () {

    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑管理员',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admin/admin_edit/' + this.seItem.id
        });
        this.layerIndex = index;
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
            Feng.error("冻结失败!" + data.responseJSON.message + "!");
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
                Admin.search();;
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
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
            Feng.error("冻结失败!" + data.responseJSON.message + "!");
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
            Feng.error("状态解除失败!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};



Admin.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");

    Admin.search();
};

Admin.search = function () {
    var queryData = {};

    queryData['deptId'] = Admin.deptId;
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();

    Admin.table.refresh({query: queryData});
};

Admin.onClickDept = function (e, treeId, treeNode) {
    Admin.deptId = treeNode.id;
    console.log("Admin.deptId  "+Admin.deptId);
    Admin.search();
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
Admin.openChangeUser = function () {

    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑管理员',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/enterpriseAdmin/admin_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
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
            var ajax = new $ax(Feng.ctxPath + "/mgr/delete", function () {
                Feng.success("删除成功!");
                Admin.search();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("userId", userId);
            ajax.start();
        };

        Feng.confirm("是否删除用户" + Admin.seItem.account + "?",operation);
    }
};


$(function () {
    var defaultColunms = Admin.initColumn();
    var table = new BSTable("adminTable", "/enterpriseAdmin/list", defaultColunms);
    table.setPaginationType("server");
    Admin.table = table.init();
    var ztree = new $ZTree("workPointTree", "/workpoint/workpointTreeList");
    ztree.bindOnClick(Admin.onClickDept);
    ztree.init();
});

/**
 * 系统管理--用户管理的单例对象
 */
var Admin = {
    id: "adminTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
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
        {title: '部门名称', field: 'orgName', align: 'center', valign: 'middle'},
        {title: '邮箱', field: 'email', align: 'center', valign: 'middle'},
        {title: '电话', field: 'phone', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle'},
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
    var index = layer.open({
        type: 2,
        title: '添加管理员',
        area: ['800px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/enterpriseAdmin/admin_add'
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
            MgrUser.table.refresh();
        }, function (data) {
            Feng.error("冻结失败,请重新操作!");
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
            MgrUser.table.refresh();
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
                Admin.table.refresh();
            }, function (data) {
                Feng.error("删除失败,请重新操作!");
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
            Admin.table.refresh();
        }, function (data) {
            Feng.error("冻结失败,请重新操作!");
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
            Admin.table.refresh();
        }, function (data) {
            Feng.error("状态解除失败!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};

/**
 * 验证密码
 */
Admin.confirmPass = function () {
        layer.open({
            content:"<input type='password' id='password1' placeholder='请输入当前密码'>",
            btn: ['确定', '取消'],
            shade: false, //不显示遮罩
            btn1: function (index,layero) {
                Admin.confirmPass1($(".layui-layer-content #password1").val());
            },
            btn2:function (index,layero) {
                console.log("quxiao  "+$("#password1").val())
            }
        });
};
Admin.confirmPass1 = function (password) {
    $.ajax({
        type: "post",
        url: Feng.ctxPath + "/enterpriseAdminInfo/confirmPass",
        async: false,
        data: {"password":$("#password1").val()},
        success: function(data) {
            if(data.code=="200"){
                layer.closeAll();
                Admin.changePass();
            }
            if(data.code=="100"){
                Feng.error("密码错误!");
            }
        },
        error: function(data) {
            Feng.error("验证密码失败!");
        }
    });
};
/**
 * 修改密码
 */
Admin.changePass = function () {
    layer.open({
        content:"<div><input type='password' id='password2' placeholder='请输入新密码' maxlength='22' />" +
        "</div><div><input type='password' id='password3' placeholder='请确认新密码' maxlength='22' /></div>",
        btn: ['确定', '取消'],
        shade: false, //不显示遮罩
        btn1: function (index,layero) {
            var password1 = $("#password2").val();
            var password2 = $("#password3").val();
            if(password1!=password2){
                Feng.error("两次密码不一致,请重新输入!");
                return;
            };
            if(password1.length<8){
                Feng.error("密码长度必须为8-22位");
                return;
            };
            var p = /[0-9]/;
            var p1 = /[a-z]/;
            var p2 = /[A-Z]/;
            var b = p.test(password2);
            var b1 = p1.test(password2);
            var b2 = p2.test(password2);
            if(!(b&b1&b2)){
                Feng.error("密码必须包含数字及大小写字母");
                return;
            };
            $.ajax({
                type: "post",
                url: Feng.ctxPath + "/enterpriseAdminInfo/changePass",
                async: false,
                data: {"password":$("#password2").val()},
                success: function(data) {
                    if(data.code=="200"){
                        layer.closeAll();
                        Feng.success("密码修改成功!");
                    }
                    if(data.code=="100"){
                        Feng.error("密码错误!");
                    }
                },
                error: function(data) {
                    Feng.error("验证密码失败!");
                }
            });


        },
        btn2:function (index,layero) {
            console.log("quxiao  "+$("#password1").val())
        }
    });
};


$(function () {
    console.log("aaaaaaaaa");
    $(".form-control").css('background-color','white');
    /*$(".form-control").style.cursor='auto';*/
});

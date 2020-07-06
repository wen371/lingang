/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var AdminInfoDlg = {
    adminInfoData: {},
    validateFields: {
        account: {
            validators: {
                notEmpty: {
                    message: '用户名不能为空'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        idNo: {
            validators: {
                notEmpty: {
                    message: '身份证不能为空'
                }
            }
        },birthday: {
            validators: {
                notEmpty: {
                    message: '出生日期不能为空'
                }
            }
        },email: {
            validators: {
                notEmpty: {
                    message: '邮箱不能为空'
                }
            }
        },phone: {
            validators: {
                notEmpty: {
                    message: '手机号不能为空'
                }
            }
        },sex: {
            validators: {
                notEmpty: {
                    message: '性别不能为空'
                }
            }
        }


    }
};

/**
 * 清除数据
 */
AdminInfoDlg.clearData = function () {
    this.adminInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdminInfoDlg.set = function (key, val) {
    this.adminInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdminInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
AdminInfoDlg.close = function () {
    parent.layer.close(window.parent.Admin.layerIndex);
};

/**
 * 点击部门input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
AdminInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#dept").attr("value", instance.getSelectedVal());
    $("#orgCode").attr("value", treeNode.code);
};

AdminInfoDlg.onClickDept1 = function (e, treeId, treeNode) {
    $("#company").attr("value", instance1.getSelectedVal());
    $("#agentCode").attr("value", treeNode.code);
};
AdminInfoDlg.onClickDept2 = function (e, treeId, treeNode) {
    $("#network").attr("value", instance2.getSelectedVal());
    $("#networkCode").attr("value", treeNode.code);
};
/**
 * 显示部门选择的树
 *
 * @returns
 */
/*AdminInfoDlg.showDeptSelectTree = function () {
    var dept = $("#dept");
    var deptOffset = $("#dept").offset();
    $("#deptContent").css({
        left: deptOffset.left + "px",
        top: deptOffset.top + dept.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};

AdminInfoDlg.showDeptSelectTree1 = function () {
    var company = $("#company");
    var companyOffset = $("#company").offset();
    $("#deptContent1").css({
        left: companyOffset.left + "px",
        top: companyOffset.top + company.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};

AdminInfoDlg.showDeptSelectTree2 = function () {
    var network = $("#network");
    var networkOffset = $("#network").offset();
    $("#deptContent2").css({
        left: networkOffset.left + "px",
        top: networkOffset.top + network.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};*/

/**
 * 显示用户详情部门选择的树
 *
 * @returns
 */
/*UserInfoDlg.showInfoDeptSelectTree = function () {
    var cityObj = $("#citySel");
    var cityPosition = $("#citySel").position();
    $("#menuContent").css({
        left: cityPosition.left + "px",
        top: cityPosition.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};*/

/**
 * 隐藏部门选择的树
 */
AdminInfoDlg.hideDeptSelectTree = function () {
    $("#deptContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};
AdminInfoDlg.hideDeptSelectTree1 = function () {
    $("#deptContent1").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};
AdminInfoDlg.hideDeptSelectTree2 = function () {
    $("#deptContent2").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

/**
 * 收集数据
 */
AdminInfoDlg.collectData = function () {
    this.set('id').set('account').set('name').set('idNo').set('type').set('sex').set('birthday').set('phone').set('email')
        .set('deptCode').set('orgCode').set('innerCode');
};
/**
 * 提交添加用户
 */
AdminInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/enterpriseAdmin/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Admin.search();
        AdminInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set(this.adminInfoData);
    ajax.start();
};
/**
 * 验证两个密码是否一致
 */
/*UserInfoDlg.validatePwd = function () {
    var password = this.get("password");
    var rePassword = this.get("rePassword");
    if (password == rePassword) {
        return true;
    } else {
        return false;
    }
};*/

/**
 * 验证数据是否为空
 */
AdminInfoDlg.validate = function () {
    $('#adminInfoForm').data("bootstrapValidator").resetForm();
    $('#adminInfoForm').bootstrapValidator('validate');
    return $("#adminInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加用户
 */
AdminInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/enterpriseAdmin/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Admin.table.refresh();
        AdminInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adminInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
/*UserInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
        Feng.success("修改成功!");
        if (window.parent.MgrUser != undefined) {
            window.parent.MgrUser.table.refresh();
            UserInfoDlg.close();
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userInfoData);
    ajax.start();
};*/

/**
 * 修改密码
 */
/*UserInfoDlg.chPwd = function () {
    var ajax = new $ax(Feng.ctxPath + "/mgr/changePwd", function (data) {
        Feng.success("修改成功!");
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("oldPwd");
    ajax.set("newPwd");
    ajax.set("rePwd");
    ajax.start();

};*/

function onBodyDown(event) {
    if (!( event.target.id == "deptContent" || $(event.target).parents("#deptContent").length > 0)) {
       AdminInfoDlg.hideDeptSelectTree();
    }
    if (!( event.target.id == "deptContent1" || $(event.target).parents("#deptContent1").length > 0)) {
        AdminInfoDlg.hideDeptSelectTree1();
    }
    if (!( event.target.id == "deptContent2" || $(event.target).parents("#deptContent2").length > 0)) {
        AdminInfoDlg.hideDeptSelectTree2();
    }
}

$(function () {
    Feng.initValidator("adminInfoForm", AdminInfoDlg.validateFields);
    $("#sex").val($("#sexValue").val());
    /*var ztree = new $ZTree("treeDemo", "/orgtree/findOrgTree/0");
    ztree.bindOnClick(AdminInfoDlg.onClickDept);
    ztree.init();
    instance = ztree;

    var ztree1 = new $ZTree("treeDemo1", "/orgtree/findOrgTree/3");
    ztree1.bindOnClick(AdminInfoDlg.onClickDept1);
    ztree1.init();
    instance1 = ztree1;

    var ztree2 = new $ZTree("treeDemo2", "/orgtree/findOrgTree/4");
    ztree2.bindOnClick(AdminInfoDlg.onClickDept2);
    ztree2.init();
    instance2 = ztree2;*/


});

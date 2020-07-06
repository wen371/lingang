/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var AdminInfoDlg = {
    adminInfoData: {},
    validateFields: {
        account: {
            validators: {
                notEmpty: {
                    message: '账号不能为空'
                },
                stringLength: {
                    min: 2,
                    max: 10,
                    message: '账号长度为2-10位'
                },
                regexp: {
                    regexp: /^[a-zA-Z\u4e00-\u9fa5]+$/,
                    message: '账号由汉字或者字母组成'
                }
            }
        }/*,
        name: {
            validators: {
                notEmpty: {
                    message: '真实姓名不能为空'
                },
                stringLength: {
                    min: 2,
                    max: 10,
                    message: '真实姓名长度为2-10位'
                },
                regexp: {
                    regexp: /^[a-zA-Z\u4e00-\u9fa5]+$/,
                    message: '真实姓名由汉字或者字母组成'
                }
            }
        }*//*,
        idNo: {
            validators: {
                notEmpty: {
                    message: '身份证号不能为空'
                },
                callback: {
                    message: '身份证号格式有误',
                    callback: function (value, validator) {
                        var tip = checkCard(value);
                        if (tip == '') {
                            $("#birthday").val(getBirthdateByIdNo(value));
                            //$("#sex").val(getSexByIdNo(value));
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }, birthday: {
            validators: {
                notEmpty: {
                    message: '出生日期不能为空'
                }
            }
        }, email: {
            validators: {
                notEmpty: {
                    message: '邮箱不能为空'
                },
                regexp: {
                    regexp: /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}$/,
                    message: '邮箱格式有误'
                }
            }
        }, phone: {
            validators: {
                notEmpty: {
                    message: '手机号不能为空'
                },
                regexp: {
                    regexp: /^[1][3,4,5,7,8,9][0-9]{9}$/,
                    message: '手机号格式有误'
                }
            }
        }, sex: {
            validators: {
                notEmpty: {
                    message: '性别不能为空'
                }
            }
        }*/, scenicId: {
            validators: {
                notEmpty: {
                    message: '景区不能为空'
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
 * 收集数据
 */
AdminInfoDlg.collectData = function () {
    this.set('id').set('account').set('name').set('idNo').set('type').set('sex').set('birthday').set('phone').set('email')
        .set('deptCode').set('agentCode').set('orgCode').set('networkCode').set('innerCode').set('scenicId');
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
        window.parent.Admin.search();
        AdminInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
        window.parent.Admin.search();
        AdminInfoDlg.close();
    });
    ajax.set(this.adminInfoData);
    ajax.start();
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
        Feng.error("修改失败!" + data.responseJSON.message + "!");
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
    if (!(event.target.id == "deptContent" || $(event.target).parents("#deptContent").length > 0)) {
        AdminInfoDlg.hideDeptSelectTree();
    }
    if (!(event.target.id == "deptContent1" || $(event.target).parents("#deptContent1").length > 0)) {
        AdminInfoDlg.hideDeptSelectTree1();
    }
    if (!(event.target.id == "deptContent2" || $(event.target).parents("#deptContent2").length > 0)) {
        AdminInfoDlg.hideDeptSelectTree2();
    }
}

/**
 * 根据身份证号返回生日
 */
function getBirthdateByIdNo(idNo) {
    var idNum = idNo.substring(6, 14);
    return idNum.substring(0, 4) + "-" + idNum.substring(4, 6) + "-" + idNum.substring(6, 8);
}

/**
 * 根据身份证号返回性别
 */
function getSexByIdNo(idNo) {
    var sexNum = idNo.substring(16, 17);
    if (sexNum % 2 == 0) {
        //女
        return "2";
    } else {
        return "1";
    }
}

$(function () {
    Feng.initValidator("adminInfoForm", AdminInfoDlg.validateFields);
    $("#sex").val($("#sexValue").val());
});

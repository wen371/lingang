/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var UserInfoDlg1 = {
    userInfoData: {},
    validateFields: {
        password: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'rePassword',
                    message: '两次密码不一致'
                }
            }
        },
        rePassword: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'password',
                    message: '两次密码不一致'
                }
            }
        }
    }
};


/**
 * 关闭此对话框
 */
UserInfoDlg1.close = function () {
    parent.layer.close(window.parent.MgrUser.layerIndex);
};


/**
 * 验证两个密码是否一致
 */
UserInfoDlg1.validatePwd = function () {
    var password = this.get("password");
    var rePassword = this.get("rePassword");
    if (password == rePassword) {
        return true;
    } else {
        return false;
    }
};




/**
 * 修改密码
 */
UserInfoDlg1.chPwd = function () {
    var type=0;
    var ajax = new $ax(Feng.ctxPath + "/mgr/changePwd", function (data) {
        if(data.code=="100"){
            Feng.error(data.message);
        }else {
            type = data.type;
            console.log(type+"aaa");
            layer.confirm('修改成功,请重新登录!', {
                btn: ['确定', '取消'],
                shade: false //不显示遮罩
            }, function () {
                window.location.href= Feng.ctxPath + "/logout";
            });
        }
    }, function (data) {
        Feng.error("修改失败!");
    });
    var password = $("#newPwd").val();
    var password1 = $("#rePwd").val();
    if(password!=password1){
        Feng.error("两次输入密码不一致");
        return;
    }
    console.log("password  "+password);
    if(password.length<8){
        Feng.error("密码长度必须为8-22位");
        return;
    };
    var p = /[0-9]/;
    var p1 = /[a-z]/;
    var p2 = /[A-Z]/;
    var b = p.test(password);
    var b1 = p1.test(password);
    var b2 = p2.test(password);
    if(!(b&b1&b2)){
        Feng.error("密码必须包含数字及大小写字母");
        return;
    };
    ajax.set("oldPwd");
    ajax.set("newPwd");
    ajax.set("rePwd");
    ajax.start();

};



$(function () {
    Feng.initValidator("userInfoForm", UserInfoDlg1.validateFields);

});


var MonitorNvrInfoDlg = {
    monitorNvrInfoData: {},
    validateFields: {
        userName: {
            validators: {
                notEmpty: {
                    message: '设备登录账号不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
MonitorNvrInfoDlg.clearData = function () {
    this.monitorNvrInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MonitorNvrInfoDlg.set = function (key, val) {
    this.monitorNvrInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MonitorNvrInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
MonitorNvrInfoDlg.close = function () {
    parent.layer.closeAll();
};

/**
 * 收集数据
 */
MonitorNvrInfoDlg.collectData = function () {
    this.set('id').set('scenicId').set('userName').set('cameraName').set('represent').set('userPassword').set('passagewayNumber').set('ipAddress').set('flowPort').set('flowUrl');
};

/**
 * 验证数据是否为空
 */
MonitorNvrInfoDlg.validate = function () {
    $('#monitorNvrInfoForm').data("bootstrapValidator").resetForm();
    $('#monitorNvrInfoForm').bootstrapValidator('validate');
    return $("#monitorNvrInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
MonitorNvrInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    console.log(JSON.stringify(this.monitorNvrInfoData));
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/monitorManage/add", function (data) {
        Feng.success("添加成功!");
        window.parent.MonitorNvr.table.refresh();
        MonitorNvrInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.monitorNvrInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
MonitorNvrInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/monitorManage/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.MonitorNvr.table.refresh();
        MonitorNvrInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.monitorNvrInfoData);
    ajax.start();
};

$(function () {
    Feng.initValidator("monitorNvrInfoForm", MonitorNvrInfoDlg.validateFields);
});

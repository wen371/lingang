/**
 * 终端详情对话框（可用于添加和修改对话框）
 */
var TerminalInfoDlg = {
    terminalInfoData: {},
    validateFields: {
        terminalNumber: {
            validators: {
                notEmpty: {
                    message: '终端编号不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
TerminalInfoDlg.clearData = function () {
    this.terminalInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TerminalInfoDlg.set = function (key, val) {
    this.terminalInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TerminalInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
TerminalInfoDlg.close = function () {
    parent.layer.closeAll();
};

/**
 * 收集数据
 */
TerminalInfoDlg.collectData = function () {
    this.set('id').set('terminalNumber').set('scenicId').set('explainInfo');
};

/**
 * 验证数据是否为空
 */
TerminalInfoDlg.validate = function () {
    $('#terminalInfoForm').data("bootstrapValidator").resetForm();
    $('#terminalInfoForm').bootstrapValidator('validate');
    return $("#terminalInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
TerminalInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    console.log(JSON.stringify(this.terminalInfoData));
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/terminalMgr/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Terminal.table.refresh();
        TerminalInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.terminalInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
TerminalInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/terminalMgr/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Terminal.table.refresh();
        TerminalInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.terminalInfoData);
    ajax.start();
};

$(function () {
    Feng.initValidator("terminalInfoForm", TerminalInfoDlg.validateFields);
});

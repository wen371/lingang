/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var ticketProductDetailAdd = {
    ticketProductDetailAddData: {},
    validateFields: {
        detailNum: {
            validators: {
                notEmpty: {
                    message: '数量不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
ticketProductDetailAdd.clearData = function () {
    this.ticketProductDetailAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ticketProductDetailAdd.set = function (key, val) {
    this.ticketProductDetailAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ticketProductDetailAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ticketProductDetailAdd.close = function () {
    parent.layer.close(window.parent.TicketProductDetail.layerIndex);
};

/**
 * 验证数据是否为空
 */
ticketProductDetailAdd.validate = function () {
    $('#ticketProductDetailInfoFrom').data("bootstrapValidator").resetForm();
    $('#ticketProductDetailInfoFrom').bootstrapValidator('validate');
    return $("#ticketProductDetailInfoFrom").data('bootstrapValidator').isValid();
};

/**
 * 收集数据
 */
ticketProductDetailAdd.collectData = function () {
    this.set('id').set('ticketProductId').set('num').set('detailNum').set('ticketTypeName').set('scenicId').set('ticketTypeId');
};

ticketProductDetailAdd.addSubmit=function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    };
    //console.log(JSON.stringify(this.ticketProductDetailAddData));
    var ajax = new $ax(Feng.ctxPath + "/ticketProduct/detailAdd", function (data) {
        console.info(data);
        if(data.code == '200'){
            Feng.success("添加成功!");
            window.parent.TicketProductDetail.table.refresh();
            window.parent.parent.TicketProduct.table.refresh();
            ticketProductDetailAdd.close();
        }else {
            Feng.error("添加失败："+data.message);
        }

    }, function (data) {
        Feng.error("添加失败!" );
    });
    ajax.set(this.ticketProductDetailAddData);
    ajax.start();
};

/**
 * 提交编辑票品票种
 */
ticketProductDetailAdd.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    };

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ticketProduct/editDetail", function (data) {
        Feng.success("修改成功!");
        window.parent.TicketProductDetail.search();
        window.parent.parent.TicketProduct.search();
        ticketProductDetailAdd.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set(this.ticketProductDetailAddData);
    ajax.start();
};
$(function () {
    Feng.initValidator("ticketProductDetailInfoFrom", ticketProductDetailAdd.validateFields);
});



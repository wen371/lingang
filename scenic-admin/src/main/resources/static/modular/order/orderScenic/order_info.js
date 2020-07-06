var OrderScenicInfo = {
    "orderScenicData": {}
}
/**
 * 清除数据
 */
OrderScenicInfo.clearData = function () {
    this.orderScenicData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderScenicInfo.set = function (key, val) {
    this.orderScenicData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderScenicInfo.get = function (key) {
    return $("#" + key).val();
};
/**
 * 关闭此对话框
 */
OrderScenicInfo.close = function () {
    parent.layer.close(window.parent.OrderScenic.layerIndex);
};
/**
 * 收集数据
 */
OrderScenicInfo.collectData = function () {
    this.set("id").set("useName").set("useCardType").set("usePhone").set("useCardNumber");
};

OrderScenicInfo.editSubmit = function () {
    this.clearData();
    this.collectData();
    var tip = checkCard(this.orderScenicData['useCardNumber']);
    if(tip){
        Feng.error(tip);
        return;
    }
    jQuery.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        url : Feng.ctxPath + "/orderScenicManage/edit",
        data : this.orderScenicData,
        success : function(resp) {
            if(resp.code=="200"){
                Feng.success("修改成功!");
                window.parent.OrderScenic.search();
                OrderScenicInfo.close();
            }else {
                Feng.error("修改失败,请联系管理员!");
            }

        },
        error : function() {
            Feng.error("修改失败!");
        }
    });
}
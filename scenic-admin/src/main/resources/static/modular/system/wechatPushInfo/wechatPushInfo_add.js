var WechatPushInfoAdd = {
    wechatPushInfoAddData: {},
    validateFields: {
        
    }
};

/**
 * 清除数据
 */
WechatPushInfoAdd.clearData = function () {
    this.wechatPushInfoAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WechatPushInfoAdd.set = function (key, val) {
    this.wechatPushInfoAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WechatPushInfoAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
WechatPushInfoAdd.close = function () {
    parent.layer.close(window.parent.WechatPushInfo.layerIndex);
};

/**
 * 收集数据
 */
WechatPushInfoAdd.collectData = function () {
    this.set('id').set('category').set('instruction').set('name').set('operate');
};


WechatPushInfoAdd.addPhotoSubmit=function () {
    this.clearData();
    this.collectData();

    var ajax = new $ax(Feng.ctxPath + "/wechatPushInfo/add", function (data) {
        if(data.code == 200){
            Feng.success("添加成功!");
            window.parent.WechatPushInfo.table.refresh();
            WechatPushInfoAdd.close();
        }else {
            Feng.error("添加失败!");
        }
    }, function (data) {
        Feng.error("添加失败!" );
    });
    ajax.set(this.wechatPushInfoAddData);
    ajax.start();
};


/**
 * 提交编辑活动信息
 */
WechatPushInfoAdd.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/wechatPushInfo/edit", function (data) {
        if(data.code == 200){
            Feng.success("修改成功!");
            window.parent.WechatPushInfo.table.refresh();
            WechatPushInfoAdd.close();
        }else {
            Feng.error("修改失败!");
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set(this.wechatPushInfoAddData);
    ajax.start();
};
/**
 * Created by huangfeng on 2018/1/3.
 */

var StopInfo = {
    workpointInfoData: {},
};

/**
 * 清除数据
 */
StopInfo.clearData = function () {
    this.workpointInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StopInfo.set = function (key, val) {
    this.workpointInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StopInfo.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StopInfo.close = function () {
    parent.layer.close(window.parent.StopList.layerIndex);
}


/**
 * 提交新增
 */
StopInfo.inSubmit = function () {
    //提交信息
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var orgCode =$("#orgCode").val();
    if(startTime.length==0 ||endTime.length==0 ){
        Feng.error("请选择开始时间和结束时间");
        return false;
    }
    var ajax = new $ax(Feng.ctxPath + "/sysStop/inSubmit/"+startTime+"/"+endTime+"/"+orgCode, function (data) {
        if(data.code=='500'){
            Feng.error("当前机构已创建停机服务,请编辑!");
        }else{
            Feng.success("配置成功!");
            window.parent.StopList.table.refresh();
            StopInfo.close();
        }
    }, function (data) {
        Feng.error("配置失败,时间格式错误!");
    });
    ajax.start();
}

/**
 * 提交修改
 */
StopInfo.upSubmit = function () {
    //提交信息
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var id =$("#id").val();
    if(startTime.length==0 ||endTime.length==0 ){
        Feng.error("请选择开始时间和结束时间");
        return false;
    }
    var ajax = new $ax(Feng.ctxPath + "/sysStop/upSubmit/"+startTime+"/"+endTime+"/"+id, function (data) {
        Feng.success("配置成功!");
        window.parent.StopList.table.refresh();
        StopInfo.close();
    }, function (data) {
        Feng.error("配置失败,时间格式错误!");
    });
    ajax.start();
}
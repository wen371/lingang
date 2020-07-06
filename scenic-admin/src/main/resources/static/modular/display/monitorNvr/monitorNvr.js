var MonitorNvr = {
    id: "MonitorNvrTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
MonitorNvr.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false,align: 'center', valign: 'middle'},
        {title: '归属景区', field: 'scenicName', align: 'center', valign: 'middle'},
        {title: '设备登录账号', field: 'userName', align: 'center', valign: 'middle'},
        {title: '设备名称', field: 'cameraName', align: 'center', valign: 'middle'},
        {title: '设备描述', field: 'represent', align: 'center', valign: 'middle'},
        {title: 'NVR设备IP地址', field: 'ipAddress', align: 'center', valign: 'middle'},
        {title: '端口', field: 'flowPort', align: 'center', valign: 'middle'}
    ];
    return columns;
};

/**
 * 检查是否选中
 */
MonitorNvr.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        MonitorNvr.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
MonitorNvr.openAddMonitorNvr = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/monitorManage/toAdd'
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 点击修改
 */
MonitorNvr.openEditMonitorNvr = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/monitorManage/toEdit/' + this.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 删除
 */
MonitorNvr.delMonitorNvr = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/monitorManage/delete", function () {
                Feng.success("删除成功!");
                MonitorNvr.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", MonitorNvr.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否删除?",operation);
    }
};

/**
 * 搜索
 */
MonitorNvr.search = function () {
    var queryData = {};
    queryData['scenicIdParam'] = $("#scenicId").val();
    MonitorNvr.table.refresh({query: queryData});
}

MonitorNvr.resetSearch = function () {
    $("#scenicId").val("");
    MonitorNvr.search();
};

$(function () {
    var defaultColunms = MonitorNvr.initColumn();
    var table = new BSTable(MonitorNvr.id, "/monitorManage/list", defaultColunms);
    table.setPaginationType("server");
    MonitorNvr.table = table.init();
});
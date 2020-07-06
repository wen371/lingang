
var Terminal = {
    id: "TerminalTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
Terminal.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '终端编号', field: 'terminalNumber', align: 'center', valign: 'middle'},
        {title: '归属景区', field: 'scenicName', align: 'center', valign: 'middle'},
        {title: '说明', field: 'explainInfo', align: 'center', valign: 'middle'}
    ];
    return columns;
};

/**
 * 检查是否选中
 */
Terminal.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Terminal.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
Terminal.openAddTerminal = function () {
    var index = layer.open({
        type: 2,
        title: '添加终端',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/terminalMgr/addTerminal'
    });
    this.layerIndex = index;
};

/**
 * 点击修改
 */
Terminal.openEditTerminal = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改终端',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/terminalMgr/editTerminal/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
Terminal.delTerminal = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/terminalMgr/delete", function () {
                Feng.success("删除成功!");
                Terminal.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", Terminal.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否删除终端?",operation);
    }
};

/**
 * 搜索
 */
Terminal.search = function () {
    var queryData = {};
    queryData['terminalNumber'] = $("#terminalNumber").val();
    Terminal.table.refresh({query: queryData});
}

Terminal.resetSearch = function () {
    $("#terminalNumber").val("");
    Terminal.search();
};

$(function () {
    var defaultColunms = Terminal.initColumn();
    var table = new BSTable(Terminal.id, "/terminalMgr/list", defaultColunms);
    table.setPaginationType("server");
    Terminal.table = table.init();
});
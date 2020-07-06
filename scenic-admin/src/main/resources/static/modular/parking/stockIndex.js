var stock = {
    id: "stockTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
stock.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '停车场名称', field: 'areaName', align: 'center', valign: 'middle'},
        {title: '现金收入', field: 'money', align: 'center', valign: 'middle'},
        {title: '第三方收入', field: 'onlineMoney', align: 'center', valign: 'middle'},
        // {title: '天入库', field: 'inbound', align: 'center', valign: 'middle'},
        // {title: '天出库', field: 'outbound', align: 'center', valign: 'middle'},
        {title: '停车数', field: 'freeParking', align: 'center', valign: 'middle'},
        {title: '登记时间', field: 'registerTimeStr', align: 'center', valign: 'middle'},
        {
            title: '操作', field:'caozuo',align:'center',valign:'middle',
            formatter:function(value, row, index){
                var  a='<button type=button onclick=stock.updateView("'+row.id+'")  class="btn btn-primary ">编辑</button>'+" "+
                    '<button type=button onclick=stock.del("'+row.id+'")  class="btn btn-primary ">删除</button>';
                return a
            }
        }];
    return columns;
};

stock.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        stock.seItem = selected[0];
        return true;
    }
};

/**
 * 点击编辑
 * @param userId 管理员id
 */
stock.updateView = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑停车场信息',
        stock: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/parkingStock/editStock/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 删除
 */
stock.del = function (id) {
    var  operation = function () {
        var ajax = new $ax(Feng.ctxPath+"/parkingStock/delete",function () {
            Feng.success("删除成功!");
            stock.search();
        },function (data) {
            Feng.error("删除失败!")
        });
        ajax.set("id",id)
        ajax.start();
    };
    Feng.confirm("是否确认删除?",operation);
};


/**
 * 点击新增活动信息
 */
stock.openAddaddActivity = function () {
    var index = layer.open({
        type: 2,
        title: '添加停车场',
        stock: ['500px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/parkingStock/addStock'
    });
    this.layerIndex = index;
    layer.full(index);
};

stock.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    stock.table.refresh({query: queryData});
};
stock.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    stock.search();
};

$(function () {
    var defaultColunms = stock.initColumn();
    var table = new BSTable("stockTable", "/parkingStock/list", defaultColunms);
    table.setPaginationType("server");
    stock.table = table.init();
})
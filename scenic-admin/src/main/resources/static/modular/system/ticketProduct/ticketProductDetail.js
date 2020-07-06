/**
 * 通知管理初始化
 */
var TicketProductDetail = {
    id: "TicketProductDetailTable",	//表格id
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
TicketProductDetail.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        /*{title: '序号',  align: 'center', valign: 'middle',formatter: function(value,row,index) {return index+1}},*/
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '票种名称', field: 'ticketTypeName', align: 'center', valign: 'middle'},
        {title: '景区名称', field: 'spotName', align: 'center', valign: 'middle'},
        {title: '数量', field: 'num', align: 'center', valign: 'middle'},
        {
            title: '操作', field: 'caozuo', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if($("#rack").val()!=1){
                    var a = '<button type=button onclick=TicketProductDetail.editTicketProductDetail("' + row.id + '")  class="btn btn-primary ">更改数量</button>'+" "+
                        '<button type=button onclick=TicketProductDetail.delTicketProductDetail("' + row.id + '")  class="btn btn-primary ">删除</button>';
                    return a
                }else{
                    return;
                }}},
                /*var a = '<button type=button onclick=TicketProductDetail.editTicketProductDetail("' + row.id + '")  class="btn btn-primary ">编辑</button>'+" "+
                    '<button type=button onclick=TicketProductDetail.delTicketProductDetail("' + row.id + '")  class="btn btn-primary ">删除</button>';
                return a*/
    ];
};


TicketProductDetail.search = function () {
    var queryData = {};
    queryData['deptId'] = TicketProductDetail.deptId;
    TicketProductDetail.table.refresh({query: queryData});
};

/**
 * 点击新增票品
 */
TicketProductDetail.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加票种',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ticketProduct/detail_add?id='+$("#ticketProductId").val()
    });
    this.layerIndex = index;
};

/**
 * 编辑票品票种
 */
TicketProductDetail.editTicketProductDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑票品票种',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ticketProduct/editTicketProductDetail/' + id
    });
    this.layerIndex = index;
};

/**
 * 删除票品
 */
TicketProductDetail.delTicketProductDetail = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/ticketProduct/deleteDetail?ticketProductId="+$("#ticketProductId").val(), function () {
            Feng.success("删除成功!");
            TicketProductDetail.search();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id", id);
        ajax.start();
    };
    Feng.confirm("是否确认删除?", operation);
};

$(function () {
    var defaultColunms = TicketProductDetail.initColumn();
    var table = new BSTable("TicketProductDetailTable", "/ticketProduct/TicketProductDetailList?id="+$("#ticketProductId").val(), defaultColunms);
    table.setPaginationType("server");
    TicketProductDetail.table = table.init();
});

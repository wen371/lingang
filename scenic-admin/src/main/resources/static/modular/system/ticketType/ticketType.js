/**
 * 通知管理初始化
 */
var TicketType = {
    id: "TicketTypeTable",	//表格id
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TicketType.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        /*{title: '序号',  align: 'center', valign: 'middle',formatter: function(value,row,index) {return index+1}},*/
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '票种名称', field: 'name', align: 'center', valign: 'middle'},
        {title: '景区名称', field: 'spotName', align: 'center', valign: 'middle'},
        {title: '价格', field: 'price', align: 'center', valign: 'middle'},
        /*{title: '提前预约天数 ', field: 'reservationDays', align: 'center', valign: 'middle'},*/
        // {title: '每天可预约人数', field: 'reservationNo', align: 'center', valign: 'middle'},
        /*{title: '是否需要预约', field: 'isReservation', align: 'center', valign: 'middle',
            formatter: function(value,row) {
                if(row.isReservation==1){
                    return '是';
                }else{
                    return '否';
                }}},*/
        {
            title: '操作', field: 'caozuo', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var a = '<button type=button onclick=TicketType.EditTicketType("' + row.id + '")  class="btn btn-primary ">编辑</button>'+" "+
                    '<button type=button onclick=TicketType.delTicketType("' + row.id + '")  class="btn btn-primary ">删除</button>';
                return a
            }
        }
    ];
};


TicketType.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['spotName'] = $('#spotName').val();
    TicketType.table.refresh({query: queryData});
};


TicketType.resetSearch = function () {
    $("#name").val("");
    $("#spotName").val("");
    TicketType.search();
};

/**
 * 点击新增
 */
TicketType.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加票种',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ticketType/ticketType_add'
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 编辑票种
 */
TicketType.EditTicketType = function (id) {
        var index = layer.open({
            type: 2,
            title: '编辑票种',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ticketType/editTicketType/' + id
        });
        this.layerIndex = index;
        layer.full(index);
};

/**
 * 删除票种
 */
TicketType.delTicketType = function (id) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/ticketType/delete", function () {
                Feng.success("删除成功!");
                TicketType.search();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", id);
            ajax.start();
        };
        Feng.confirm("是否确认删除?", operation);
};

$(function () {
    var defaultColunms = TicketType.initColumn();
    var table = new BSTable(TicketType.id, "/ticketType/list", defaultColunms);
    table.setPaginationType("server");
    TicketType.table = table.init();
});

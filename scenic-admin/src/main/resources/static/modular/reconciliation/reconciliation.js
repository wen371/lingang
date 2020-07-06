/**
 * 系统管理--用户管理的单例对象
 */
var Reconciliation = {
    id: "ReconciliationTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Reconciliation.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '订单号', field: 'orderNo', align: 'center', valign: 'middle'},
        {title: '票种名称', field: 'typeName', align: 'center', valign: 'middle'},
        {title: '票品名称', field: 'ticketName', align: 'center', valign: 'middle'},
        {title: '景区名称', field: 'scenicName', align: 'center', valign: 'middle'},
        // {title: '使用人', field: 'useName', align: 'center', valign: 'middle'},
        // {title: '使用人手机', field: 'usePhone', align: 'center', valign: 'middle'},
        {title: '游玩时间', field: 'date', align: 'center', valign: 'middle'},
        {title: '核销时间', field: 'checkTime', align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: true, formatter:function (value,options,rowObject){
                if(value == 0) { return "未使用"}
                else if(value == 1) { return "已使用"}
                else if(value == 4) { return "退款申请中"}
                else if(value == 5) { return "已退款"}
                else if(value == 10) { return "已过期"}
                else if(value == 11) { return "订单超时未支付"};
            } }
    ];
    return columns;
};


/**
 * 引入字典
 */
var Dict = getDict("order");
/**
 * 检查是否选中
 */
Reconciliation.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Reconciliation.seItem = selected[0];
        return true;
    }
};

Reconciliation.getResult = function(){
    var ajax = new $ax(Feng.ctxPath + "/reconciliation/check", function (data) {
        if(data.flag){
            console.info(data);
            Reconciliation.export();
        }else {
            alert(data.message)
        }
    }, function (data) {

    });
    ajax.set("orderNo1",$("#orderNo").val());
    ajax.set("beginTime1",$("#beginTime").val());
    ajax.set("endTime1",$("#endTime").val());
    ajax.set("status1",$("#status").val());
    ajax.start();

};

Reconciliation.export = function  () {

    var form = document.createElement('form');
    form.action = Feng.ctxPath + '/reconciliation/export';
    form.method = 'post';
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'orderNo1';
    input.value = $("#orderNo").val();

    var input1 = document.createElement('input');
    input1.type = 'hidden';
    input1.name = 'beginTime1';
    input1.value = $("#beginTime").val();

    var input2 = document.createElement('input');
    input2.type = 'hidden';
    input2.name = 'endTime1';
    input2.value = $("#endTime").val();

    var input3 = document.createElement('input');
    input3.type = 'hidden';
    input3.name = 'status1';
    input3.value = $("#status").val();
    form.appendChild(input);
    form.appendChild(input1);
    form.appendChild(input2);
    form.appendChild(input3);
    $(document.body).append(form);
    form.submit();


};



Reconciliation.resetSearch = function () {
    $("#orderNo").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#status").val("");

    Reconciliation.search();
};

Reconciliation.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['status'] = $("#status").val();
    Reconciliation.table.refresh({query: queryData});
};




$(function () {
    var defaultColumns = Reconciliation.initColumn();
    var table = new BSTable("ReconciliationTable", "/reconciliation/list", defaultColumns);
    table.setPaginationType("server");
    Reconciliation.table = table.init();
});

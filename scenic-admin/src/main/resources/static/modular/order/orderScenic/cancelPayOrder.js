/**
 * 系统管理--用户管理的单例对象
 */
var CancelPayOrder = {
    id: "cancelOrderPayTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CancelPayOrder.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '订单号', field: 'orderNo', align: 'center', valign: 'middle'},
        {title: '票品名称', field: 'ticketName', align: 'center', valign: 'middle'},
        {title: '购买数量', field: 'num', align: 'center', valign: 'middle'},
        {title: '订单金额', field: 'orderMoney', align: 'center', valign: 'middle'},
        {title: '用户名称', field: 'userName', align: 'center', valign: 'middle'},
        {title: '用户电话', field: 'userPhone', align: 'center', valign: 'middle'},
        {title: '游玩开始时间', field: 'firstTime', align: 'center', valign: 'middle'},
        {
            title: '订单状态', field: 'status', align: 'center', valign: 'middle', formatter: function (value) {
                if(value == 4){
                    return "申请取消中";
                }
            }
        },
        /*{title: '用户电话', field: 'userPhone', align: 'center', valign: 'middle'},*/
        {title: '操作', field: 'operation', align: 'center', valign: 'middle',formatter:function (value, row, index) {
            var  a='<button type=button onclick=CancelPayOrder.confirmCancel("'+row.orderNo+'","'+row.id+'")  class="btn btn-primary ">确认退款</button>';
            return a
        }}
    ];
    return columns;
};

CancelPayOrder.confirmCancel = function (orderNo,id) {
    //alert(Feng.ctxPath);
    var ajax = new $ax(Feng.ctxPath + "/orderScenicManage/aliRefund?orderNo="+orderNo, function (data) {
        //alert("退款成功!");
       /* if(data.success == '0'){
            alert(data.msg);
        }else if(data.success == '1'){
            //alert("退款成功!");
            CancelPayOrder.doCancel(id);
        }*/
        if(data.code == '0'){
            Feng.success("退款成功!")
            CancelPayOrder.table.refresh();
        }else {
            Feng.error(data.message);
            CancelPayOrder.table.refresh();
        }
    }, function (data) {
        Feng.error("退款失败!可能是网络不通导致的!请联系管理员!");
        CancelPayOrder.table.refresh();
    });
    //ajax.set(JSON.stringify(this.productAddData));
    ajax.start();
};

CancelPayOrder.doCancel = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/cancelPayOrder/doCancel?id="+id, function (data) {
        if(data.success == '0'){
            alert(data.msg);
        }else if(data.success == '1'){
            alert("退款成功!");
            CancelPayOrder.table.refresh();
        }
    }, function (data) {
        Feng.error("退款失败!可能是网络不通导致的!请联系管理员!");
    });
    //ajax.set(JSON.stringify(this.productAddData));
    ajax.start();
};
/**
 * 引入字典
 */
//var Dict = getDict("product");
/**
 * 检查是否选中
 */
CancelPayOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        CancelPayOrder.seItem = selected[0];
        return true;
    }
};

CancelPayOrder.resetSearch = function () {
    $("#orderNo").val("");

    CancelPayOrder.search();
};

CancelPayOrder.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    CancelPayOrder.table.refresh({query: queryData});
};

$(function () {
    var defaultColumns = CancelPayOrder.initColumn();
    var table = new BSTable("cancelOrderPayTable", "/cancelPayOrder/list", defaultColumns);
    table.setPaginationType("server");
    CancelPayOrder.table = table.init();
});

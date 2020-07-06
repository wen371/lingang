/**
 * 系统管理--用户管理的单例对象
 */
var OrderScenicDetail = {
    id: "orderScenicDetailTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderScenicDetail.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '票品名称', field: 'ticketName', align: 'center', valign: 'middle'},
        {title: '票品金额', field: 'ticketPrice', align: 'center', valign: 'middle'},
        {title: '数量', field: 'num', align: 'center', valign: 'middle'},
        {
            title: '状态', field: 'status', align: 'center', valign: 'middle', formatter: function (value) {
                return DictOrder[this.field][value]
            }
        },

    ];
    return columns;
};

var DictOrder = getDict("orderScenicDetail");

/**
 * 引入字典
 */
var Dict = getDict("orderDetail");
/**
 * 检查是否选中
 */
OrderScenicDetail.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        OrderScenicDetail.seItem = selected[0];
        return true;
    }
};

OrderScenicDetail.resetSearch = function () {
    $("#orderNo").val("");
    $("#productName").val("");
    OrderScenicDetail.search();
};

OrderScenicDetail.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['productName'] = $("#productName").val();
    OrderScenicDetail.table.refresh({query: queryData});
};


OrderScenicDetail.openDetail = function () {
    if(!this.check()){
        return;
    }
    var index = layer.open({
        type: 2,
        title: '订单详情列表',
        area: ['830px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderScenicManage/orderTicketDetail?orderDetailId='+OrderScenicDetail.seItem.id
    });
    this.layerIndex = index;
    layer.full(index);
}

$(function () {
    var defaultColumns = OrderScenicDetail.initColumn();
    console.info("orderId : " + $("#orderId").val())
    var table = new BSTable("orderScenicDetailTable", "/orderScenicManage/orderDetailList?orderId="+$("#orderId").val(), defaultColumns);
    table.setPaginationType("server");
    OrderScenicDetail.table = table.init();
});

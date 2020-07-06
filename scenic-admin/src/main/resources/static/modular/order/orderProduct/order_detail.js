/**
 * 系统管理--用户管理的单例对象
 */
var OrderProductDetail = {
    id: "orderProductDetailTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderProductDetail.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '产品名称', field: 'productName', align: 'center', valign: 'middle'},
        {title: '产品金额', field: 'productPrice', align: 'center', valign: 'middle'},
        {title: '数量', field: 'num', align: 'center', valign: 'middle'}
        // {
        //     title: '状态', field: 'status', align: 'center', valign: 'middle', formatter: function (value) {
        //         return DictOrderDetail[this.field][value]
        //     }
        // }
    ];
    return columns;
};


/**
 * 引入字典
 */
var DictCalculate = getDict("calculate");
var DictOrderDetail = getDict("orderDetail");
/**
 * 检查是否选中
 */
OrderProductDetail.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        OrderProductDetail.seItem = selected[0];
        return true;
    }
};

OrderProductDetail.resetSearch = function () {
    $("#orderNo").val("");
    $("#productName").val("");
    OrderProductDetail.search();
};

OrderProductDetail.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['productName'] = $("#productName").val();
    OrderProductDetail.table.refresh({query: queryData});
};


OrderProductDetail.openDetail = function () {
    if(!this.check()){
        return;
    }
    var index = layer.open({
        type: 2,
        title: '订单详情列表',
        area: ['830px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderProductManage/orderSpec?orderDetailId='+this.seItem.id
    });
    this.layerIndex = index;
    layer.full(index);
}

$(function () {
    var defaultColumns = OrderProductDetail.initColumn();
    var table = new BSTable("orderProductDetailTable", "/orderProductManage/orderDetailList?orderId="+$("#orderId").val(), defaultColumns);
    table.setPaginationType("server");
    OrderProductDetail.table = table.init();
});

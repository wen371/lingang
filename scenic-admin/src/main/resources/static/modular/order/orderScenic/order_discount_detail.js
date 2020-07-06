/**
 * 系统管理--用户管理的单例对象
 */
var OrderDiscountDetail = {
    id: "orderDiscountDetailTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderDiscountDetail.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '优惠券ID', field: 'prizeId', align: 'center', valign: 'middle'},
        {title: '券码code', field: 'couponCode', align: 'center', valign: 'middle'},
        {title: '生效时间', field: 'startTime', align: 'center', valign: 'middle'},
        {
            title: '状态', field: 'status', align: 'center', valign: 'middle', formatter: function (value) {
                return Dict[this.field][value]
            }
        }
    ];
    return columns;
};


/**
 * 引入字典
 */
var Dict = getDict("orderDiscountDetail");

$(function () {
    var defaultColumns = OrderDiscountDetail.initColumn();
    var table = new BSTable("orderDiscountDetailTable", "/orderScenicManage/OrderDiscountDetailList?orderNo="+$("#orderNo").val(), defaultColumns);
    table.setPaginationType("server");
    OrderDiscountDetail.table = table.init();
});

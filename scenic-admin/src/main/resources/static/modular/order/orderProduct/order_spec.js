/**
 * 系统管理--用户管理的单例对象
 */
var OrderProductSpec = {
    id: "orderProductSpecTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 引入字典
 */
var DictCalculate = getDict("calculate");
var DictOrderDetail = getDict("orderDetail");

/**
 * 初始化表格的列
 */
OrderProductSpec.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '产品名称', field: 'productName', align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', align: 'center', valign: 'middle'},
        {title: '押金金额', field: 'deposit', align: 'center', valign: 'middle'},
        {title: '计费方式', field: 'calculateType', align: 'center', valign: 'middle', formatter: function (value) {
                return DictCalculate[this.field][value]
            }},
        {title: '项目规格', field: 'specStr', align: 'center', valign: 'middle'},
        {title: '核销时间', field: 'reconciliationTime', align: 'center', valign: 'middle'},
        {
            title: '状态', field: 'status', align: 'center', valign: 'middle', formatter: function (value) {
                return DictOrderDetail[this.field][value]
            }
        }
    ];
    return columns;
};


/**
 * 引入字典
 */
/**
 * 检查是否选中
 */
OrderProductSpec.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        OrderProductSpec.seItem = selected[0];
        return true;
    }
};

/**
 * 小项目核销
 */
OrderProductSpec.verificate = function () {
    if(!this.check()){
        return;
    }
    if(this.seItem.status=='1'){
        Feng.error("该票已使用");
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/orderProduct/verificate/operation", function (data) {
        console.info(data);
        if(data.code=='200'){
            Feng.success("操作成功!");
            OrderProductSpec.resetSearch();
        }else {
            Feng.error("操作失败!" + data.message + "!" );

        }
    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!" );
    });
    ajax.set("id",this.seItem.id);
    ajax.set("orderId",this.seItem.orderId);
    ajax.start();
}

OrderProductSpec.resetSearch = function () {
    $("#orderNo").val("");
    $("#productName").val("");
    OrderProductSpec.search();
};

OrderProductSpec.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['productName'] = $("#productName").val();
    OrderProductSpec.table.refresh({query: queryData});
};

$(function () {
    var defaultColumns = OrderProductSpec.initColumn();
    var table = new BSTable("orderProductSpecTable", "/orderProductManage/orderSpecList?orderDetailId="+$("#orderDetailId").val(), defaultColumns);
    table.setPaginationType("server");
    OrderProductSpec.table = table.init();
});

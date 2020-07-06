/**
 * 系统管理--用户管理的单例对象
 */
var OrderTicketDetail = {
    id: "orderTicketDetailTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderTicketDetail.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '票品名称', field: 'ticketName', align: 'center', valign: 'middle'},
        {title: '票种名称', field: 'typeName', align: 'center', valign: 'middle'},
        {title: '核销时间', field: 'checkTime', align: 'center', valign: 'middle'},
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
var Dict = getDict("orderDetail");
/**
 * 检查是否选中
 */
OrderTicketDetail.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        OrderTicketDetail.seItem = selected[0];
        return true;
    }
};

OrderTicketDetail.resetSearch = function () {
    $("#orderNo").val("");
    $("#productName").val("");
    OrderTicketDetail.search();
};

OrderTicketDetail.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['productName'] = $("#productName").val();
    OrderTicketDetail.table.refresh({query: queryData});
};


OrderTicketDetail.verification = function () {
    if(!this.check()){
        return;
    }

    var operation = function(){
    if(OrderTicketDetail.seItem.status != '0'){
        Feng.error("该票状态不能核销");
        return;
    }
    //alert(111)
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/verification/web", function (data) {
        console.info(data);
        if(data.code=='200'){
            Feng.success("修改成功!");
            OrderTicketDetail.resetSearch();
            if(typeof(window.parent.parent.OrderScenic)=="undefined"){
                window.parent.OrderScenic.resetSearch();
            }
            if(typeof(window.parent.OrderScenic)=="undefined"){
                window.parent.parent.OrderScenic.resetSearch();
            }
        }else {
           Feng.error("修改失败!" + data.message + "!" );

        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set("id",OrderTicketDetail.seItem.id);
    ajax.set("orderId",OrderTicketDetail.seItem.orderId);
    ajax.start();

    };

    Feng.confirm("是否核销这个订单?", operation);

}

$(function () {
    var defaultColumns = OrderTicketDetail.initColumn();
    console.info("orderDetailId : " + $("#orderDetailId").val())
    var table = new BSTable("orderTicketDetailTable", "/orderScenicManage/orderTicketDetailList?orderDetailId="+$("#orderDetailId").val(), defaultColumns);
    table.setPaginationType("server");
    OrderTicketDetail.table = table.init();
});

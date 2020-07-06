/**
 * 系统管理--用户管理的单例对象
 */
var OrderProduct = {
    id: "orderProductTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderProduct.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '订单号', field: 'orderNo', align: 'center', valign: 'middle'},
        {title: '凭证号', field: 'certificateNo', align: 'center', valign: 'middle'},
        {title: '产品名', field: 'productName', align: 'center', valign: 'middle'},
        {title: '下单时间', field: 'createTime', align: 'center', valign: 'middle'},
        /*{title: '游玩时间', field: 'firstTime', align: 'center', valign: 'middle',formatter(value){
            return value.substring(0,10);
            }},*/
        {title: '开始使用时间', field: 'startCheckTime', align: 'center', valign: 'middle'},
        {title: '使用完成时间', field: 'endCheckTime', align: 'center', valign: 'middle'},
        {title: '付款方式', field: 'orderSource', align: 'center', valign: 'middle',formatter(value){
                if(value == '0'){
                    return '线上'
                }else if(value == '1'){
                    return '现金'
                }else {
                    return '第三方'
                }
            }},
        /*{title: '购买数量', field: 'num', align: 'center', valign: 'middle'},*/
        {title: '支付金额', field: 'payMoney', align: 'center', valign: 'middle'},
        {title: '结算金额', field: 'sumMoney', align: 'center', valign: 'middle'},
        {title: '票数', field: 'num', align: 'center', valign: 'middle'},
        {title: '核销数', field: 'useNum', align: 'center', valign: 'middle'},
        // {title: '用户名称', field: 'userNickName', align: 'center', valign: 'middle'},
        // {title: '用户电话', field: 'userPhone', align: 'center', valign: 'middle'},
        {
            title: '订单状态', field: 'status', align: 'center', valign: 'middle', formatter: function (value) {
                return DictOrder[this.field][value]
            }
        }
    ];
    return columns;
};


/**
 * 引入字典
 */
var DictOrder = getDict("order");
/**
 * 检查是否选中
 */
OrderProduct.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        OrderProduct.seItem = selected[0];
        return true;
    }
};

OrderProduct.resetSearch = function () {
    $("#orderNo").val("");
    $("#productName").val("");
    $("#startDate").val("");
    $("#endDate").val("");
    $("#status").val("");

    OrderProduct.search();
};

OrderProduct.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['productName'] = $("#productName").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    queryData['status'] = $("#status").val();
    OrderProduct.initMyData();
    OrderProduct.table.refresh({query: queryData});
};


OrderProduct.openDetail = function () {
    if(!this.check()){
        return;
    }
    var index = layer.open({
        type: 2,
        title: '订单详情列表',
        area: ['830px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderProductManage/orderDetail?orderId='+OrderProduct.seItem.id
    });
    this.layerIndex = index;
    layer.full(index);
}

OrderProduct.applyRefund = function () {//
    if(this.check()){
        if(OrderProduct.seItem.status == '3'||OrderProduct.seItem.status == '10'||OrderProduct.seItem.status == '14'){
            var operation = function () {
                var ajax = new $ax(Feng.ctxPath+"/orderProductManage/applyRefund",function (data) {
                    if(data.code == '400'){
                        Feng.error(data.message);
                    }else{
                        Feng.success("操作成功!");
                        location.reload();
                    }
                    OrderProduct.search();
                },function (data) {
                    Feng.error("操作失败!");
                });
                ajax.set("id",OrderProduct.seItem.id)
                ajax.start();
            }
            Feng.confirm("是否确认退款？", operation);
        }else {
            Feng.error("该状态不支持退款！");
        }

    }

}

OrderProduct.closeTime = function () {
    if(this.check()){//类别 ( 0-门票，1-自行车， 2-观光车， 3-游船)',
        if(OrderProduct.seItem.category == '0'||OrderProduct.seItem.category == '2'){
            Feng.error("该订单不支持计时功能!");
            return;
        }
        if(OrderProduct.seItem.status != '8'){
            Feng.error("该订单状态不是使用中不能结束计时！");
            return;
        }
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath+"/orderProductManage/closeTime",function (data) {
                if(data.code == '400'){
                    Feng.error(data.message);
                }else{
                    Feng.success("操作成功!");
                }
                OrderProduct.search();
            },function (data) {
                Feng.error("操作失败!");
            });
            ajax.set("id",OrderProduct.seItem.id);
            ajax.start();
        }
        Feng.confirm("是否结束计时？", operation);
    }

}


$(function () {
    var defaultColumns = OrderProduct.initColumn();
    var table = new BSTable("orderProductTable", "/orderProductManage/list", defaultColumns);
    table.setPaginationType("server");
    OrderProduct.table = table.init();
});

OrderProduct.getResult = function(){
    var ajax = new $ax(Feng.ctxPath + "/orderProductManage/check", function (data) {
        if(data.flag){
            console.info(data);
            OrderProduct.export();
        }else {
            alert(data.message)
        }
    }, function (data) {

    });
    ajax.set("orderNo1",$("#orderNo").val());
    ajax.set("productName1",$("#productName").val());
    ajax.set("startDate1",$("#startDate").val());
    ajax.set("endDate1",$("#endDate").val());
    ajax.set("status1",$("#status").val());
    ajax.start();

};

OrderProduct.export = function  () {

    var form = document.createElement('form');
    form.action = Feng.ctxPath + '/orderProductManage/export';
    form.method = 'post';
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'orderNo1';
    input.value = $("#orderNo").val();

    var input1 = document.createElement('input');
    input1.type = 'hidden';
    input1.name = 'productName1';
    input1.value = $("#productName").val();

    var input2 = document.createElement('input');
    input2.type = 'hidden';
    input2.name = 'startDate1';
    input2.value = $("#startDate").val();

    var input3 = document.createElement('input');
    input3.type = 'hidden';
    input3.name = 'endDate1';
    input3.value = $("#endDate").val();

    var input4 = document.createElement('input');
    input4.type = 'hidden';
    input4.name = 'status1';
    input4.value = $("#status").val();

    form.appendChild(input);
    form.appendChild(input1);
    form.appendChild(input2);
    form.appendChild(input3);
    form.appendChild(input4);
    $(document.body).append(form);
    form.submit();

};

OrderProduct.initMyData = function  ()  {
    var ajax = new $ax(Feng.ctxPath + "/orderProductManage/initData",function (result) {
        // var result = result.result;
        $('#sumOrderNum').html(result.result.sumOrderNum);
        $('#sumNum').html(result.result.sumNum);
        $('#allPayMoney').html(result.result.allPayMoney);
        $('#allRefMoney').html(result.result.allRefMoney);
        $('#allVerMoney').html(result.result.allVerMoney);
        console.info("result :" + JSON.stringify(result.result))
    });
        ajax.set("orderNo1",$("#orderNo").val());
        ajax.set("productName1",$("#productName").val());
        ajax.set("startDate1",$("#startDate").val());
        ajax.set("endDate1",$("#endDate").val());
        ajax.set("status1",$("#status").val());
        ajax.start();
};

OrderProduct.wasteBill = function () {//
    if(this.check()){
        if(OrderProduct.seItem.orderSource == '1'||OrderProduct.seItem.orderSource == '2'){
            var operation = function () {
                var ajax = new $ax(Feng.ctxPath+"/orderProductManage/wasteBill",function (data) {
                    if(data.code == '400'){
                        Feng.error(data.message);
                    }else{
                        Feng.success("操作成功!");
                        location.reload();
                    }
                    OrderProduct.search();
                },function (data) {
                    Feng.error("操作失败!");
                });
                ajax.set("id",OrderProduct.seItem.id)
                ajax.start();
            }
            Feng.confirm("是否确认废单？", operation);
        }else {
            Feng.error("微信渠道不支持废单操作！");
        }

    }

};

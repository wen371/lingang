/**
 * 系统管理--用户管理的单例对象
 */
var OrderScenic = {
    id: "orderScenicTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderScenic.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        /*{title: '景区名称', field: 'scenicName', align: 'center', valign: 'middle'},*/
        {title: '订单号', field: 'orderNo', align: 'center', valign: 'middle'},
        {title: '凭证号', field: 'certificateNo', align: 'center', valign: 'middle'},
        {title: '票类', field: 'ticketName', align: 'center', valign: 'middle'},
        {title: '下单时间', field: 'createTime', align: 'center', valign: 'middle'},
        {title: '游玩时间', field: 'firstTime', align: 'center', valign: 'middle'},
        {title: '来源', field: 'orderSource', align: 'center', valign: 'middle'},
        {title: '订单金额', field: 'orderMoney', align: 'center', valign: 'middle'},
        {title: '用户名称', field: 'useName', align: 'center', valign: 'middle'},
        {title: '票数', field: 'num', align: 'center', valign: 'middle'},
        {title: '核销数', field: 'useNum', align: 'center', valign: 'middle'},
        {title: '用户电话', field: 'usePhone', align: 'center', valign: 'middle'},
       /* {title: '证件类型', field: 'useCardType', align: 'center', valign: 'middle', formatter: function (value) {
                return DictType[this.field][value]
            }},*/
        {title: '证件号码', field: 'useCardNumber', align: 'center', valign: 'middle'},
        {title: '出游时间', field: 'travelTimeType', align: 'center', valign: 'middle',formatter: function(value) {
                if(value=='1'){
                    return '上午';
                }else if(value =='2'){
                    return '下午';
                }else{
                    return '';
                }
            }},
        // {title: '游玩开始日期', field: 'firstTime', align: 'center', valign: 'middle'},
        // {title: '游玩结束日期', field: 'endTime', align: 'center', valign: 'middle'},
        {
            title: '状态', field: 'status', align: 'center', valign: 'middle', formatter: function (value) {
                return DictOrder[this.field][value]
            }
        },
        {title: '操作', field: 'orderNo', align: 'center', valign: 'middle',
            formatter:function (value,row){
                if(row.status == 3 || row.status == 10|| row.status == 14){
                    return ['<button id=detailButton type=button onclick="OrderScenic.refund(\''+row.orderNo+'\')"  class="btn btn-primary ">退款</button>'].join("");
                }
            }
        }
    ];
    return columns;
};


/**
 * 引入字典
 */
var DictOrder = getDict("order");
var DictType = getDict("cardType");


/**
 * 检查是否选中
 */
OrderScenic.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        OrderScenic.seItem = selected[0];
        return true;
    }
};

OrderScenic.resetSearch = function () {
    $("#orderNo").val("");
    $("#userName").val("");
    $("#userPhone").val("");
    $("#startDate").val("");
    $("#endDate").val("");
    $("#orderSource").val("");
    $("#status").val("");

    OrderScenic.search();
};

OrderScenic.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['userName'] = $("#userName").val();
    queryData['userPhone'] = $("#userPhone").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    queryData['orderSource'] = $("#orderSource").val();
    queryData['status'] = $("#status").val();
    console.info("queryData : " + JSON.stringify(queryData))
    OrderScenic.initMyData();
    OrderScenic.table.refresh({query: queryData});
};



OrderScenic.refund = function (orderNo) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/orderScenicManage/refundOrder", function (data) {
            if(data.code == 200){
                Feng.success("退款成功!");
                location.reload();
                OrderScenic.resetSearch();
            }else {
                Feng.error("退款失败!");
            }
        }, function (data) {
            Feng.error("退款失败!" );
        });
        ajax.set("orderNo",orderNo);
        ajax.start();
    };
    Feng.confirm("是否发起退款?",operation);

}





OrderScenic.openDetail = function () {
    if (!this.check()) {
        return;
    }
    var index = layer.open({
        type: 2,
        title: '订单详情列表',
        area: ['830px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderScenicManage/orderDetail?orderId=' + OrderScenic.seItem.id
    });
    this.layerIndex = index;
    layer.full(index);
}

OrderScenic.editOrder = function () {
    if (!this.check()) {
        return;
    }
    var index = layer.open({
        type: 2,
        title: '修改订单信息',
        area: ['830px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderScenicManage/editOrder?orderId=' + OrderScenic.seItem.id
    });
    this.layerIndex = index;
    layer.full(index);
}

OrderScenic.openDiscountOrder = function () {
    if (!this.check()) {
        return;
    }
    var index = layer.open({
        type: 2,
        title: '订单优惠券列表',
        area: ['830px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderScenicManage/openDiscountOrder/' + OrderScenic.seItem.orderNo
    });
    this.layerIndex = index;
    layer.full(index);
}

$(function () {
    var defaultColumns = OrderScenic.initColumn();
    var table = new BSTable("orderScenicTable", "/orderScenicManage/list", defaultColumns);
    table.setPaginationType("server");
    OrderScenic.table = table.init();
});

OrderScenic.getResult = function(){
    var ajax = new $ax(Feng.ctxPath + "/orderScenicManage/check", function (data) {
        if(data.flag){
            console.info(data);
            OrderScenic.export();
        }else {
            alert(data.message)
        }
    }, function (data) {

    });
    ajax.set("orderNo1",$("#orderNo").val());
    ajax.set("userName1",$("#userName").val());
    ajax.set("userPhone1",$("#userPhone").val());
    ajax.set("startDate1",$("#startDate").val());
    ajax.set("endDate1",$("#endDate").val());
    ajax.set("orderSource1",$("#orderSource").val());
    ajax.set("status1",$("#status").val());
    ajax.start();

};

OrderScenic.export = function  () {

    var form = document.createElement('form');
    form.action = Feng.ctxPath + '/orderScenicManage/export';
    form.method = 'post';
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'orderNo1';
    input.value = $("#orderNo").val();

    var input1 = document.createElement('input');
    input1.type = 'hidden';
    input1.name = 'userName1';
    input1.value = $("#userName").val();

    var input2 = document.createElement('input');
    input2.type = 'hidden';
    input2.name = 'userPhone1';
    input2.value = $("#userPhone").val();

    var input3 = document.createElement('input');
    input3.type = 'hidden';
    input3.name = 'startDate1';
    input3.value = $("#startDate").val();

    var input4 = document.createElement('input');
    input4.type = 'hidden';
    input4.name = 'endDate1';
    input4.value = $("#endDate").val();

    var input5 = document.createElement('input');
    input5.type = 'hidden';
    input5.name = 'orderSource1';
    input5.value = $("#orderSource").val();

    var input6 = document.createElement('input');
    input6.type = 'hidden';
    input6.name = 'status1';
    input6.value = $("#status").val();



    form.appendChild(input);
    form.appendChild(input1);
    form.appendChild(input2);
    form.appendChild(input3);
    form.appendChild(input4);
    form.appendChild(input5);
    form.appendChild(input6);

    $(document.body).append(form);
    form.submit();

};

OrderScenic.initMyData = function  ()  {
    var ajax = new $ax(Feng.ctxPath + "/orderScenicManage/initData",function (result) {
        // var result = result.result;
        $('#sumOrderNum').html(result.result.sumOrderNum);
        $('#sumNum').html(result.result.sumNum);
        $('#allPayMoney').html(result.result.allPayMoney);
        $('#allRefMoney').html(result.result.allRefMoney);
        $('#allVerMoney').html(result.result.allVerMoney);
        console.info("result :" + JSON.stringify(result.result))
    });
    ajax.set("orderNo1",$("#orderNo").val());
    ajax.set("userName1",$("#userName").val());
    ajax.set("userPhone1",$("#userPhone").val());
    ajax.set("startDate1",$("#startDate").val());
    ajax.set("endDate1",$("#endDate").val());
    ajax.set("orderSource1",$("#orderSource").val());
    ajax.set("status1",$("#status").val());
    ajax.start();
};
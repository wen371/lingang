/**
 * 系统管理--用户管理的单例对象
 */
var Product = {
    id: "productTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Product.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'projectProductName', align: 'center', valign: 'middle'},
        /*{title: '所属景区', field: 'ticketName', align: 'center', valign: 'middle'},*/
        // {title: '产品原价', field: 'originalPrice', align: 'center', valign: 'middle'},
        {title: '产品现价', field: 'presentPrice', align: 'center', valign: 'middle'},
        {title: '标题图', field: 'url', align: 'center', valign: 'middle',formatter:function (value) {
            if(value != '' && value != null){
                return "<img src='"+value+"' style='height:300px;width:200px;' />"
            }
        }},
        {title: '限购数量', field: 'maxnum', align: 'center', valign: 'middle'},
        /*{title: '起始日期', field: 'startTime', align: 'center', valign: 'middle'},
        {title: '结束日期', field: 'endTime', align: 'center', valign: 'middle'},*/
        {title: '当天售票结束时间', field: 'endTicket', align: 'center', valign: 'middle'},
        {title: '选择游玩时间', field: 'isChoose', align: 'center', valign: 'middle',formatter:function(value){
            if(value == '1'){
                return "是";
            }else{
                return "否";
            }
        }},
        {title: '类别', field: 'category', align: 'center', valign: 'middle',formatter:function(value){
                if(value == '0'){
                    return "门票";
                }else if(value == '1'){
                    return "自行车";
                }else if(value == '2'){
                    return "观光车";
                }else {
                    return "游船";
                }
            }},
        /*{title: '是套餐产品', field: 'isPackage', align: 'center', valign: 'middle',formatter: function (value) {
            if(value == '1'){
                return "是";
            }else{
                return "否";
            }
        }},*/
        {title: '操作', field: 'operation', align: 'center', valign: 'middle',formatter:function (value, row, index) {
            var  a='<button type=button onclick=Product.openDetail("'+row.id+'")  class="btn btn-primary ">查看-编辑</button>'+" "+
             '<button type=button onclick=Product.del("'+row.id+'")  class="btn btn-danger ">删除</button>';
            return a
        }}
        /*{
            title: '订单状态', field: 'status', align: 'center', valign: 'middle', formatter: function (value) {
                return Dict[this.field][value]
            }
        }*/
    ];
    return columns;
};

Product.del = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/product/deleteProduct/"+id, function (data) {
            if(data.success == '0'){
                alert(data.msg);
            }else if(data.success == '1'){
                alert("删除成功!");
                Product.table.refresh();
            }
        }, function (data) {
            Feng.error("删除失败!可能是网络不通导致的!");
        });
        //ajax.set(JSON.stringify(this.productAddData));
        ajax.start();
    };
    Feng.confirm("是否删除?",operation);

};
/**
 * 引入字典
 */
//var Dict = getDict("product");
/**
 * 检查是否选中
 */
Product.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Product.seItem = selected[0];
        return true;
    }
};

Product.resetSearch = function () {
    $("#projectProductName").val("");

    Product.search();
};

Product.search = function () {
    var queryData = {};
    queryData['projectProductName'] = $("#projectProductName").val();
    Product.table.refresh({query: queryData});
};


Product.openDetail = function (id) {
    /*if(!this.check()){
        return;
    }*/
    var index = layer.open({
        type: 2,
        title: '产品详情',
        area: ['830px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/product/openDetail/'+id
    });
    this.layerIndex = index;
    layer.full(index);
};

Product.add = function () {
    var index = layer.open({
        type: 2,
        title: '添加产品',
        area: ['830px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/product/initAdd'
    });
    this.layerIndex = index;
    layer.full(index);
};

$(function () {
    var defaultColumns = Product.initColumn();
    var table = new BSTable("productTable", "/product/list", defaultColumns);
    table.setPaginationType("server");
    Product.table = table.init();
});

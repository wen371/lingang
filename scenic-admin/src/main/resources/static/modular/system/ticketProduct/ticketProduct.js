/**
 * 通知管理初始化
 */
var TicketProduct = {
    id: "TicketProductTable",	//表格id
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TicketProduct.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        /*{title: '序号',  align: 'center', valign: 'middle',formatter: function(value,row,index) {return index+1}},*/
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '票品名称', field: 'name', align: 'center', valign: 'middle'},
        /*{title: '票品副标题', field: 'title', align: 'center', valign: 'middle'},*/
        {title:'票种ID',field: 'scenicIds',visible: false, align: 'center', valign: 'middle'},
        {title: '上下架', field: 'rack',align: 'center', valign: 'middle',
            formatter: function(value,row) {
                if(row.rack==0){
                    return '未上架';
                }else if(row.rack ==1){
                    return '上架';
                }else{
                    return '下架';
                }}},
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
        {
            title: '操作', field: 'caozuo', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (row.rack==0){
                    //console.log(JSON.stringify(row));
                    var a = '<button type=button onclick=TicketProduct.openDetailAdd("' + row.id + '")  class="btn btn-primary ">票种信息</button>'+" "+
                        '<button type=button onclick=TicketProduct.editTicketProduct("' + row.id + '")  class="btn btn-primary ">编辑票品</button>'+" "+
                        '<button type=button onclick=TicketProduct.seeDetailed("' + row.id + '")  class="btn btn-primary ">查看详细</button>'+" "+
                        '<button type=button onclick=TicketProduct.editRack("'+ row.id + '","' + row.scenicIds +'")  class="btn btn-primary ">上架</button>'+" "+
                        '<button type=button onclick=TicketProduct.editDayStock("' + row.id + '")  class="btn btn-primary ">日历库存</button>'+" "+
                        '<button type=button onclick=TicketProduct.delTicketProduct("' + row.id + '")  class="btn btn-primary ">删除</button>';
                    return a
                }else if (row.rack ==1) {
                    var a = '<button type=button onclick=TicketProduct.openDetailAdd("' + row.id + '")  class="btn btn-primary ">票种信息</button>'+" "+
                        '<button type=button onclick=TicketProduct.seeDetailed("' + row.id + '")  class="btn btn-primary ">查看详细</button>'+" "+
                        '<button type=button onclick=TicketProduct.editRack1("'+ row.id + '","' + row.scenicIds +'")  class="btn btn-primary ">下架</button>'+" "+
                    '<button type=button onclick=TicketProduct.editDayStock("' + row.id + '")  class="btn btn-primary ">日历库存</button>'+" ";
                    return a
                }else{
                    var a = '<button type=button onclick=TicketProduct.openDetailAdd("' + row.id + '")  class="btn btn-primary ">票种信息</button>'+" "+
                        '<button type=button onclick=TicketProduct.editTicketProduct("' + row.id + '")  class="btn btn-primary ">编辑票品</button>'+" "+
                        '<button type=button onclick=TicketProduct.seeDetailed("' + row.id + '")  class="btn btn-primary ">查看详细</button>'+" "+
                        '<button type=button onclick=TicketProduct.editRack("'+ row.id + '","' + row.scenicIds +'")  class="btn btn-primary ">上架</button>'+" "+
                        '<button type=button onclick=TicketProduct.editDayStock("' + row.id + '")  class="btn btn-primary ">日历库存</button>'+" "+
                        '<button type=button onclick=TicketProduct.delTicketProduct("' + row.id + '")  class="btn btn-primary ">删除</button>';
                    return a
                }

            }
        }
    ];
};


TicketProduct.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    TicketProduct.table.refresh({query: queryData});
};


TicketProduct.resetSearch = function () {
    $("#name").val("");
    TicketProduct.search();
};

/**
 * 点击新增票品
 */
TicketProduct.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加票品',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ticketProduct/ticketProduct_add'
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 编辑票品
 */
TicketProduct.editTicketProduct = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑票品',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ticketProduct/editTicketProduct/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 编辑日历库存
 */
TicketProduct.editDayStock = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑日历库存',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ticketProduct/editDayStock/0/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 查看详细票品信息
 */
TicketProduct.seeDetailed = function (id) {
    var index = layer.open({
        type: 2,
        title: '查看详细票品信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ticketProduct/seeDetailed/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 删除票品
 */
TicketProduct.delTicketProduct = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/ticketProduct/delete", function () {
            Feng.success("删除成功!");
            TicketProduct.search();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id", id);
        ajax.start();
    };
    Feng.confirm("是否确认删除?", operation);
};
/**
 * 上架票品
 */
TicketProduct.editRack = function (id,scenicIds) {
    /*var sIds = row.scenicIds;*/
    console.log("sids......................"+id+"....."+scenicIds);
    if (scenicIds != "null" && scenicIds != ""){
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/ticketProduct/editRack", function () {
                Feng.success("上架成功!");
                TicketProduct.search();
            }, function (data) {
                Feng.error("上架失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",id);
            ajax.start();
        };
        Feng.confirm("上架后不可更改票品信息,是否确认上架?", operation);
    }else {
        Feng.error("未添加票种不可上架!");
    }
};
/**
 * 下架票品
 */
TicketProduct.editRack1 = function (id,scenicIds) {
    if(scenicIds != null && scenicIds != ""){
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/ticketProduct/editRack1", function () {
                Feng.success("下架成功!");
                TicketProduct.search();
            }, function (data) {
                Feng.error("下架失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",id);
            ajax.start();
        };
        Feng.confirm("下架后不可更改票品信息,是否确认下架?", operation);
    }else {
        Feng.error("未添加票种不可下架!");
    }
};
/**
 * 点击新增票种信息
 */
TicketProduct.openDetailAdd = function (id) {
    var index = layer.open({
        type: 2,
        title: '票种信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ticketProduct/DetailList/'+id
    });
    this.layerIndex = index;
    layer.full(index);
};

$(function () {
    var defaultColunms = TicketProduct.initColumn();
    var table = new BSTable("TicketProductTable", "/ticketProduct/list", defaultColunms);
    table.setPaginationType("server");
    TicketProduct.table = table.init();
});

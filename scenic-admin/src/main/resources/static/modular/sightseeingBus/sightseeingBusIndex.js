var SightseeingBus = {
    id: "SightseeingBusTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
SightseeingBus.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '观光车名称', field: 'name', align: 'center', valign: 'middle'},
        // {title: '车牌号码', field: 'plateNumber', align: 'center', valign: 'middle'},
        {title: '班车停靠', field: 'positions', align: 'center', valign: 'middle',
            formatter:function (value,row,index) {
                if(value == "1"){
                    return "1号门"
                }
                if(value == "2"){
                    return "2号门"
                }
                if(value == "3"){
                    return "3号门"
                }
            }
        },
        {title: '班次时间', field: 'shift', align: 'center', valign: 'middle'},
        // {title: '班次名称', field: 'shiftName', align: 'center', valign: 'middle'},
        {title: '景区名称', field: 'scenicName', align: 'center', valign: 'middle'},
        {title: '简介', field: 'synopsis', align: 'center', valign: 'middle'},
        {
            title: '操作', field:'caozuo',align:'center',valign:'middle',
            formatter:function(value, row, index){
                var  a='<button type=button onclick=SightseeingBus.updateView("'+row.id+'")  class="btn btn-primary ">编辑</button>'+" "+
                    '<button type=button onclick=SightseeingBus.del("'+row.id+'")  class="btn btn-primary ">删除</button>';
                return a
            }
        }];
    return columns;
};

SightseeingBus.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        SightseeingBus.seItem = selected[0];
        return true;
    }
};

/**
 * 点击编辑
 * @param userId 管理员id
 */
SightseeingBus.updateView = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑观光车班次信息',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sightseeingBus/editSightseeingBus/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 删除
 */
SightseeingBus.del = function (id) {
    var  operation = function () {
        var ajax = new $ax(Feng.ctxPath+"/sightseeingBus/delete",function () {
            Feng.success("删除成功!");
            SightseeingBus.search();
        },function (data) {
            Feng.error("删除失败!")
        });
        ajax.set("id",id)
        ajax.start();
    };
    Feng.confirm("是否确认删除?",operation);
};


/**
 * 点击新增活动信息
 */
SightseeingBus.openAddaddActivity = function () {
    var index = layer.open({
        type: 2,
        title: '添加观光车班次',
        area: ['500px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sightseeingBus/addSightseeingBus'
    });
    this.layerIndex = index;
    layer.full(index);
};

SightseeingBus.search = function () {
    var queryData = {};
    queryData['positions'] = $("#positions").val();
    SightseeingBus.table.refresh({query: queryData});
};
SightseeingBus.resetSearch = function () {
    $("#positions").val("");
    SightseeingBus.search();
};

$(function () {
    var defaultColunms = SightseeingBus.initColumn();
    var table = new BSTable("SightseeingBusTable", "/sightseeingBus/list", defaultColunms);
    table.setPaginationType("server");
    SightseeingBus.table = table.init();
})
var area = {
    id: "areaTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
area.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '停车场名称', field: 'name', align: 'center', valign: 'middle'},
        // {title: '车牌号码', field: 'plateNumber', align: 'center', valign: 'middle'},
        {title: '总车位数', field: 'number', align: 'center', valign: 'middle'},
        {title: '景区名称', field: 'scenicName', align: 'center', valign: 'middle'},
        {
            title: '操作', field:'caozuo',align:'center',valign:'middle',
            formatter:function(value, row, index){
                var  a='<button type=button onclick=area.updateView("'+row.id+'")  class="btn btn-primary ">编辑</button>'+" "+
                    '<button type=button onclick=area.del("'+row.id+'")  class="btn btn-primary ">删除</button>';
                return a
            }
        }];
    return columns;
};

area.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        area.seItem = selected[0];
        return true;
    }
};

/**
 * 点击编辑
 * @param userId 管理员id
 */
area.updateView = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑停车场信息',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/parkingArea/editArea/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 删除
 */
area.del = function (id) {
    var  operation = function () {
        var ajax = new $ax(Feng.ctxPath+"/parkingArea/delete",function () {
            Feng.success("删除成功!");
            area.search();
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
area.openAddaddActivity = function () {
    var index = layer.open({
        type: 2,
        title: '添加停车场',
        area: ['500px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/parkingArea/addArea'
    });
    this.layerIndex = index;
    layer.full(index);
};

area.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    area.table.refresh({query: queryData});
};
area.resetSearch = function () {
    $("#name").val("");
    area.search();
};

$(function () {
    var defaultColunms = area.initColumn();
    var table = new BSTable("areaTable", "/parkingArea/list", defaultColunms);
    table.setPaginationType("server");
    area.table = table.init();
})
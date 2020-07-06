var ScenicSpot = {
    id: "ScenicSpotTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
ScenicSpot.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '景区名称', field: 'name', align: 'center', valign: 'middle'},
        {title: '景区地址', field: 'address', align: 'center', valign: 'middle'},
        /*{title: '等级', field: 'rank', align: 'center', valign: 'middle'},*/
        {
            title: '操作', field:'caozuo',align:'center',valign:'middle',
            formatter:function(value, row, index){
                var  a='<button type=button onclick=ScenicSpot.updateView("'+row.id+'")  class="btn btn-primary ">编辑</button>'+" "+
                    '<button type=button onclick=ScenicSpot.del("'+row.id+'")  class="btn btn-primary ">删除</button>';
                return a
            }
        }];
    return columns;
};

ScenicSpot.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ScenicSpot.seItem = selected[0];
        return true;
    }
};

/**
 * 点击编辑
 * @param userId 管理员id
 */
ScenicSpot.updateView = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑景区信息',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scenicSpot/editScenicSpot/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 删除
 */
ScenicSpot.del = function (id) {
    var  operation = function () {
        var ajax = new $ax(Feng.ctxPath+"/scenicSpot/delete",function () {
            Feng.success("删除成功!");
            ScenicSpot.search();
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
ScenicSpot.openAddaddActivity = function () {
    var index = layer.open({
        type: 2,
        title: '添加景区',
        area: ['500px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scenicSpot/addScenicSpot'
    });
    this.layerIndex = index;
    layer.full(index);
};

ScenicSpot.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    ScenicSpot.table.refresh({query: queryData});
};
ScenicSpot.resetSearch = function () {
    $("#name").val("");
    ScenicSpot.search();
};

$(function () {
    var defaultColunms = ScenicSpot.initColumn();
    var table = new BSTable("ScenicSpotTable", "/scenicSpot/list", defaultColunms);
    table.setPaginationType("server");
    ScenicSpot.table = table.init();
})
/**
 * 节日活动Routes管理的单例
 */
var Routes = {
    id: "routesTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Routes.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '线路名称', field: 'name', align: 'center', valign: 'middle'},
        {title: '简介', field: 'instruction', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle'},
        {title: '状态', field: 'status',visible: true, align: 'center', valign: 'middle',
            formatter:function (value , row) {
                if(row.status == 1){
                    return "<span style='color: green;'>已发布</span>";
                }else{
                    return "<span style='color: firebrick'>已撤销</span>";
                }
            }},
        {title: '操作', field: 'id', align: 'center', valign: 'middle',
            formatter:function (value,row){
                if(row.status == 1){
                    return ['<button id=detailButton type=button onclick="Routes.edit('+ row.id + ')"  class="btn btn-primary ">编辑</button>' +
                    '&nbsp;&nbsp;&nbsp; <button id=deleteButton type=button onclick="Routes.undoOrPublish('+ row.id +',2)"  class="btn btn-primary ">撤销</button>'].join("");
                }
                return ['<button id=detailButton type=button onclick="Routes.edit('+ row.id +')"  class="btn btn-primary ">编辑</button>' +
                '&nbsp;&nbsp;&nbsp; <button id=deleteButton type=button onclick="Routes.undoOrPublish('+ row.id +',1)"  class="btn btn-primary ">发布 </button>'].join("");
            }
        }
    ];
    return columns;
};

/**
 * Routes查询
 */
Routes.query = function(){
    var queryData = {};
    queryData['name'] = $('#routesName').val();
    Routes.table.refresh({query:queryData});
};

/**
 * 撤销or发布
 * @param id
 * @param type
 */
Routes.undoOrPublish = function (id , type) {
    var data = {
        status : type,
        id : id
    };
    var jsonString = JSON.stringify(data);
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        dataType: "JSON",
        url :   Feng.ctxPath + "/routes/editStatus",
        data:  jsonString,
        contentType: "application/json; charset=utf-8",
        error : function() {
            Feng.error("修改状态失败!" );
            console.log("change status Routes  FAILED:");
        },
        success : function(data) {
            if(data.msg == '0'){
                Feng.success("修改成功!");
                Routes.table.refresh();
            }
        }
    });
};

/**
 * 新增 线路推荐
 */
Routes.addNew = function () {
    var index = layer.open({
        type: 2,
        title: '新增线路推荐',
        area: ['1000px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/routes/addPage'
    });
    this.layerIndex = index;
};

/**
 * 线路推荐的编辑
 * @param RoutesId
 * @param status
 */
Routes.edit = function (RoutesId) {
    console.log("your Routes id:",RoutesId);
    var index = layer.open({
        type: 2,
        title: '编辑节庆活动',
        area: ['1000px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/routes/editPage/' + RoutesId
    });
    this.layerIndex = index;
};

$(function () {
    var defaultColunms = Routes.initColumn();
    var table = new BSTable(Routes.id, "/routes/list", defaultColunms);
    table.setPaginationType("server");
    Routes.table = table.init();

});

/**
 * 检查是否选中
 */
Routes.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的一行!");
        return false;
    } else {
        Routes.seItem = selected[0];
        return true;
    }
};
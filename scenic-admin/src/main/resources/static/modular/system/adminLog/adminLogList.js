/**
 * 产品管理
 */
var AdminLog = {
    id: "adminLogs",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AdminLog.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作时间', field: 'createTime', align: 'center', valign: 'middle', sortable: false},
        {title: '操作人', field: 'userName', align: 'center', valign: 'middle', sortable: false},
        {title: '操作描述', field: 'describe', align: 'center', valign: 'middle', sortable: false,formatter: function(value) {
                return Feng.stringTruncat(value, 20);
            }}
    ];
    return columns;
};

/**
 * 重制
 */
AdminLog.resetSearch = function () {
    $("#describe").val("");
    $("#createTime").val("");
    AdminLog.search();
};

/**
 * 查询
 */
AdminLog.search = function () {
    var queryData = AdminLog.getSearchInfo();
    if(queryData == null){
        return;
    }
    AdminLog.table.refresh({query: queryData});
};

AdminLog.getSearchInfo = function() {
    var queryData = {};
    queryData['createTime'] =$("#createTime").val().trim();
    queryData['describe'] = $("#describe").val().trim();

    return queryData;
};

/**
 * 报表导出
 */
/*AdminLog.exportCsv = function() {
    var queryData = AdminLog.getSearchInfo();
    if(queryData == null){
        return;
    }
    jQuery.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        url : Feng.ctxPath + "/adminlog/  未填  ",
        data : queryData,
        timeout: 6000,
        success : function(resp) {
            if(resp.code == '100'){
                window.location.href = Feng.ctxPath + "/adminlog/ 未填  ?salesmanReportSearchJson="+JSON.stringify(queryData);
            } else{
                Feng.info("暂无数据!");
            }
        },
        error : function() {
            Feng.error("导出提交失败!" );
        }
    });
};*/

$(function () {
    var defaultColunms = AdminLog.initColumn();
    var table = new BSTable("adminLogs", "/adminlog/list", defaultColunms);
    table.setPaginationType("server");
    AdminLog.table = table.init();
});

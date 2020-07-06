/**
 * Created by huangfeng on 2018/1/9.
 */
/**
 * 通知公告-单例对象
 */
var OptNotice = {
    id: "optNoticeTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId: 0
};

/**
 * 初始化表格的列
 */
OptNotice.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '标题', field: 'title', align: 'center', valign: 'middle', sortable: false},
        {title: '发布时间', field: 'issueTime', align: 'center', valign: 'middle', sortable: false},
        {title: '最后编辑时间', field: 'modifyTime', align: 'center', valign: 'middle', sortable: false},
        {title: '发布状态', field: 'statusName', align: 'center', valign: 'middle'}];
    return columns;
};

/**
 * 检查是否选中
 */
OptNotice.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        OptNotice.seItem = selected[0];
        return true;
    }
};

/**
 * 点击新增按钮
 */
OptNotice.addNotice = function () {

    var index = layer.open({
        type: 2,
        title: '新增通知公告',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/optNotice/notice_add/'
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 编辑
 */
OptNotice.editNotice = function () {
    if (this.check()) {
        var status = this.seItem.status;
        if(status == 1){
            Feng.info("已发布的通知公告不允许编辑！");
            return;
        }
        var index = layer.open({
            type: 2,
            title: '编辑通知公告',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/optNotice/notice_edit/' + this.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 取消发布/发布
 */
OptNotice.updateNotice = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/optNotice/upStatus", function (data) {
                if(data.code == 200){
                    Feng.success("修改成功!");
                    OptNotice.table.refresh();
                } else{
                    Feng.error("修改失败!" + data.message + "!");
                }
            }, function (data) {
                Feng.error("修改失败!" + data.responseJSON.message + "!");
            });
            ajax.set("optNoticeId", OptNotice.seItem.id);
            ajax.start();
        };
        Feng.confirm("是否更改选中公告的发布状态?", operation);
    }
};

/**
 * 查看详情
 */
OptNotice.viewNotice = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '显示通知公告',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/optNotice/notice_view/' + this.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 重制
 */
OptNotice.resetSearch = function () {
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#status").val("");
    OptNotice.search();
};

/**
 * 搜索
 */
OptNotice.search = function () {
    var queryData = {};
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['status'] = $("#status").val();
    queryData['deptCode'] = OptNotice.deptCode;
    queryData['deptId'] = OptNotice.deptId;

    OptNotice.table.refresh({query: queryData});
};

OptNotice.onClickDept = function (e, treeId, treeNode) {
    OptNotice.deptCode = treeNode.code;
    OptNotice.deptId = treeNode.id;
    OptNotice.search();
};

OptNotice.formParams = function() {
    var queryData = {};
    queryData['deptCode'] = OptNotice.deptCode;
    queryData['deptId'] = OptNotice.deptId;
    return queryData;
}

$(function () {
    var defaultColunms = OptNotice.initColumn();
    var table = new BSTable("optNoticeTable", "/optNotice/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(OptNotice.formParams());
    OptNotice.table = table.init();

    var ztree = new $ZTree("optNoticeOrgTree", "/optNotice/selectOrgTreeList");
    ztree.bindOnClick(OptNotice.onClickDept);
    ztree.init();
});

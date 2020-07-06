var WechatPushInfo = {
    id: "WechatPushInfoTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
WechatPushInfo.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'name', align: 'center', valign: 'middle'},
        {title: '描述', field: 'instruction', align: 'center', valign: 'middle'},
        {title: '类型', field: 'category', align: 'center', valign: 'middle', formatter: function (value) {
            if (value == '0') {
                return "门票";
            }else if (value == '1') {
                return "自行车";
            }else if (value == '2') {
                return "观光车";
            }else if (value == '3') {
                return "游船";
            }else{
                return "其他";
            }
        }},
        {title: '购买/退票', field: 'operate', align: 'center', valign: 'middle', formatter: function (value) {
            if (value == '0') {
                return "购买";
            }else{
                return "退票";
            }
        }},
        {
            title: '操作', field:'caozuo',align:'center',valign:'middle',
            formatter:function(value, row, index){
                var  a='<button type=button onclick=WechatPushInfo.updateView("'+row.id+'")  class="btn btn-primary ">编辑</button>'+" "+
                    '<button type=button onclick=WechatPushInfo.del("'+row.id+'")  class="btn btn-primary ">删除</button>';
                return a
            }
        }];
    return columns;
};

WechatPushInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        WechatPushInfo.seItem = selected[0];
        return true;
    }
};


/**
 * 点击新增活动信息
 */
WechatPushInfo.openAddaddActivity = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['500px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/wechatPushInfo/addWechatPushInfo'
    });
    this.layerIndex = index;
    layer.full(index);
};

WechatPushInfo.search = function () {
    var queryData = {};
    queryData['category'] = $("#category").val();
    WechatPushInfo.table.refresh({query: queryData});
};

WechatPushInfo.resetSearch = function () {
    $("#category").val("");
    WechatPushInfo.search();
};


/**
 * 点击编辑
 * @param userId 管理员id
 */
WechatPushInfo.updateView = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑景区信息',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/wechatPushInfo/editwechatPushInfo/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 删除
 */
WechatPushInfo.del = function (id) {
    var  operation = function () {
        var ajax = new $ax(Feng.ctxPath+"/wechatPushInfo/delete",function () {
            Feng.success("删除成功!");
            WechatPushInfo.search();
        },function (data) {
            Feng.error("删除失败!")
        });
        ajax.set("id",id)
        ajax.start();
    };
    Feng.confirm("是否确认删除?",operation);
};


$(function () {
    var defaultColunms = WechatPushInfo.initColumn();
    var table = new BSTable("WechatPushInfoTable", "/wechatPushInfo/list", defaultColunms);
    table.setPaginationType("server");
    WechatPushInfo.table = table.init();
})

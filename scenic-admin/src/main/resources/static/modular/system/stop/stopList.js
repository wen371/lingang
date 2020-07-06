var StopList = {
    id: "Stops",//表格id
    seItem: null,//选中的条目
    table: null,
    layerIndex: -1,
    deptId: 0,
    innerCode:'',
    orgCode:''
};

/**
 * 初始化表格的列
 */
StopList.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '机构名称', field: 'name',align: 'center', valign: 'middle', sortable: false},
        {title: '开始时间', field: 'startTime',align: 'center', valign: 'middle', sortable: false},
        {title: '结束时间', field: 'endTime',align: 'center', valign: 'middle', sortable: false},
        {title: '更新时间', field: 'modifyTime',align: 'center', valign: 'middle', sortable: false},
        {
            title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: false,
            formatter: function (value) {
                return Dict[this.field][value]
            }
        }
    ];
    return columns;
};
/**
 * 引入字典
 */
var Dict = getDict("sysStop");

/**
 * 检查是否选中
 */
StopList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        console.log("检查seItem  "+selected[0]);
        StopList.seItem = selected[0];
        return true;
    }
};


/**
 * 新增
 */
StopList.InStop = function () {
    var orgCode =  StopList.orgCode;
    if(''==orgCode){
        Feng.info("请先选中一个机构！");
        return false;
    }
        var index = layer.open({
            type: 2,
            title: '上架',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sysStop/InStop/'+orgCode
        });
        this.layerIndex = index;
};

/**
 * 编辑
 */
StopList.UpStop = function () {
    if(this.check()){
    var index = layer.open({
        type: 2,
        title: '上架',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sysStop/upStop/'+StopList.seItem.id
    });
    this.layerIndex = index;
    }
};


/**
 * 失效
 */
StopList.guanBi = function (){
    if(this.check()) {
        var stop = function () {
            console.log("seItem  "+StopList.seItem);
            var id = StopList.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/sysStop/guanBi/" + id, function () {
                Feng.success("操作成功!");
                StopList.table.refresh();
            }, function (data) {
                Feng.error("操作失败!");
            });
            ajax.start();
        };
        Feng.confirm("是否关闭", stop);
    }
};


StopList.search = function () {
    var queryData = {};
    queryData['innerCode'] = StopList.innerCode;
    StopList.table.refresh({query: queryData});
};

StopList.onClickDept = function (e, treeId, treeNode) {
    StopList.innerCode = treeNode.innerCode;
    StopList.orgCode =treeNode.code;
    console.log("StopList.innerCode  "+StopList.innerCode);
    StopList.search();
};

$(function () {
    var defaultColunms = StopList.initColumn();
    var table = new BSTable("Stops", "/sysStop/list", defaultColunms);
    table.setPaginationType("server");
    StopList.table = table.init();
    var ztree = new $ZTreeOrg("orgTreeList", "/orgtree/treelist/4");
    ztree.bindOnClick(StopList.onClickDept);
    ztree.init();
});

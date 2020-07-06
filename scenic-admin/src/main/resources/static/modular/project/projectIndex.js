var Project = {
    id: "ProjectTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId:-1
};

/**
 * 初始化表格的列
 */
Project.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        // {title: '编号', align: 'center', valign: 'middle',width:'10%',formatter: function(value,row,index) {return index+1}},
        {title: '小项目名称', field: 'name', align: 'center', valign: 'middle'},
        /*{title: '所属景区', field: 'scenicName', align: 'center', valign: 'middle'},*/
        {title: '是否押金', field: 'isDeposit', align: 'center', valign: 'middle'},
        {title: '计费方式', field: 'type', align: 'center', valign: 'middle',
            formatter: function(value,row,index) {
                if(value=='0')return "普通计费";
                if(value=='1')return "按时计费";
            }
        },
        {title: '押金金额', field: 'deposit', align: 'center', valign: 'middle'},
        /*{title: '免费分钟数', field: 'freeMinutes', align: 'center', valign: 'middle'},*/
        // {title: '说明', field: 'instruction', align: 'center', valign: 'middle'},
        {
            title: '操作', field:'caozuo',align:'center',valign:'middle',
            formatter:function(value, row, index){
                var  a='<button type=button onclick=Project.detail("'+row.id+'")  class="btn btn-primary ">查看详情</button>'+" "+
                    '<button type=button onclick=Project.updateView("'+row.id+'")  class="btn btn-primary ">编辑</button>'+" "+
                     '<button type=button onclick=Project.del("'+row.id+'")  class="btn btn-primary ">删除</button>';
                return a
            }
        }];
    return columns;
};

Project.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Project.seItem = selected[0];
        return true;
    }
};

/**
 * 点击详情
 * @param userId
 */
Project.detail = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑小项目信息',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/project/detail/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 点击编辑
 * @param userId 管理员id
 */
Project.updateView = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑小项目信息',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/project/editProject/' + id
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 删除
 */
Project.del = function (id) {
    var  operation = function () {
        var ajax = new $ax(Feng.ctxPath+"/project/delete",function () {
            Feng.success("删除成功!");
            Project.search();
        },function (data) {
            Feng.error("删除失败!");
        });
        ajax.set("id",id)
        ajax.start();
    };
    Feng.confirm("是否确认删除?",operation);
};


/**
 * 点击新增活动信息
 */
Project.addProject = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目',
        area: ['500px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/project/addProject'
    });
    this.layerIndex = index;
    layer.full(index);
};

Project.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['scenicName'] = $("#scenicName").val();
    queryData['type'] = $("#type").val();
    queryData['isDeposit'] = $("#isDeposit").val();
    queryData['deposit'] = $("#deposit").val();
    Project.table.refresh({query: queryData});
};
Project.resetSearch = function () {
    $("#name").val("");
    $("#scenicName").val("");
    $("#type").val("");
    $("#isDeposit").val("");
    $("#deposit").val("");
    Project.search();
};

$(function () {
    var defaultColunms = Project.initColumn();
    var table = new BSTable("ProjectTable", "/project/list", defaultColunms);
    table.setPaginationType("server");
    Project.table = table.init();
})
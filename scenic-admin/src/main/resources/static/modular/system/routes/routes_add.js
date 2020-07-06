var RouteInfoDlg = {
    routesInfoData: {},
    layerIndex: -1,
    validateFields: {
        festivalName: {
            validators: {
                notEmpty: {
                    message: '活动名称不能为空！'
                }
            }
        },
        festivalStyleType: {
            validators: {
                notEmpty: {
                    message: '样式模板不能为空！'
                }
            }
        },
        festivalContent: {
            validators: {
                notEmpty: {
                    message: '活动内容不能为空！'
                }
            }
        }
    }
};


/**
 * 清除数据
 */
RouteInfoDlg.clearData = function () {
    this.routesInfoData = {};
};

/**
 * 收集数据
 */
RouteInfoDlg.collectData = function(){
    /** 线路名称*/
    this.routesInfoData['name'] = $("#routesName").val();
    /** 线路简介*/
    this.routesInfoData['instruction'] =$("#routesIntroduction").val();
    /** 线路详情(富文本)*/
    this.routesInfoData['detailNote'] = escape(editor.getContent());
    /** 活动ID(编辑页面取到的)*/
    this.routesInfoData['id'] = $("#routesId").val();
    /** 是否可编辑(编辑页面取到的)*/
    this.routesInfoData['canBeEdit'] = $("#canBeEdit").val();
    /** 封面图片的信息*/
    this.routesInfoData['fileName'] = $("#sc").find(".kv-preview-thumb").find("img").attr("title");
    this.routesInfoData['fileSize'] = $("#sc").find("samp").html();
    this.routesInfoData['fileUrl'] = $("#sc").find("input[name=pic]").val();
    /** 轮播图的信息*/
    this.routesInfoData['slideFileName'] = "";
    this.routesInfoData['slideFileSize'] = "";
    this.routesInfoData['slideFileUrl'] = "";
    var length =$("#sc2").find(".kv-preview-thumb").find("[name=pic]").length;
    var samp = $("#sc2").find(".kv-preview-thumb").find("samp");
    var name = $("#sc2").find(".kv-preview-thumb").find("img");
    var img1 = $("#sc2").find(".kv-preview-thumb").find("[name=pic]");
    for (var i=0;i<length;i++){
        this.routesInfoData['slideFileSize'] += samp.eq(i).html()+",";
        this.routesInfoData['slideFileName'] += name.eq(i).attr("title")+",";
        this.routesInfoData['slideFileUrl'] += img1.eq(i).val()+",";
    }





};


/**
 * 为什么会有collectData2这个方法?它和collectData有什么区别?
 * collectData在获取fileUrl时为空，为了解决这个问题于是就有了collectData2。collectData2主要是用在编辑功能时的收集数据。
 * 收集数据2
 */
RouteInfoDlg.collectData2 = function(){
    /** 线路名称*/
    this.routesInfoData['name'] = $("#routesName").val();
    /** 线路简介*/
    this.routesInfoData['instruction'] =$("#routesIntroduction").val();
    /** 线路详情(富文本)*/
    this.routesInfoData['detailNote'] = escape(editor.getContent());
    /** 活动ID(编辑页面取到的)*/
    this.routesInfoData['id'] = $("#routesId").val();
    /** 是否可编辑(编辑页面取到的)*/
    this.routesInfoData['canBeEdit'] = $("#canBeEdit").val();
    /** 封面图片的信息*/
    this.routesInfoData['fileName'] = $("#sc").find(".kv-preview-thumb").find("img").attr("title");
    this.routesInfoData['fileSize'] = $("#sc").find("samp").html();
    this.routesInfoData['fileUrl'] =  $("#sc").find(".file-sortable.kv-preview-thumb").find("img").eq(0).attr("src");
        /*$("#sc").find("input[name=pic]").val();//这种方式没有取到，很奇怪，用上面这种方式就成功了！*/
    if($("#sc").find(".file-sortable.kv-preview-thumb").find("img").eq(0).attr("src")==undefined){
        console.log("启用第二种方式获得封面图图片URL");
        this.routesInfoData['fileUrl'] = $("#sc").find("input[name=pic]").val();
    }
    /** 轮播图的信息*/
    this.routesInfoData['slideFileName'] = "";
    this.routesInfoData['slideFileSize'] = "";
    this.routesInfoData['slideFileUrl'] = "";
    var length =$("#sc2").find(".kv-preview-thumb").find("[name=pic]").length;
    var samp = $("#sc2").find(".kv-preview-thumb").find("samp");
    var name = $("#sc2").find(".kv-preview-thumb").find("img");
    var img1 = $("#sc2").find(".file-sortable.kv-preview-thumb").find("img");
    for (var i=0;i<length;i++){
        this.routesInfoData['slideFileSize'] += samp.eq(i).html()+",";
        this.routesInfoData['slideFileName'] += name.eq(i).attr("title")+",";
        /*console.log("方式1：" + img1.eq(i).attr("src"));
        console.log("方式2：" + $("#sc2").find(".kv-preview-thumb").find("[name=pic]").eq(i).val());*/
        if(img1.eq(i).attr("src")==undefined){
            console.log("启用方式2获取轮播图图片url");
            this.routesInfoData['slideFileUrl'] += $("#sc2").find(".kv-preview-thumb").find("[name=pic]").eq(i).val()+",";
        }else{
            this.routesInfoData['slideFileUrl'] += img1.eq(i).attr("src")+",";
        }
    }


};

/**
 * 关闭弹窗
 */
RouteInfoDlg.closeWindow =function () {
    parent.layer.close(window.parent.Routes.layerIndex);
};

/**
 * 线路简介最多20个字
 */
RouteInfoDlg.checkWordsLimit = function () {
    //检查字数
    var text = $("#routesIntroduction").val();
    //中文字数统计
    var str = (text.replace(/\w/g,"")).length;
    //非汉字的个数
    var abcnum = text.length-str;
    var total = str+abcnum;
    if(total > 20){
        Feng.error("大V介绍字数过多!");
        return;
    }
}

/**
 * 新增节庆活动
 */
RouteInfoDlg.addSubmit =function () {

    this.clearData();
    this.collectData();

    //收集数据之后
    if(this.routesInfoData['name']==''){
        Feng.error("线路名称不能为空！");
        return;
    }
    if(this.routesInfoData['detailNote']==''){
        Feng.error("线路详情不能为空！");
        return;
    }
    if(RouteInfoDlg.routesInfoData['fileUrl']==undefined||RouteInfoDlg.routesInfoData['fileUrl']==""){
        console.log("封面图",RouteInfoDlg.routesInfoData)
        Feng.error("请上传封面图!");
        return;
    }
    if(RouteInfoDlg.routesInfoData['slideFileUrl']==undefined||RouteInfoDlg.routesInfoData['slideFileUrl']==""){
        Feng.error("请上传轮播图！");
        return;
    }
    var length =$("#sc").find(".kv-preview-thumb").find("[name=pic]").length;
    if(parseInt(length)>1){
        Feng.error("封面图只能上传一张")
        return;
    }
    console.info(RouteInfoDlg.routesInfoData['slideFileUrl'].split(",").length-1);
    if(RouteInfoDlg.routesInfoData['slideFileUrl'].split(",").length-1 >8){
        Feng.error("上传图不能超过八张");
        return;
    }
    console.log("add parameter1111:" , this.routesInfoData);
    //return;

    var jsonString = JSON.stringify(this.routesInfoData);
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        dataType: "JSON",
        url :   Feng.ctxPath + "/routes/addRoutes",
        data:  jsonString,
        contentType: "application/json; charset=utf-8",
        error : function(data) {
            Feng.error("添加失败!" );
            console.log("ADD ROUTES FAILED:", data);
        },
        success : function(data) {
           if(data.msg == '0'){
               Feng.success("添加成功!");
               window.parent.Routes.table.refresh();
               parent.layer.close(window.parent.Routes.layerIndex);
           }
        }
    });

};

/**
 * 编辑线路
 */
RouteInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData2();

    //收集数据之后
    if(this.routesInfoData['name']==''){
        Feng.error("活动名称不能为空！");
        return;
    }
    if(this.routesInfoData['detailNote']==''){
        Feng.error("活动详情不能为空！");
        return;
    }
    if(RouteInfoDlg.routesInfoData['fileUrl']==undefined||RouteInfoDlg.routesInfoData['fileUrl']==""){
        console.log("封面图",RouteInfoDlg.routesInfoData)
        Feng.error("请上传封面图!");
        return;
    }
    if(RouteInfoDlg.routesInfoData['slideFileUrl']==undefined||RouteInfoDlg.routesInfoData['slideFileUrl']==""){
        Feng.error("请上传轮播图！");
        return;
    }

    /*if(RouteInfoDlg.routesInfoData['slideFileUrl'].split(",").length-1 >2){
        Feng.error("上传封面图不能超过一张");
        return;
    }*/
    console.info(RouteInfoDlg.routesInfoData['slideFileUrl'].split(",").length-1);
    if(RouteInfoDlg.routesInfoData['slideFileUrl'].split(",").length-1 >8){
        Feng.error("上传图不能超过八张");
        return;
    }


    if(RouteInfoDlg.routesInfoData['canBeEdit']=='false'){
        Feng.error("状态为‘已发布’不能编辑!");
        return;
    }
    console.log("edit parameter1111:" , this.routesInfoData);

    var jsonString = JSON.stringify(this.routesInfoData);
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        dataType: "JSON",
        url :   Feng.ctxPath + "/routes/editRoutes",
        data:  jsonString,
        contentType: "application/json; charset=utf-8",
        error : function(data) {
            Feng.error("编辑失败!" );
            console.log("EDIT ROUTES FAILED:", data);
        },
        success : function(data) {
            if(data.msg == '0'){
                Feng.success("编辑成功!");
                window.parent.Routes.table.refresh();
                parent.layer.close(window.parent.Routes.layerIndex);
            }
        }
    });
};

$(function () {
    Feng.initValidator("routesInfoForm", RouteInfoDlg.validateFields);

    $("#input-folder-1").fileinput({
        uploadUrl: Feng.ctxPath+"/routes/upload",
        maxFileCount: 1,
        overwriteInitial: false,
        allowedFileExtensions: ["jpeg", "jpg","png"]
    });
    $("#input-folder-2").fileinput({
        uploadUrl: Feng.ctxPath+"/routes/upload",
        maxFileCount: 8,
        overwriteInitial: false,
        allowedFileExtensions: ["jpeg", "jpg","png"]
    });

});
var keys = Object.keys || function(obj) {
    obj = Object(obj)
    var arr = []
    for (var a in obj) arr.push(a)
    return arr
}
var invert = function(obj) {
    obj = Object(obj)
    var result = {}
    for (var a in obj) result[obj[a]] = a
    return result
}
var entityMap = {
    escape: {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": "&apos;",
        " ":"&nbsp;",
        "(":"&#40;",
        ")":"&#41;"
    }
}
entityMap.unescape = invert(entityMap.escape)
var entityReg = {
    escape: RegExp('[' + keys(entityMap.escape).join('') + ']', 'g'),
    unescape: RegExp('(' + keys(entityMap.unescape).join('|') + ')', 'g')
}

// 将HTML转义为实体
function escape(html) {
    if (typeof html !== 'string') return ''
    return html.replace(entityReg.escape, function(match) {
        return entityMap.escape[match]
    })
}
// 将实体转回为HTML
function unescape(str) {
    return str.replace(entityReg.unescape, function(match) {
        console.log("我会"+entityMap.unescape[match]);
        return entityMap.unescape[match];

    })
}
editor.ready(function() {//编辑器初始化完成再赋值
    editor.setContent(unescape($("#text").val()));  //赋值给UEditor
});
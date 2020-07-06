/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var productAdd = {
    productAddData: {}

};

/**
 * 清除数据
 */
productAdd.clearData = function () {
    this.productAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
productAdd.set = function (key, val) {
    this.productAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
productAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
productAdd.close = function () {
    parent.layer.close(window.parent.Product.layerIndex);
};

/**
 * 验证数据是否为空
 */
productAdd.validate = function () {
    $('#productAddForm').data("bootstrapValidator").resetForm();
    $('#productAddForm').bootstrapValidator('validate');
    return $("#productAddForm").data('bootstrapValidator').isValid();
};
/**
 * 收集数据
 */
productAdd.collectData = function () {
    /*this.set('id').set('name').set('activityTimeStr').set('activityAddress');
    this.productAddData['detailNote']=escape(editor.getContent());//富文本内容
    this.productAddData['entryInfo']=escape(editor1.getContent());//富文本内容
    this.productAddData['imgSize'] = "";
    this.productAddData['imgUrl'] = "";
    this.productAddData['imgName'] = "";
    var length = $("#sc").find(".file-preview-initial.file-sortable.kv-preview-thumb").length;
        var length1 = $("#sc").find(".file-preview-success.kv-preview-thumb").length;
    console.log("length  " + length);
    console.log("length1  " + length1);
    var img = $("#sc").find(".file-sortable.kv-preview-thumb").find("img");
    var img1 = $("#sc").find(".file-preview-success.kv-preview-thumb").find("input[name=pic]");
    for (var i = 0; i < length; i++) {
        this.productAddData['imgUrl'] += img.eq(i).attr("src") + ",";
    }
    for (var i = 0; i < length1; i++) {
        this.productAddData['imgUrl'] += img1.eq(i).val() + ",";
    }*/
    this.productAddData['projectProductName'] = $("#projectProductName").val();
    this.productAddData['presentPrice'] = $("#presentPrice").val();
    this.productAddData['originalPrice'] = $("#originalPrice").val();
    this.productAddData['maxnum'] = $("#maxnum").val();
    //this.productAddData['note'] = $("#note").val();
    this.productAddData['rule'] = $("#rule").val();
    /*this.productAddData['startTime'] = $("#startTime").val();
    this.productAddData['endTime'] = $("#endTime").val();*/
    this.productAddData['category'] = $("#category").val();
    this.productAddData['endTicket'] = $("#endTicket").val();
    this.productAddData['isChoose'] = $("#isChoose").val();
    //this.productAddData['url'] = $("#coverImg").val();//图片地址
    this.productAddData['url'] = "";
    var img = $("#sc").find(".file-sortable.kv-preview-thumb").find("img");
    var img1 = $("#sc").find(".file-preview-success.kv-preview-thumb").find("input[name=pic]");

    if(img.attr("src") != undefined){
        this.productAddData['url'] += img.attr("src");
    }
    if(img1.val() != undefined){
        this.productAddData['url'] += img1.val();
    }

    this.productAddData['url1'] = "";
    var img2 = $("#sc1").find(".file-sortable.kv-preview-thumb").find("img");
    var img3 = $("#sc1").find(".file-preview-success.kv-preview-thumb").find("input[name=pic]");

    if(img2.attr("src") != undefined){
        this.productAddData['url1'] += img2.attr("src");
    }
    if(img3.val() != undefined){
        this.productAddData['url1'] += img3.val();
    }
    var projects = [];
    var specs = [];
    var numbers = [];
    $("select[name='project']").each(function () {
        projects.push($(this).val());
    });
    $("select[name='spec']").each(function () {
        specs.push($(this).val());
    });
    $("input[name='number']").each(function () {
        numbers.push($(this).val());
    });
    this.productAddData['projects'] = projects;
    this.productAddData['specs'] = specs;
    this.productAddData['numbers'] = numbers;
    this.productAddData['isSingleChoose'] = $("#isSingleChoose").val();
    this.productAddData['instruction'] = escape(editor.getContent());
    this.productAddData['specialReminder'] = escape(editor1.getContent());
    this.productAddData['note'] = escape(editor2.getContent());
};

productAdd.addSubmit = function () {
    this.clearData();
    this.collectData();
    //
    if($("select[name='project']").length == 0){
        Feng.error("请选择 项目与规格!");
        return;
    }
    if(this.productAddData['projectProductName'] == ''){
        Feng.error("请填写 产品名称!");
        return;
    }
    if(this.productAddData['presentPrice'] == ''){
        Feng.error("请填写 当前价格!");
        return;
    }
    if(this.productAddData['maxnum'] == ''){
        Feng.error("请填写 限购数量!");
        return;
    }
    if(this.productAddData['startTime'] == ''){
        Feng.error("请选择 起始时间!");
        return;
    }
    if(this.productAddData['endTime'] == ''){
        Feng.error("请选择 结束时间!");
        return;
    }
    if(this.productAddData['endTicket'] == ''){
        Feng.error("请选择 当天售票结束时间!");
        return;
    }
    if(this.productAddData['category'] == ''){
        Feng.error("请选择 类别!");
        return;
    }
    if(productAdd.checkEndTicketTime($("#endTicket").val())!=0){
        Feng.error("当天售票结束时间格式是HH:mm，例如 09:00 或者21:30 等等");
        return;
    }
    if (productAdd.productAddData['url'] === undefined || productAdd.productAddData['url'] == "") {
        Feng.error("请上传标题图片");
        return;
    }
    if (productAdd.productAddData['url1'] === undefined || productAdd.productAddData['url1'] == "") {
        Feng.error("请上传地标图片");
        return;
    }

    var numberMissing = false;
    $("input[name='number']").each(function () {
       if($(this).val() == ''){
           numberMissing = true;
       }
    });
    if(numberMissing){
        Feng.error("请填写 项目数量");
        return;
    }

    console.log(JSON.stringify(this.productAddData));
    $.ajax({
        async:false,
        url:Feng.ctxPath + "/product/addSubmit",
        contentType: "application/json",
        type:'post',
        data: JSON.stringify(this.productAddData),
        dataType: 'json',
        success:function(data){
            if(data.success=='0'){
                if(data.msg == '' || data.msg == null){
                    Feng.error("提交失败!");
                }else{
                    Feng.error(data.msg);
                }
            }else if(data.success == '1'){
                Feng.success("添加成功!");
                window.parent.Product.table.refresh();
                productAdd.close();
            }
        },
        error:function(){
            alert("访问异常！");
        }
    });
};

//判断时间是否是 HH:mm 格式
productAdd.checkEndTicketTime = function (val) {
    var reg = /^(([0-1]\d)|(2[0-4])):[0-5]\d$/;
    var regExp = new RegExp(reg);
    var succeed = regExp.test(val);
    if(succeed){
        return 0;
    }
    return 1;
};

//判断用户输入的数字有没有问题
productAdd.checkNumberRight = function (obj) {
    var reg=/^[1-9]\d*$/;
    if(reg.test($(obj).val())){
        productAdd.calculateOriginalPrice();
    }else{
        Feng.error("请输入正整数!");
        $(obj).val("1");
        productAdd.calculateOriginalPrice();
    }
};

//计算产品的原始价格
productAdd.calculateOriginalPrice = function () {
    var originalPrice = 0;
    var eachPrices = [];
    $("select[name='spec']").each(function(){
        var temp = $(this).find("option:selected").text();
        //console.log(temp.split('/')[0]);
        //console.log($(this).parent().parent().parent().next().find("input[name='number']").val());
        var price = parseInt(temp.split('/')[0]);
        var num = parseInt($(this).parent().parent().parent().next().find("input[name='number']").val());
        eachPrices.push(price * num);
    });
    for(var i=0;i<eachPrices.length;i++){
        originalPrice += eachPrices[i];
    }
    $("#originalPrice").val(originalPrice);
};

var newProjectCount = 0;
productAdd.newProject = function () {
   /* var aa = $("select[name='project']").val();
    console.info(typeof(aa) == "undefined");
    if (!(typeof(aa) == "undefined")){
        Feng.error("只能增加一个项目!");
        return false;
    }*/
    //<div>项目</div> <div>按钮(添加规格、删除整个项目)</div> <div>装规格</div>
    $("#projectDiv").prepend("" +

        "<div class=\"col-sm-3\">\n" +
        "\t<div class=\"form-group\">\n" +
        "\t\t<label class=\"col-sm-3 control-label\">项目</label>\n" +
        "\t\t<div class=\"col-sm-9\">\n" +
        "\t\t\t<select class=\"form-control\" name=\"project\" onchange=\"productAdd.initSpecList($($(this).parent().parent().parent().next().find('select')[0]),$(this).val())\">\n" +
        "\t\t\t</select>\n" +
        "\t\t</div>\n" +
        "\t</div>\n" +
        "    <div class=\"hr-line-dashed\"></div>\n" +
        "</div>\n" +

        "<div class=\"col-sm-3\">\n" +
        "\t<div class=\"form-group\">\n" +
        "\t\t<label class=\"col-sm-3 control-label\">规格</label>\n" +
        "\t\t<div class=\"col-sm-9\">\n" +
        "\t\t\t<select class=\"form-control\" name=\"spec\" onchange=\"productAdd.calculateOriginalPrice()\">\n" +
        "\t\t\t</select>\n" +
        "\t\t</div>\n" +
        "\t</div>\n" +
        "    <div class=\"hr-line-dashed\"></div>\n" +
        "</div>"+

        "<div class=\"col-sm-3\">\n" +
        "\t<div class=\"form-group\">\n" +
        "\t\t<label class=\"col-sm-3 control-label\">数量</label>\n" +
        "\t\t<div class=\"col-sm-9\">\n" +
        "\t\t\t<input class=\"form-control\" name=\"number\" value='1' onchange=\"productAdd.checkNumberRight(this)\">\n" +
        "\t\t\t</input>\n" +
        "\t\t</div>\n" +
        "\t</div>\n" +
        "    <div class=\"hr-line-dashed\"></div>\n" +
        "</div>"+

        "<div class=\"form-group\">\n" +
        "\t<div class=\"col-sm-2\">\n" +
        "\t\t<button type=\"button\" class=\"btn btn-danger \" onclick=\"productAdd.deleteProject(this)\">\n" +
        "\t\t\t<i class=\"fa fa-eraser\"></i>&nbsp;删除项目\n" +
        "\t\t</button>\n" +
        "\t</div>\n" +
        "</div>" +

        "<div name='thisSpecs'></div>"
    );
    productAdd.initProductList($("select[name='project']")[newProjectCount]);
    //prepend是在开头,newProjectCount一直等于0就没问题
    //newProjectCount++;
    productAdd.calculateOriginalPrice();
};

//初始化数据 产品list
productAdd.initProductList = function (obj) {
    var ajax = new $ax(Feng.ctxPath + "/product/projectList?selectedProjects="+$("#selectedProjects").val(), function (data) {
        console.log('获取的projectList:',data.length);
        for(var i=0;i<data.length;i++){
            $(obj).append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
        }
        if(data.length>0){
            productAdd.initSpecList($($(obj).parent().parent().parent().next().find('select')[0]),$(obj).val());
        }
    }, function (data) {
        console.log('获取项目列表失败!',data);
    });

    ajax.start();
};

//初始化数据 规格list,填充到obj里面
productAdd.initSpecList = function(obj,projectId) {
    //console.log($(obj));
    $(obj).empty();
    var ajax = new $ax(Feng.ctxPath + "/product/specList/"+projectId, function (data) {
        console.log('获取的specList:',data.length);
        for(var i=0;i<data.length;i++){
            $(obj).append("<option value='"+data[i].id+"'>"+data[i].price+"/"+data[i].num+ data[i].unit+"</option>");
        }
    }, function (data) {
        console.log('获取规格列表失败!',data);
    });

    ajax.start();

    productAdd.calculateOriginalPrice();
};

//删除 项目
productAdd.deleteProject = function(obj) {
    $(obj).parent().parent().prev().prev().prev().remove();
    $(obj).parent().parent().prev().prev().remove();
    $(obj).parent().parent().prev().remove();
    $(obj).parent().parent().remove();

    productAdd.calculateOriginalPrice();
};

/**
 * 提交编辑活动信息
 */
productAdd.editSubmit = function () {

    this.clearData();
    this.collectData();
    console.info(this.productAddData);
    //
    this.productAddData['id']=$('#productId').val();
    if(this.productAddData['id']==''){
        Feng.error("ID为空无法进行更新操作!");
        return;
    }
    if($("select[name='project']").length == 0){
        Feng.error("请选择 项目与规格!");
        return;
    }
    if(this.productAddData['projectProductName'] == ''){
        Feng.error("请填写 产品名称!");
        return;
    }
    if(this.productAddData['presentPrice'] == ''){
        Feng.error("请填写 当前价格!");
        return;
    }
    if(this.productAddData['maxnum'] == ''){
        Feng.error("请填写 限购数量!");
        return;
    }
    if(this.productAddData['startTime'] == ''){
        Feng.error("请选择 起始时间!");
        return;
    }
    if(this.productAddData['endTime'] == ''){
        Feng.error("请选择 结束时间!");
        return;
    }
    if(this.productAddData['category'] == ''){
        Feng.error("请选择 类别!");
        return;
    }
    if(this.productAddData['endTicket'] == ''){
        Feng.error("请选择 当天售票结束时间!");
        return;
    }
    if(productAdd.checkEndTicketTime($("#endTicket").val())!=0){
        Feng.error("当天售票结束时间格式是HH:mm，例如 09:00 或者21:30 等等");
        return;
    }
    if (productAdd.productAddData['url'] === undefined || productAdd.productAddData['url'] == "") {
        Feng.error("请上传图片");
        return;
    }

    var numberMissing = false;
    $("input[name='number']").each(function () {
        if($(this).val() == ''){
            numberMissing = true;
        }
    });
    if(numberMissing){
        Feng.error("请填写 项目数量");
        return;
    }

    console.log(JSON.stringify(this.productAddData));
    $.ajax({
        async:false,
        url:Feng.ctxPath + "/product/editSubmit",
        contentType: "application/json",
        type:'post',
        data: JSON.stringify(this.productAddData),
        dataType: 'json',
        success:function(data){
            if(data.success=='0'){
                if(data.msg == '' || data.msg == null){
                    Feng.error("提交失败!");
                }else{
                    Feng.error(data.msg);
                }
            }else if(data.success == '1'){
                Feng.success("添加成功!");
                window.parent.Product.table.refresh();
                productAdd.close();
            }
        },
        error:function(){
            alert("访问异常！");
        }
    });
};

editor.ready(function() {//编辑器初始化完成再赋值
    editor.setContent(unescape($("#instruction").val()));  //赋值给UEditor
});
editor1.ready(function() {//编辑器初始化完成再赋值
    editor1.setContent(unescape($("#specialReminder").val()));  //赋值给UEditor
});
editor2.ready(function() {//编辑器初始化完成再赋值
    editor2.setContent(unescape($("#note").val()));  //赋值给UEditor
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


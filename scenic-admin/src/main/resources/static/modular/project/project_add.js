/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var projectVsSpecAdd = {
    projectVsSpecAddData: {},

};

/**
 * 清除数据
 */
projectVsSpecAdd.clearData = function () {
    this.projectVsSpecAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
projectVsSpecAdd.set = function (key, val) {
    this.projectVsSpecAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
projectVsSpecAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
projectVsSpecAdd.close = function () {
    parent.layer.close(window.parent.Project.layerIndex);
};

/**
 * 验证数据是否为空
 */
projectVsSpecAdd.validate = function () {
    $('#projectVsSpecAddForm').data("bootstrapValidator").resetForm();
    $('#projectVsSpecAddForm').bootstrapValidator('validate');
    return $("#projectVsSpecAddForm").data('bootstrapValidator').isValid();
};
/**
 * 收集数据
 */
/*projectVsSpecAdd.collectData = function () {
    this.projectVsSpecAddData['instruction']=escape(editor.getContent());//富文本内容
};*/


/**
 * 新增规格
 */
function addspec() {
    //新增规格
    var trIndex = Number($("#specTab_table").find("tr").length)-1;
    var price="price";
    var num = "num";
    var unit = "unit";
    var id = "id";
    // if($("#productspec").val()=="1"){
    //     price = "specName";
    //     value = "specValue";
    // }
    var trHtml = "<tr id='specTab_table_" + trIndex + "'>" +
        "<td>" + (trIndex+1) + "</td>" +
        "<td><input hidden name='specVos[" + trIndex + "]."+id+ "'/><input type='text' class='form-control' name='specVos[" + trIndex + "]."+price+"'/></td>" +
        "<td><input type='text' class='form-control' name='specVos[" + trIndex + "]."+num+"'/></td>" +
        "<td><input type='text' class='form-control' name='specVos[" + trIndex + "]."+unit+"' placeholder='小时/人等'/></td>" +
        "<td><button type='button' class='btn btn-danger ' onclick='closespecTr(" + trIndex + ")'><i class='fa fa-remove'></i>删除规格</button></td>" +
        "</tr>";
    $("#specTab_table").append(trHtml);
}

function closespecTr(trIndex) {
    //删除规格
    var closespec = function () {
        $("#specTab_table_" + trIndex).remove();
        resetTrIndex();
    };
    Feng.confirm("是否删除此属性", closespec);

}

function changeIsDeposit(op) {
    //选择是否有押金
    if($(op).val()=='是'){//是表示有，则此时押金金额 ，计费方式，免费分钟数展示
        $("#deposit_show").show();
        $("#type_show").show();
        //$("#freeMinutes_show").show();
    }else{//此时押金金额 ，计费方式，免费分钟数隐藏
        $("#deposit_show").hide();
        $("#type_show").hide();
        //$("#freeMinutes_show").hide();
    }

}

//保存新增
function save() {
    /*console.log(escape(editor.getContent()));
    $("#instruction").val(escape(editor.getContent()));*/
    $("#note").val($("#note1").val());
    if(!validate())return false;
    jQuery.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        url : Feng.ctxPath + "/project/addProjectVsSpec",
        data : $("#projectVsSpecForm").serialize(),
        success : function(resp) {
            if(resp.code=="200"){
                Feng.success("添加成功!");
                window.parent.Project.search();
                projectVsSpecAdd.close();
            }else {
                Feng.error("添加失败,请联系管理员!");
            }

        },
        error : function() {
            Feng.error("添加失败!");
        }
    });
}

//保存修改
function update() {
    /*console.log(escape(editor.getContent()));
    $("#instruction").val(escape(editor.getContent()));*/
    $("#note").val($("#note1").val());
    if(!validate())return false;
    jQuery.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        url : Feng.ctxPath + "/project/update",
        data : $("#projectVsSpecForm").serialize(),
        success : function(resp) {
            if(resp.code=="200"){
                Feng.success("修改成功!");
                window.parent.Project.search();
                projectVsSpecAdd.close();
            }else {
                Feng.error("修改失败,请联系管理员!");
            }

        },
        error : function() {
            Feng.error("修改失败!");
        }
    });
}

function delete1(id){
    var deleteSpec = function () {
        jQuery.ajax({
            type : "POST",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            url : Feng.ctxPath + "/project/deleteSpec?id="+id,
            success : function(resp) {
                if(resp.code=="200"){
                    Feng.success("删除成功!");
                    window.location.reload();
                }else {
                    Feng.error("删除失败,请联系管理员!");
                }

            },
            error : function() {
                Feng.error("删除成功!");
            }
        });
    };
    Feng.confirm("是否删除此属性", deleteSpec);
}



/*editor.ready(function() {//编辑器初始化完成再赋值
    editor.setContent(unescape($("#instruction").val()));  //赋值给UEditor
});*/


var keys = Object.keys || function(obj) {
    obj = Object(obj);
    var arr = [];
    for (var a in obj) arr.push(a)
    return arr
};
var invert = function(obj) {
    obj = Object(obj);
    var result = {};
    for (var a in obj) result[obj[a]] = a
    return result
};
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
};
entityMap.unescape = invert(entityMap.escape);
var entityReg = {
    escape: RegExp('[' + keys(entityMap.escape).join('') + ']', 'g'),
    unescape: RegExp('(' + keys(entityMap.unescape).join('|') + ')', 'g')
}

// 将HTML转义为实体
function escape(html) {
    if (typeof html !== 'string') return '';
    return html.replace(entityReg.escape, function(match) {
        return entityMap.escape[match];
    })
}
// 将实体转回为HTML
function unescape(str) {
    return str.replace(entityReg.unescape, function(match) {
        console.log("我会"+entityMap.unescape[match]);
        return entityMap.unescape[match];
    })
}

//重制tr的序列
function resetTrIndex() {
    $("#specTab_table tr").each(function (i) {
        if(i >= 1){
            $(this).attr("id","specTab_table_"+(i-1));
        }
        $(this).children('td').each(function (j) {
            if (i >= 1 && j == 0) {
                $(this).text(i);
            }
            if (i >= 1 && j == 1) {
                var a = $(this).children().get(0);
                var b = $(this).children().get(1);
                console.log(a);
                console.log(b);
                if(a!=undefined && $(a).attr("name").indexOf("id")>-1){
                    $(a).attr("name","specVos["+(i-1)+"].id");
                }
                if(b!=undefined && $(b).attr("name").indexOf("price")>-1){
                    $(b).attr("name","specVos["+(i-1)+"].price");
                }
            }
            if (i >= 1 && j == 2) {
                $(this).children().attr("name","specVos["+(i-1)+"].num");
            }
            if (i >= 1 && j == 3) {
                $(this).children().attr("name","specVos["+(i-1)+"].unit");
            }
            if (i >= 1 && j == 4) {
                var str = $(this).children().attr("onclick");
                if(str.indexOf("delete1")>-1){
                    console.log(111);
                }else{
                    $(this).children().attr("onclick","closespecTr("+(i-1)+")");
                }
            }
        });
    });
}

function isPositiveInteger(s){
    var re = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/ ;
    return re.test(s)
}

function validate(){
    if($("#name").val()==null || $("#name").val()==''){
        Feng.info("项目名称不能为空！");
        return false;
    }
    if($("#isDeposit").val()==null || $("#isDeposit").val()==''){
        Feng.info("是否押金不能为空！");
        return false;
    }

    if($("#stock").val()==null || $("#stock").val()==''){
        Feng.info("项目库存不能为空！");
        return false;
    }
    if($("#isDeposit").val()=='是'){
        if($("#deposit").val()==null || $("#deposit").val()==''||!isPositiveInteger($("#deposit").val())){
            Feng.info("押金金额不正确！");
            return false;
        }
        /*if($("#freeMinutes").val()==null || $("#freeMinutes").val()==''){
            Feng.info("免费分钟数不能为空！");
            return false;
        }*/
    }
    var n = 0;
    $("#specTab_table").find("input").each(function () {
        if($(this).attr("name").indexOf("price")>-1){
            if($(this).val()==''){
                Feng.info("规格单价不能为空！");
                n++;
                return false;
            }
        };
        if($(this).attr("name").indexOf("num")>-1){
            if($(this).val()==''){
                Feng.info("规格数量不能为空！");
                n++;
                return false;
            }
        };
        if($(this).attr("name").indexOf("unit")>-1){
            if($(this).val()==''){
                Feng.info("规格单位不能为空！");
                n++;
                return false;
            }
        };
    });
    if(n>0){
        return false;
    }
    return true;
}
/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var scenicSpotAdd = {
    scenicSpotAddData: {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '景区名称不能为空'
                }
            }
        }/*,synopsis: {
            validators: {
                notEmpty: {
                    message: '简介不能为空'
                },
                stringLength: {
                    max:30,
                    message: '简介长度必须在30位之内'
                }
            }
        }*//*,city: {
            validators: {
                notEmpty: {
                    message: '所属城市不能为空'
                }
            }
        },area:{
            validators:{
                notEmpty:{
                    message:"所属地区不能为空"
                }
            }
        }*/,address:{
            validators:{
                notEmpty:{
                    message:"地址不能为空"
                }
            }
        }/*,ticketingAddress:{
            validators:{
                notEmpty:{
                    message:"取票地址不能为空"
                }
            }
        }*/,openHours:{
            validators:{
                notEmpty:{
                    message:"开放时间不能为空"
                }
            }
        }/*,ticketingHours:{
            validators:{
                notEmpty:{
                    message:"取票时间不能为空"
                }
            }
        },theme:{
            validators:{
                notEmpty:{
                    message:"主题不能不能为空"
                }
            }
        }*/


    }
};

/**
 * 清除数据
 */
scenicSpotAdd.clearData = function () {
    this.scenicSpotAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
scenicSpotAdd.set = function (key, val) {
    this.scenicSpotAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
scenicSpotAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
scenicSpotAdd.close = function () {
    parent.layer.close(window.parent.ScenicSpot.layerIndex);
};

/**
 * 验证数据是否为空
 */
scenicSpotAdd.validate = function () {
    $('#scenicSpotAddForm').data("bootstrapValidator").resetForm();
    $('#scenicSpotAddForm').bootstrapValidator('validate');
    return $("#scenicSpotAddForm").data('bootstrapValidator').isValid();
};
/**
 * 收集数据
 */
scenicSpotAdd.collectData = function () {
    this.scenicSpotAddData['fileSize'] = "";
    this.scenicSpotAddData['fileUrl'] = "";
    this.scenicSpotAddData['fileName'] = "";
    this.set('id').set('name').set('phone').set('city').set('area').set('address').set('synopsis')
        .set('ticketingAddress').set('rank').set('openHours').set('ticketingHours').set('theme');
    // this.scenicSpotAddData['reservationNotes']=escape(editor.getContent());//富文本内容
    this.scenicSpotAddData['details']=escape(editor1.getContent());//富文本内容
    // this.scenicSpotAddData['trafficInformation']=escape(editor2.getContent());//富文本内容

    /*this.scenicSpotAddData['headImg'] = "";
    var img = $("#sc").find(".file-sortable.kv-preview-thumb").find("img");
    var img1 = $("#sc").find(".file-preview-success.kv-preview-thumb").find("input[name=pic]");
    if(img.attr("src") != undefined){
        this.scenicSpotAddData['headImg'] += img.attr("src");
    }
    if(img1.val() != undefined){
        this.scenicSpotAddData['headImg'] += img1.val();
    }
    //巡播图
    var length = $("#sc1").find(".file-preview-initial.file-sortable.kv-preview-thumb").length;
    var length1 = $("#sc1").find(".file-preview-success.kv-preview-thumb").length;
    console.log("length  " + length);
    console.log("length1  " + length1);
    var samp = $("#sc1").find(".file-sortable.kv-preview-thumb").find("samp");
    var samp1 = $("#sc1").find(".file-preview-success.kv-preview-thumb").find("samp");
    var name = $("#sc1").find(".kv-preview-thumb").find("img");
    var name1 = $("#sc1").find(".file-preview-success.kv-preview-thumb").find(".file-caption-info");
    var image = $("#sc1").find(".file-sortable.kv-preview-thumb").find("img");
    var image1 = $("#sc1").find(".file-preview-success.kv-preview-thumb").find("input[name=pic]");
    for (var i = 0; i < length; i++) {
        this.scenicSpotAddData['fileSize'] += samp.eq(i).html() + ",";
        this.scenicSpotAddData['fileName'] += name.eq(i).attr("title") + ",";
        this.scenicSpotAddData['fileUrl'] += image.eq(i).attr("src") + ",";
    }
    for (var i = 0; i < length1; i++) {
        this.scenicSpotAddData['fileSize'] += samp1.eq(i).html() + ",";
        this.scenicSpotAddData['fileName'] += name1.eq(i).html() + ",";
        this.scenicSpotAddData['fileUrl'] += image1.eq(i).val() + ",";
    }*/

};

scenicSpotAdd.addPhotoSubmit=function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    console.log(JSON.stringify(this.scenicSpotAddData));
   /* if (scenicSpotAdd.scenicSpotAddData['headImg'] === undefined || scenicSpotAdd.scenicSpotAddData['headImg'] == "") {
        Feng.error("请上传标题图");
        return;
    }
    if (scenicSpotAdd.scenicSpotAddData['fileUrl'] === undefined || scenicSpotAdd.scenicSpotAddData['fileUrl'] == "") {
        Feng.error("请上传巡播图");
        return;
    }*/

    if(scenicSpotAdd.scenicSpotAddData['details'] === undefined || scenicSpotAdd.scenicSpotAddData['details'] == "") {
        Feng.error("详细信息不能为空");
        return;
    }

    /*if(scenicSpotAdd.scenicSpotAddData['synopsis'].length > 30){
        Feng.error("简介不能超过30个字符");
        return;
    }*/

    var ajax = new $ax(Feng.ctxPath + "/scenicSpot/add", function (data) {
        if(data.code == 200){
            Feng.success("添加成功!");
            window.parent.ScenicSpot.table.refresh();
            scenicSpotAdd.close();
        }else {
            Feng.error("添加失败!");
        }
    }, function (data) {
        Feng.error("添加失败!" );
    });
    ajax.set(this.scenicSpotAddData);
    ajax.start();
};

/**
 * 提交编辑活动信息
 */
scenicSpotAdd.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    console.log(JSON.stringify(this.scenicSpotAddData));

    /*if (scenicSpotAdd.scenicSpotAddData['headImg'] === undefined || scenicSpotAdd.scenicSpotAddData['headImg'] == "") {
        Feng.error("请上传标题图");
        return;
    }
    if (scenicSpotAdd.scenicSpotAddData['fileUrl'] === undefined || scenicSpotAdd.scenicSpotAddData['fileUrl'] == "") {
        Feng.error("请上传巡播图");
        return;
    }*/

    if(scenicSpotAdd.scenicSpotAddData['details'] === undefined || scenicSpotAdd.scenicSpotAddData['details'] == "") {
        Feng.error("详细信息不能为空");
        return;
    }

    /*if(scenicSpotAdd.scenicSpotAddData['synopsis'].length > 30){
        Feng.error("简介不能超过30个字符");
        return;
    }*/

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scenicSpot/edit", function (data) {
        if(data.code == 200){
            Feng.success("修改成功!");
            window.parent.ScenicSpot.table.refresh();
            scenicSpotAdd.close();
        }else {
            Feng.error("修改失败!");
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set(this.scenicSpotAddData);
    ajax.start();
};
// editor.ready(function() {//编辑器初始化完成再赋值
//     editor.setContent(unescape($("#text").val()));  //赋值给UEditor
// });
editor1.ready(function() {//编辑器初始化完成再赋值
    editor1.setContent(unescape($("#text1").val()));  //赋值给UEditor
});
// editor2.ready(function() {//编辑器初始化完成再赋值
//     editor2.setContent(unescape($("#text2").val()));  //赋值给UEditor
// });


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


/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var ticketProductAdd = {
    ticketProductAddData: {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '票种名称不能为空'
                }
            }
        }/*,title:{
            validators:{
                notEmpty:{
                    message:'票品标题不能为空'
                }
            }
        }*/,saleStartTimeStr:{
            validators:{
                notEmpty:{
                    message:"售卖起始日期不能为空"
                }
            }
        },saleEndTimeStr:{
            validators:{
                notEmpty:{
                    message:"售卖结束时间不能为空"
                }
            }
        }/*,isSelectDate:{
            validators:{
                notEmpty:{
                    message:"是否选择游玩日期不能为空"
                }
            }
        }*/,amount:{
            validators:{
                notEmpty:{
                    message:"单价不能为空"
                }
            }
        },displayAmount:{
            validators:{
                notEmpty:{
                    message:"显示价格不能为空"
                }
            }
        },dayStock:{
            validators:{
                notEmpty:{
                    message:"日库存不能为空"
                }
            }
        },buyMaxNum:{
            validators:{
                notEmpty:{
                    message:"最大购买数量不能为空"
                }
            }
        },validStartTimeStr:{
            validators:{
                notEmpty:{
                    message:"有效起始日期不能为空"
                }
            }
        },validEndTimeStr:{
            validators:{
                notEmpty:{
                    message:"有效结束日期不能为空"
                }
            }
        },
        // ,productCode:{
        //     validators:{
        //         notEmpty:{
        //             message:"产品编码不能为空"
        //         }
        //     }
        // }*/,productCode:{
        //     validators:{
        //         notEmpty:{
        //             message:"产品编码不能为空"
        //         }
        //     }
        // // }/*,isRetreat:{
        //     validators:{
        //         notEmpty:{
        //             message:"预约后是否可退票不能为空"
        //         }
        //     }
        // }*/,category:{
            validators:{
                notEmpty:{
                    message:"类别不能为空"
                }
            }
        }
};

/**
 * 清除数据
 */
ticketProductAdd.clearData = function () {
    this.ticketProductAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ticketProductAdd.set = function (key, val) {
    this.ticketProductAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ticketProductAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ticketProductAdd.close = function () {
    parent.layer.close(window.parent.TicketProduct.layerIndex);
};

/**
 * 验证数据是否为空
 */
ticketProductAdd.validate = function () {
    $('#ticketProductInfoFrom').data("bootstrapValidator").resetForm();
    $('#ticketProductInfoFrom').bootstrapValidator('validate');
    return $("#ticketProductInfoFrom").data('bootstrapValidator').isValid();
};

/**
 * 收集数据
 */
ticketProductAdd.collectData = function () {
    this.set('id').set('name').set('title').set('dayStock').set('scenicIds').set('prizeId').set('num').set('detailNum').set('saleStartTimeStr').set('weeks').set('weeksStr').set('saleEndTimeStr').set('isSelectDate').set('amount')
        .set('buyMinNum').set('buyMaxNum')/*.set('validStartTimeStr').set('validEndTimeStr')*/.set('suspendStartTimeStr').set('suspendEndTimeStr').set('displayAmount').set('isAloneSale').set('useDays').set('isPackage').set('productCode').set('isRetreat')
        .set('passEquity').set('passValidTime').set('passOtherExplain').set('passRetreatChange').set('category').set('saveMoney').set('limitDay').set('limitHour').set('limitMinute').set('stopTime');
    this.ticketProductAddData['strategy']=escape(editor1.getContent());//富文本内容
    this.ticketProductAddData['fileSize'] = "";
    this.ticketProductAddData['fileUrl'] = "";
    this.ticketProductAddData['fileName'] = "";
    /*var Weeks = "";
    $(":checkbox[name='weeks']:checked").each(function(){
        Weeks += $(this).val();
    });
    this.ticketProductAddData['weeks'] = Weeks;*/
    /*var WeeksStr = [];
    $("[name='weeksStr']:checked").each(function () {
        WeeksStr.push($(this).val());
    })*/
    //this.ticketProductAddData['weeksStr'] = WeeksStr;
    /*Weeks = JSON.stringify(Weeks);*/

   /* for (var i = 0;i<WeeksStr.length;i++){
        this.ticketProductAddData['weeksStr']  += WeeksStr[i]+" ";
    }*/
    this.ticketProductAddData['titleImg'] = "";
    var img = $("#sc").find(".file-sortable.kv-preview-thumb").find("img");
    var img1 = $("#sc").find(".file-preview-success.kv-preview-thumb").find("input[name=pic]");
    if(img.attr("src") != undefined){
        this.ticketProductAddData['titleImg'] += img.attr("src");
    }
    if(img1.val() != undefined){
        this.ticketProductAddData['titleImg'] += img1.val();
    }

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
        this.ticketProductAddData['fileSize'] += samp.eq(i).html() + ",";
        this.ticketProductAddData['fileName'] += name.eq(i).attr("title") + ",";
        this.ticketProductAddData['fileUrl'] += image.eq(i).attr("src") + ",";
    }
    for (var i = 0; i < length1; i++) {
        this.ticketProductAddData['fileSize'] += samp1.eq(i).html() + ",";
        this.ticketProductAddData['fileName'] += name1.eq(i).html() + ",";
        this.ticketProductAddData['fileUrl'] += image1.eq(i).val() + ",";
    }
};

ticketProductAdd.addSubmit=function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    };
    var saleStartTimeStr =new Date($("#saleStartTimeStr").val());
    var saleEndTimeStr= new Date($("#saleEndTimeStr").val());
    var suspendStartTimeStr =new Date($("#suspendStartTimeStr").val());
    var suspendEndTimeStr= new Date($("#suspendEndTimeStr").val());
    /*var validStartTimeStr = new  Date($("#validStartTimeStr").val());
    var validEndTimeStr = new Date($("#validEndTimeStr").val());*/

    //判断富文本长度
    /*var passEquity = $("#passEquity").val();
    var passValidTime = $("#passValidTime").val();
    var passOtherExplain =  $("#passOtherExplain").val();
    var passRetreatChange = $("#passRetreatChange").val();
    if (passEquity.length > 1000){
        Feng.error("PASS权益文本最多不能超过1000字段长度限制,请重新编写！")
        return;
    }
    if (passValidTime.length>1000){
        Feng.error("PASS有效期文本最多不能超过1000字段长度限制,请重新编写！")
        return;
    }
    if (passOtherExplain.length>1000){
        Feng.error("PASS其他说明文本最多不能超过1000字段长度限制,请重新编写！")
        return;
    }
    if (passRetreatChange.length>1000){
        Feng.error("PASS退改文本最多不能超过1000字段长度限制,请重新编写！")
        return;
    }*/

    if (saleStartTimeStr.getTime()>saleEndTimeStr.getTime()){
        Feng.error("售卖起始日期不能大于售卖结束日期!" );
        $("#saleStartTimeStr").val("");
        $("#saleEndTimeStr").val("");
        return false;
    };
    if ($("#saleStartTimeStr").val()==""||$("#saleEndTimeStr").val()==""){
        Feng.error("售卖起始日期或售卖结束日期不能为空!" );
        return false;
    };
    if (suspendStartTimeStr.getTime()>suspendEndTimeStr.getTime()){
        Feng.error("暂停售卖起始日期不能大于暂停售卖结束日期!" );
        $("#suspendStartTimeStr").val("");
        $("#suspendEndTimeStr").val("");
        return false;
    };
    if ($("#suspendStartTimeStr").val()==""||$("#suspendEndTimeStr").val()==""){
        Feng.error("暂停售卖起始日期或暂停售卖结束日期不能为空!" );
        return false;
    };
   /* if ($("#isRetreat").val()==""){
        Feng.error("预约后是否可退票不能为空!" );
        return false;
    };*/
    var productCode = $("#productCode").val();
    if(productCode.length != 6){
        Feng.error("产品编码必须6位!" );
        return;
    };
    var p = /[0-9]/;
    var p1 = /[a-z]/;
    var p2 = /[A-Z]/;
    var b = p.test(productCode);
    var b1 = p1.test(productCode);
    var b2 = p2.test(productCode);
    if(!(b&b1&b2)){
        Feng.error("产品编码必须包含数字及大小写字母组成的6位字符");
        return;
    };
    /*var limitDay = $("#limitDay").val();
    var just= /^(0|[1-9][0-9]*)$/;
    var limitDay1 = just.test(limitDay);
    if (!(limitDay1)){
        Feng.error("预定限制天数必须为大于等于0的正整数,非负数!");
        return;
    }*/
    /*if (validStartTimeStr.getTime()>validEndTimeStr.getTime()){
        Feng.error("有效起始日期不能大于有效结束日期!" );
        $("#validStartTimeStr").val("");
        $("#validEndTimeStr").val("");
        return false;
    };
    if ($("#validStartTimeStr").val()==""||$("#validEndTimeStr").val()==""){
        Feng.error("有效起始日期或有效结束日期不能为空!" );
        return false;
    };*/
    if (ticketProductAdd.ticketProductAddData['titleImg'] === undefined || ticketProductAdd.ticketProductAddData['titleImg'] == "") {
        Feng.error("请上传标题图");
        return;
    }
    //console.log(JSON.stringify(this.ticketProductAddData));
    var ajax = new $ax(Feng.ctxPath + "/ticketProduct/add", function (data) {
        console.log("data..............."+JSON.stringify(data));
        if (data.code == 300){
            Feng.error("产品编码已存在");
        } else if (data.code == 200) {
            Feng.success("添加成功!");
            window.parent.TicketProduct.table.refresh();
            ticketProductAdd.close();
        }
    }, function (data) {
        Feng.error("添加失败!" );
    });
    ajax.set(this.ticketProductAddData);
    ajax.start();
};

/**
 * 提交编辑票品
 */
ticketProductAdd.editSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    };
    //console.log(JSON.stringify(this.ticketProductAddData));

    /*var limitDay = $("#limitDay").val();
    var just= /^(0|[1-9][0-9]*)$/;
    var limitDay1 = just.test(limitDay);
    if (!(limitDay1)){
        Feng.error("预定限制天数必须为大于等于0的正整数,非负数!");
        return;
    }*/

    /*var WeeksStr = [];
    $("[name='weeks']:checked").each(function () {
        WeeksStr.push($(this).val());
    })
    //this.ticketProductAddData['weeksStr'] = WeeksStr;
    /!*Weeks = JSON.stringify(Weeks);*!/
    for (var i = 0;i<WeeksStr.length;i++){
        this.ticketProductAddData['weeksStr']  += WeeksStr[i]+" ";
    }*/
    var saleStartTimeStr =new Date($("#saleStartTimeStr").val());
    var saleEndTimeStr= new Date($("#saleEndTimeStr").val());
    var validStartTimeStr = new  Date($("#validStartTimeStr").val());
    var validEndTimeStr = new Date($("#validEndTimeStr").val());
    if (saleStartTimeStr.getTime()>saleEndTimeStr.getTime()){
        Feng.error("售卖起始日期不能大于售卖结束日期!" );
        $("#saleStartTimeStr").val("");
        $("#saleEndTimeStr").val("");
        return false;
    };
    if ($("#saleStartTimeStr").val()==""||$("#saleEndTimeStr").val()==""){
        Feng.error("售卖起始日期或售卖结束日期不能为空!" );
        return false;
    };
    /*if ($("#isRetreat").val()==""){
        Feng.error("预约后是否可退票不能为空!" );
        return false;
    };*/
    if (validStartTimeStr.getTime()>validEndTimeStr.getTime()){
        Feng.error("有效起始日期不能大于有效结束日期!" );
        $("#validStartTimeStr").val("");
        $("#validEndTimeStr").val("");
        return false;
    };
    if ($("#validStartTimeStr").val()==""||$("#validEndTimeStr").val()==""){
        Feng.error("有效起始日期或有效结束日期不能为空!" );
        return false;
    };

    //判断富文本长度
    /*var passEquity = $("#passEquity").val();
    var passValidTime = $("#passValidTime").val();
    var passOtherExplain =  $("#passOtherExplain").val();
    var passRetreatChange = $("#passRetreatChange").val();
    if (passEquity.length > 1000){
        Feng.error("PASS权益文本最多不能超过1000字段长度限制,请重新编写！")
        return;
    };
    if (passValidTime.length>1000){
        Feng.error("PASS有效期文本最多不能超过1000字段长度限制,请重新编写！")
        return;
    };
    if (passOtherExplain.length>1000){
        Feng.error("PASS其他说明文本最多不能超过1000字段长度限制,请重新编写！")
        return;
    };
    if (passRetreatChange.length>1000){
        Feng.error("PASS退改文本最多不能超过1000字段长度限制,请重新编写！")
        return;
    };*/

    if (ticketProductAdd.ticketProductAddData['titleImg'] === undefined || ticketProductAdd.ticketProductAddData['titleImg'] == "") {
        Feng.error("请上传标题图");
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ticketProduct/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.TicketProduct.search();
        ticketProductAdd.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set(this.ticketProductAddData);
    ajax.start();
};
editor1.ready(function() {//编辑器初始化完成再赋值
    editor1.setContent(unescape($("#text1").val()));  //赋值给UEditor
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



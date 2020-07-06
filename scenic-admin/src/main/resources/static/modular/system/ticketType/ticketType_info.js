/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var ticketTypeAdd = {
    ticketTypeAddData: {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '票种名称不能为空'
                }
            }
        }/*,instruction: {
            validators: {
                notEmpty: {
                    message: '简介不能为空'
                }
            }
        }*//*,stock:{
            validators:{
                notEmpty:{
                    message:"库存不能为空"
                }
            }
        }*/,reservationNo: {
            validators: {
                notEmpty: {
                    message: "每天可预约人数不能为空"
                }
            }
        },price: {
            validators: {
                notEmpty: {
                    message: "价格不能为空"
                }
            }
        }/*,reservationDays: {
            validators: {
                notEmpty: {
                    message: "提前预约天数不能为空"
                }
            }
        }*/,isReservation:{
            validators:{
                notEmpty:{
                    message:"是否预约不能为空"
                }
            }
        }
    }
};

/**
 * 清除数据
 */
ticketTypeAdd.clearData = function () {
    this.ticketTypeAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ticketTypeAdd.set = function (key, val) {
    this.ticketTypeAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ticketTypeAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ticketTypeAdd.close = function () {
    parent.layer.close(window.parent.TicketType.layerIndex);
};

/**
 * 验证数据是否为空
 */
ticketTypeAdd.validate = function () {
    $('#ticketTypeInfoFrom').data("bootstrapValidator").resetForm();
    $('#ticketTypeInfoFrom').bootstrapValidator('validate');
    return $("#ticketTypeInfoFrom").data('bootstrapValidator').isValid();
};

/**
 * 收集数据
 */
ticketTypeAdd.collectData = function () {
    this.set('id').set('name').set('scenicId').set('type').set('reservationNo').set('isReservation').set('stock').set('price').set('reservationDays');
    this.ticketTypeAddData['instruction']=escape(editor1.getContent());
};

ticketTypeAdd.addSubmit=function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    };
    var instruction = this.ticketTypeAddData['instruction'];
    if (instruction.length>5000){
        Feng.error("简介文本最多不能超过5000字段长度限制,请重新编写！")
        return;
    };
    
    console.log(JSON.stringify(this.ticketTypeAddData));
    var ajax = new $ax(Feng.ctxPath + "/ticketType/add", function (data) {
        Feng.success("添加成功!");
        window.parent.TicketType.table.refresh();
        ticketTypeAdd.close();
    }, function (data) {
        Feng.error("添加失败!" );
    });
    ajax.set(this.ticketTypeAddData);
    ajax.start();
};

/**
 * 提交编辑票种
 */
ticketTypeAdd.editSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    };

    var instruction = $("#instruction").val();
    if (instruction.length>5000){
        Feng.error("简介文本最多不能超过5000字段长度限制,请重新编写！")
        return;
    };

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ticketType/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.TicketType.search();
        ticketTypeAdd.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set(this.ticketTypeAddData);
    ajax.start();
};

$(function () {
    Feng.initValidator("ticketTypeInfoFrom", ticketTypeAdd.validateFields);
    /*$("#isReservation").val($("#isReservationValue").val());*/
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

editor1.ready(function() {//编辑器初始化完成再赋值
    editor1.setContent(unescape($("#text").val()));  //赋值给UEditor
});



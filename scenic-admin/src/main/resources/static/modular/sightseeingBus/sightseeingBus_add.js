/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var sightseeingBusAdd = {
    sightseeingBusAddData: {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '观光车名称不能为空'
                }
            }
        },synopsis: {
            validators: {
                notEmpty: {
                    message: '简介不能为空'
                },
                stringLength: {
                    max:255,
                    message: '简介长度必须在255位之内'
                }
            }
        },shift:{
            validators:{
                notEmpty:{
                    message:"班次时间不能为空"
                },
                regexp:{
                    regexp:/^(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                    message:'格式错误'
                }
            }
        },scenicId:{
            validators:{
                notEmpty:{
                    message:"请选择景区"
                }
            }
        }

    }
};

/**
 * 清除数据
 */
sightseeingBusAdd.clearData = function () {
    this.sightseeingBusAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
sightseeingBusAdd.set = function (key, val) {
    this.sightseeingBusAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
sightseeingBusAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
sightseeingBusAdd.close = function () {
    parent.layer.close(window.parent.SightseeingBus.layerIndex);
};

/**
 * 验证数据是否为空
 */
sightseeingBusAdd.validate = function () {
    $('#sightseeingBusAddForm').data("bootstrapValidator").resetForm();
    $('#sightseeingBusAddForm').bootstrapValidator('validate');
    return $("#sightseeingBusAddForm").data('bootstrapValidator').isValid();
};
/**
 * 收集数据
 */
sightseeingBusAdd.collectData = function () {

    this.set('id').set('name').set('shift').set('shiftName').set('synopsis').set('scenicId').set('positions');

};

sightseeingBusAdd.addPhotoSubmit=function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    console.log(JSON.stringify(this.sightseeingBusAddData));


    var ajax = new $ax(Feng.ctxPath + "/sightseeingBus/add", function (data) {
        if(data.code == 200){
            Feng.success("添加成功!");
            window.parent.SightseeingBus.table.refresh();
            sightseeingBusAdd.close();
        }else {
            Feng.error("添加失败!");
        }
    }, function (data) {
        Feng.error("添加失败!" );
    });
    ajax.set(this.sightseeingBusAddData);
    ajax.start();
};

/**
 * 提交编辑活动信息
 */
sightseeingBusAdd.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    console.log(JSON.stringify(this.sightseeingBusAddData));

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sightseeingBus/edit", function (data) {
        if(data.code == 200){
            Feng.success("修改成功!");
            window.parent.SightseeingBus.table.refresh();
            sightseeingBusAdd.close();
        }else {
            Feng.error("修改失败!");
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set(this.sightseeingBusAddData);
    ajax.start();
};





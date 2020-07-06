/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var areaAdd = {
    areaAddData: {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '停车场名称不能为空'
                },
                stringLength: {
                    max:64,
                    message: '停车场名称不能大于64位'
                }
            }
        },number: {
            validators: {
                notEmpty: {
                    message: '总车位数不能为空'
                },
                stringLength: {
                    max:10,
                    message: '总车位数不能大于10位数'
                },
                regexp:{
                    regexp:/^[0-9]*[1-9][0-9]*$/,
                    message:'总车位数必须是正整数'
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
areaAdd.clearData = function () {
    this.areaAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
areaAdd.set = function (key, val) {
    this.areaAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
areaAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
areaAdd.close = function () {
    parent.layer.close(window.parent.area.layerIndex);
};

/**
 * 验证数据是否为空
 */
areaAdd.validate = function () {
    $('#areaAddForm').data("bootstrapValidator").resetForm();
    $('#areaAddForm').bootstrapValidator('validate');
    return $("#areaAddForm").data('bootstrapValidator').isValid();
};
/**
 * 收集数据
 */
areaAdd.collectData = function () {

    this.set('id').set('name').set('number').set('scenicId');

};

areaAdd.addPhotoSubmit=function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    console.log(JSON.stringify(this.areaAddData));


    var ajax = new $ax(Feng.ctxPath + "/parkingArea/add", function (data) {
        if(data.code == 200){
            Feng.success("添加成功!");
            window.parent.area.table.refresh();
            areaAdd.close();
        }else {
            Feng.error("添加失败!");
        }
    }, function (data) {
        Feng.error("添加失败!" );
    });
    ajax.set(this.areaAddData);
    ajax.start();
};

/**
 * 提交编辑活动信息
 */
areaAdd.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    console.log(JSON.stringify(this.areaAddData));

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/parkingArea/edit", function (data) {
        if(data.code == 200){
            Feng.success("修改成功!");
            window.parent.area.table.refresh();
            areaAdd.close();
        }else {
            Feng.error("修改失败!");
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set(this.areaAddData);
    ajax.start();
};





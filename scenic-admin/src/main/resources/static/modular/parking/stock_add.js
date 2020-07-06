/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var stockAdd = {
    stockAddData: {},
    validateFields: {
        money: {
            validators: {
                notEmpty: {
                    message: '现金收入不能为空'
                },
                stringLength: {
                    max:8,
                    message: '现金收入不能大于8位数'
                },
                regexp:{
                    regexp: /^\d+(\.\d{1,2})?$/,
                    message:'第三方收入只能包含两位小数'
                }
            }
        },onlineMoney: {
            validators: {
                notEmpty: {
                    message: '第三方收入不能为空'
                },
                stringLength: {
                    max:8,
                    message: '第三方收入不能大于8位数'
                },
                regexp:{
                    regexp: /^\d+(\.\d{1,2})?$/,
                    message:'第三方收入只能包含两位小数'
                }
            }
        },inbound: {
            validators: {
                notEmpty: {
                    message: '天入库不能为空'
                },
                stringLength: {
                    max:10,
                    message: '天入库不能大于10位数'
                },
                regexp:{
                    regexp:/^[0-9]*[1-9][0-9]*$/,
                    message:'天入库必须是正整数'
                }
            }
        },outbound: {
            validators: {
                notEmpty: {
                    message: '天出库不能为空'
                },
                stringLength: {
                    max:10,
                    message: '天出库不能大于10位数'
                },
                regexp:{
                    regexp:/^[0-9]*[1-9][0-9]*$/,
                    message:'天出库必须是正整数'
                }
            }
        },freeParking: {
            validators: {
                notEmpty: {
                    message: '剩余库位不能为空'
                },
                stringLength: {
                    max:10,
                    message: '剩余库位不能大于10位数'
                },
                regexp:{
                    regexp:/^[+]{0,1}(\d+)$/,
                    message:'剩余库位必须是正整数'
                }
            }
        },parkingId:{
            validators:{
                notEmpty:{
                    message:"请选择停车场"
                }
            }
        },registerTime:{
            validators:{
                notEmpty:{
                    message:"请选登记时间"
                }
            }
        }

    }
};

/**
 * 清除数据
 */
stockAdd.clearData = function () {
    this.stockAddData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
stockAdd.set = function (key, val) {
    this.stockAddData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
stockAdd.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
stockAdd.close = function () {
    parent.layer.close(window.parent.stock.layerIndex);
};

/**
 * 验证数据是否为空
 */
stockAdd.validate = function () {
    $('#stockAddForm').data("bootstrapValidator").resetForm();
    $('#stockAddForm').bootstrapValidator('validate');
    return $("#stockAddForm").data('bootstrapValidator').isValid();
};
/**
 * 收集数据
 */
stockAdd.collectData = function () {

    this.set('id').set('money').set('onlineMoney').set('inbound').set('outbound').set('freeParking').set('registerTimeStr').set('parkingId');

};

stockAdd.addPhotoSubmit=function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    console.log("stockAddData : " + JSON.stringify(this.stockAddData));


    var ajax = new $ax(Feng.ctxPath + "/parkingStock/add", function (data) {
        if(data.code == 200){
            Feng.success("添加成功!");
            window.parent.stock.table.refresh();
            stockAdd.close();
        }else {
            Feng.error("添加失败!");
        }
    }, function (data) {
        Feng.error("添加失败!" );
    });
    ajax.set(this.stockAddData);
    ajax.start();
};

/**
 * 提交编辑活动信息
 */
stockAdd.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    console.log(JSON.stringify(this.stockAddData));

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/parkingStock/edit", function (data) {
        if(data.code == 200){
            Feng.success("修改成功!");
            window.parent.stock.table.refresh();
            stockAdd.close();
        }else {
            Feng.error("修改失败!");
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!" );
    });
    ajax.set(this.stockAddData);
    ajax.start();
};





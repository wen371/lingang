/**
 * 菜单详情对话框
 */
var MenuInfoDlg = {
    menuInfoData: {},
    ztreeInstance: null,
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '菜单名称不能为空'
                }
            }
        },
        code: {
            validators: {
                notEmpty: {
                    message: '菜单编号不能为空'
                }
            }
        },
        pcodeName: {
            validators: {
                notEmpty: {
                    message: '父菜单不能为空'
                }
            }
        },
        num: {
            validators: {
                notEmpty: {
                    message: '序号不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
MenuInfoDlg.clearData = function () {
    this.menuInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MenuInfoDlg.set = function (key, val) {
    this.menuInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MenuInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MenuInfoDlg.close = function () {
    parent.layer.close(window.parent.Menu.layerIndex);
}

/**
 * 收集数据
 */
MenuInfoDlg.collectData = function () {
    this.set('id').set('name').set('code').set('pcode').set('url').set('num').set('levels').set('icon').set("ismenu");
}


/**
 * 验证数据是否为空
 */
MenuInfoDlg.validate = function () {
    $('#menuInfoForm').data("bootstrapValidator").resetForm();
    $('#menuInfoForm').bootstrapValidator('validate');
    return $("#menuInfoForm").data('bootstrapValidator').isValid();
}



/**
 * 请求地址验证，顶级无需传url
 */

/**
 * 层级
 */
/*var level_url="0";
MenuInfoDlg.validateTwo = function () {
    var url =$("#url").val();
    if(level_url=="0" || level_url=="1"){

        if(url.trim().length!=0){
            $(".ibox-content").append( "<div onclick='quxiao();' id='quxiao' class='layui-layer layui-anim layui-layer-dialog layui-layer-border layui-layer-msg' id='layui-layer14' type='dialog' times='14' showtime='3000' contype='string' style='z-index:19891028; top:31%; left:40%;'> " +
                "<span class=\"layui-layer-setwin\" style='right:4px;top:8px;'><a class=\"layui-layer-ico layui-layer-close layui-layer-close1\" href=\"javascript:;\"></a></span><div class='layui-layer-content layui-layer-padding'><i class='layui-layer-ico layui-layer-ico0'></i>请求地址需为空</div><span class='layui-layer-setwin'></span></div>");
            return false;
        }

    }else{

        if(url.trim().length==0){
            $(".ibox-content").append( "<div onclick='quxiao();' id='quxiao' class='layui-layer layui-anim layui-layer-dialog layui-layer-border layui-layer-msg' id='layui-layer14' type='dialog' times='14' showtime='3000' contype='string' style='z-index:19891028; top:31%; left:40%;'> " +
                "<span class=\"layui-layer-setwin\" style='right:4px;top:8px;'><a class=\"layui-layer-ico layui-layer-close layui-layer-close1\" href=\"javascript:;\"></a></span><div class='layui-layer-content layui-layer-padding'><i class='layui-layer-ico layui-layer-ico0'></i>请求地址不得为空</div><span class='layui-layer-setwin'></span></div>");
            return false;
        }

    }
    return true;
}*/

/**
 * 顶级菜单url 应该为空 提示
 */
/*function quxiao() {
     $("#quxiao").remove();
 }*/



/**
 * 提交添加用户
 */
MenuInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/menu/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Menu.table.refresh();
        MenuInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.menuInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MenuInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

   /* if (!this.validateTwo()) {
        return;
    }*/

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/menu/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Menu.table.refresh();
        MenuInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.menuInfoData);
    ajax.start();
}

/**
 * 点击父级编号input框时
 */
MenuInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#pcodeName").attr("value", MenuInfoDlg.ztreeInstance.getSelectedVal());
    $("#pcode").attr("value", treeNode.id);
    level_url=treeNode.level;

};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
        MenuInfoDlg.hideMenuSelectTree();
    }
}
MenuInfoDlg.hideMenuSelectTree = function() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}
/**
 * 显示父级菜单选择的树
 */
MenuInfoDlg.showMenuSelectTree = function () {
    Feng.showInputTree("pcodeName", "pcodeTreeDiv", 15, 34);
    $("body").bind("mousedown", onBodyDown);
};

$(function () {
    Feng.initValidator("menuInfoForm", MenuInfoDlg.validateFields);

    var ztree = new $ZTree("pcodeTree", "/menu/selectMenuTreeList");
    ztree.bindOnClick(MenuInfoDlg.onClickDept);
    ztree.init();
    MenuInfoDlg.ztreeInstance = ztree;

    //初始化是否是菜单
    if($("#ismenuValue").val() == undefined){
        $("#ismenu").val(0);
    }else{
        $("#ismenu").val($("#ismenuValue").val());
    }
});

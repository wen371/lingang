/**
 * Created by huangfeng on 2018/1/9.
 */
/**
 * 通知公告
 */
var OptNoticeInfoDlg = {
    noticeInfoData: {},
    ztreeInstance: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                },
                stringLength: {
                    max:40,
                    message: '标题长度必须在40位之内'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
OptNoticeInfoDlg.clearData = function () {
    this.noticeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OptNoticeInfoDlg.set = function (key, val) {
    this.noticeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OptNoticeInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
OptNoticeInfoDlg.close = function () {
    parent.layer.close(window.parent.OptNotice.layerIndex);
}

/**
 * 收集数据
 */
OptNoticeInfoDlg.collectData = function () {
    this.set('id').set('title').set('description').set('detail');
    this.noticeInfoData['detail']=escape(editor.getContent());
}

/**
 * 验证数据是否为空
 */
OptNoticeInfoDlg.validate = function () {
    $('#optNoticeInfoForm').data("bootstrapValidator").resetForm();
    $('#optNoticeInfoForm').bootstrapValidator('validate');
    return $("#optNoticeInfoForm").data('bootstrapValidator').isValid();
};

OptNoticeInfoDlg.validateInfo = function () {
    var ids = Feng.zTreeCheckedNodes("zTree");
    if (ids == '' || ids == null){
        Feng.info("授权展示机构不能为空!");
        return false;
    }

    var description = $("#description").val();
    if (description == ''){
        Feng.info("通知公告简介不能为空!");
        return false;
    }
    if (description.length > 500){
        Feng.info("通知公告简介必须再500字符内！");
        return false;
    }
    var detail = $("#detail").val();
    if (detail.length > 500){
        Feng.info("通知公告详情必须再500字符内！");
        return false;
    }

    return true;
}

/**
 * 提交保存
 */
OptNoticeInfoDlg.addSave = function () {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    if (!this.validateInfo()){
        return;
    }

    var ids = Feng.zTreeCheckedNodes("zTree");
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/optNotice/add", function (data) {
        Feng.success("保存成功!");
        window.parent.OptNotice.table.refresh();
        OptNoticeInfoDlg.close();
    }, function (data) {
        Feng.error("保存失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticeInfoData);
    ajax.set("ids", ids);
    ajax.start();
}

/**
 * 直接发布
 */
OptNoticeInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    if (!this.validateInfo()){
        return;
    }
    var ids = Feng.zTreeCheckedNodes("zTree");
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/optNotice/submit", function (data) {
        Feng.success("发布成功!");
        window.parent.OptNotice.table.refresh();
        OptNoticeInfoDlg.close();
    }, function (data) {
        Feng.error("发布失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticeInfoData);
    ajax.set("ids", ids);
    ajax.start();
}

/**
 * 机构树
 */
function initZtree() {
    var setting = {
        check: {
            enable: true,
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    var noticeId = $("#id").val();
    if(null != noticeId && noticeId != ''){
        var ztree = new $ZTree("zTree", "/optNotice/selectOrgTreeListBySelect/"+noticeId);
        ztree.setSettings(setting);
        ztree.init();
    } else{
        var ztree = new $ZTree("zTree", "/optNotice/selectOrgTreeList/");
        ztree.setSettings(setting);
        ztree.init();
    }
}

$(function () {
    Feng.initValidator("optNoticeInfoForm", OptNoticeInfoDlg.validateFields);
    initZtree();

    /*$("#titleUpload").initUpload({
     "uploadUrl":Feng.ctxPath + "/education/operation/fileUpload",//上传文件信息地址
     "progressUrl":"#",//获取进度信息地址，可选，注意需要返回的data格式如下（{bytesRead: 102516060, contentLength: 102516060, items: 1, percent: 100, startTime: 1489223136317, useTime: 2767}）
     //"selfUploadBtId":"titleUrl",//自定义文件上传按钮id
     "isHiddenUploadBt":false,//是否隐藏上传按钮
     "isHiddenCleanBt":false,//是否隐藏清除按钮
     "isAutoClean":false,//是否上传完成后自动清除
     "velocity":10,//模拟进度上传数据
     //"showSummerProgress":false,//总进度条，默认限制
     //"scheduleStandard":true,//模拟进度的方式，设置为true是按总进度，用于控制上传时间，如果设置为false,按照文件数据的总量,默认为false
     //"size":350,//文件大小限制，单位kb,默认不限制
     //"maxFileNumber":3,//文件个数限制，为整数
     //"filelSavePath":"G://",//文件上传地址，后台设置的根目录
     "beforeUpload":beforeUploadFun,//在上传前执行的函数
     "onUpload":onUploadFun//在上传后执行的函数
     // autoCommit:true,//文件是否自动上传
     //"fileType":['png','jpg','docx','doc']，//文件类型限制，默认不限制，注意写的是文件后缀

     });
     function beforeUploadFun(opt){
     opt.otherData =[{"name":"name","value":"zxm"}];
     }
     function onUploadFun(opt,data){
     $("#titleUrl").val(data.fileDetail.fileUrl);
     $("#titleId").val(data.fileDetail.id);
     //uploadTools.uploadError(opt);//显示上传错误
     // uploadTools.uploadSuccess(opt);//显示上传成功
     }


     OperationInfoDlg.testUpload=function(){
     var opt = uploadTools.getOpt("titleUpload");
     uploadEvent.uploadFileEvent(opt);
     }*/
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
        return entityMap.escape[match];
    })
}
// 将实体转回为HTML
function unescape(str) {
    return str.replace(entityReg.unescape, function(match) {
        return entityMap.unescape[match];

    })
}
editor.ready(function() {//编辑器初始化完成再赋值
    editor.setContent(unescape($("#detailText").val()));  //赋值给UEditor
});




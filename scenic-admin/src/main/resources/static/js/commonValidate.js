
/*验证姓名*/
function userNameValidate(name, message) {
    var vr1 = /(^[\u2E80-\uFE4F]{2,32}$)|(^[a-zA-Z]{2,32}$)/;
    if (!vr1.test(name)) {
        Feng.info("请输入正确的" + message + "姓名");
        return false;
    }
    var vr2 = /^([\u2E80-\u9FFF]){2,7}$/;
    if (!vr2.test(name)) {
        Feng.info("请输入2至7位" + message + "姓名");
        return false;
    }
    return true
}

function companyNameValidate(name, message) {
    var vr1 = /(^[\u2E80-\uFE4F]{2,32}$)|(^[a-zA-Z]{2,32}$)/;
    if (!vr1.test(name)) {
        Feng.info("请输入正确的" + message);
        return false;
    }
    var vr2 = /^([\u2E80-\u9FFF]){2,30}$/;
    if (!vr2.test(name)) {
        Feng.info("请输入2至30位" + message);
        return false;
    }
    return true
}

//联系电话
function phoneValidate(name, message) {
    var regexp = new RegExp('^[1][3,4,5,7,8,9][0-9]{9}$');
    if (!name) {
        Feng.info("请输入正确的" + message + "手机号");
        return false;
    }
    if (!regexp.test(name)) {
        Feng.info("请输入11位有效的" + message + "手机号");
        return false;
    }
    return true;
}

//联系电话
function companyPhoneValidate(name, message) {
    var regexp = new RegExp('^[1][3,4,5,7,8,9][0-9]{9}$');
    if (!name) {
        Feng.info("请输入正确的" + message);
        return false;
    }
    if (!regexp.test(name)) {
        Feng.info("请输入11位有效的" + message);
        return false;
    }
    return true;
}


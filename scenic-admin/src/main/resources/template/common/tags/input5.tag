@/*
    表单中input框标签中各个参数的说明:

    hidden : input hidden框的id
    id : input框id
    name : input框名称
    readonly : readonly属性
    clickFun : 点击事件的方法名
    style : 附加的css属性
    suffix:附加文本框后缀
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-7">
        <input class="form-control" id="${id}" name="${id}"
               @if(isNotEmpty(value)){
               value="${tool.dateType(value)}"
               @}

               @if(isNotEmpty(readonly)){
               readonly="${readonly}"
               @}
               @if(isNotEmpty(placeholder)){
               placeholder="${placeholder}"
               @}
        >
    </div>
    @if(isNotEmpty(suffix)){
        <span class="col-sm-2" style="margin-top: 7px;">${suffix}</span>
    @}
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}



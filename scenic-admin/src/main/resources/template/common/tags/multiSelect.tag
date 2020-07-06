@/*
表单中multiSelect框标签中各个参数的说明:

id : multiSelect框id
name : multiSelect框名称
readonly : readonly属性
clickFun : 点击事件的方法名
multiple: 是否支持多选
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9 ">
        <select class="chosen-select col-sm-12 selectpicker" id="${id}" name="${id}" multiple="${multiple}" tabindex="4"
                data-live-search="true"

                @if(isNotEmpty(readonly)){
                readonly="${readonly}"
                @}
                @if(isNotEmpty(clickFun)){
                onclick="${clickFun}"
                @}
                @if(isNotEmpty(changeFun)){
                onchange="${changeFun}"
                @}
                @if(isNotEmpty(style)){
                style="${style}"
                @}
                @if(isNotEmpty(disabled)){
                disabled="${disabled}"
                @}
                @if(isNotEmpty(placeholder)){
                data-placeholder="${placeholder}"
                @}
        >${tagBody!}</select>
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
<div class="hr-line-dashed"></div>
@}

<script>
    //$('#'+'${id}').chosen({});
    $(window).on('load', function () {
        $(".selectpicker").selectpicker({
            noneSelectedText: '',
            noneResultsText: '',
            liveSearch: true,
            size: 5   //设置select高度，同时显示5个值
        });
    });
    //MultiSelect
</script>
<link rel="stylesheet" href="${basePath}/static/css/bootstrap-select.css">
<script src="${basePath}/static/js/bootstrap-select.js?v=2.0.0"></script>


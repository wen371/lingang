@/*
头像参数的说明:
name : 名称
id : 头像的id
@*/
<div class="form-group">
    <label class="col-sm-3 control-label head-scu-label">${name}</label>
    <div class="col-sm-4">
        <div id="${id}PreId">
            <div><img width="100px" height="100px"
                      @if(isEmpty(avatarImg)){
                      src="${ctxPath}/static/img/user.png"></div>
            @}else{
            src="${ctxPath}/${avatarImg}"></div>
        @}
    </div>
</div>
<input type="hidden" id="${id}" value="${avatarImg!}"/>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
<div class="hr-line-dashed"></div>
@}


<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <textarea class="form-control col-sm-12" rows="4" id="${id}"
              @if(isNotEmpty(maxlength)){
              maxlength="${maxlength}"
              @}
              @if(isNotEmpty(disabled)){
              disabled="${disabled}"
              @}
        >${value}</textarea>
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
<div class="hr-line-dashed"></div>
@}



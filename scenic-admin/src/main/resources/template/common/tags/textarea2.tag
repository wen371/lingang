<div class="form-group">
    <label class="col-sm-1 control-label">${name}</label>
    <div class="col-sm-11">
        <textarea class="form-control col-sm-9" rows="3" id="${id}"
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



<div style="margin:auto;text-align: center;" class="col-sm-6" >
    要查询的地址：</span><input id="text_1" type="text" value="${value}"
                         @if(isNotEmpty(maxlength)){
                         maxlength="${maxlength}"
                         @}
                         @if(isNotEmpty(readonly)){
                         readonly="${readonly}"
                         @}
/>
    查询结果(经纬度)：<input id="result_1" type="text"
                     @if(isNotEmpty(readonly)){
                     readonly="${readonly}"
                     @}
/>
    <input type="button" value="查询" onclick="searchByStationName1();"/>
    <div id="container1"
         style="margin-top: 10px;
                 margin-bottom: 20px;
                 width: ${width};
                 height: ${height};
                 border: 1px solid gray;
                 overflow:hidden;">
    </div>
</div>
<div style="margin:auto;text-align: center;" class="col-sm-6">
    要查询的地址：</span><input id="text_" type="text" value="${value1}"
                         @if(isNotEmpty(maxlength)){
                         maxlength="${maxlength}"
                         @}
                         @if(isNotEmpty(readonly)){
                         readonly="${readonly}"
                         @}
/>
    查询结果(经纬度)：<input id="result_" type="text"
                     @if(isNotEmpty(readonly)){
                     readonly="${readonly}"
                     @}
/>
    <input type="button" value="查询" onclick="searchByStationName1();"/>
    <div id="container"
         style="margin-top: 10px;
                 margin-bottom: 20px;
                 width: ${width};
                 height: ${height};
                 border: 1px solid gray;
                 overflow:hidden;">
    </div>
</div>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=1.3"></script>
<script type="text/javascript">
    var map = new BMap.Map("container");
    map.centerAndZoom("上海", 12);
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
    map.addControl(new BMap.OverviewMapControl({ isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT }));   //右下角，打开
    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
    function searchByStationName() {
        map.clearOverlays();//清空原来的标注
        var keyword = document.getElementById("text_").value;
        localSearch.setSearchCompleteCallback(function (searchResult) {
            console.log("searchResult")
            console.log(searchResult);
            var poi = searchResult.getPoi(0);
            document.getElementById("result_").value = poi.point.lng + "," + poi.point.lat;
            map.centerAndZoom(poi.point, 13);
            var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
            map.addOverlay(marker);
            var content = document.getElementById("text_").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
            var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
            marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
            // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        });
        localSearch.search(keyword);
    }
    var map1 = new BMap.Map("container1");
    map1.centerAndZoom("上海", 12);
    map1.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map1.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
    map1.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map1.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
    map1.addControl(new BMap.OverviewMapControl({ isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT }));   //右下角，打开
    var localSearch1 = new BMap.LocalSearch(map1);
    localSearch1.enableAutoViewport(); //允许自动调节窗体大小
    function searchByStationName1() {
        map1.clearOverlays();//清空原来的标注
        var keyword1 = document.getElementById("text_1").value;
        localSearch1.setSearchCompleteCallback(function (searchResult1) {
            console.log("searchResult")
            console.log(searchResult1);
            var poi1 = searchResult1.getPoi(0);
            document.getElementById("result_1").value = poi1.point.lng + "," + poi1.point.lat;
            map1.centerAndZoom(poi1.point, 13);
            var marker1 = new BMap.Marker(new BMap.Point(poi1.point.lng, poi1.point.lat));  // 创建标注，为要查询的地方对应的经纬度
            map1.addOverlay(marker1);
            var content1 = document.getElementById("text_1").value + "<br/><br/>经度：" + poi1.point.lng + "<br/>纬度：" + poi1.point.lat;
            var infoWindow1 = new BMap.InfoWindow("<p style='font-size:14px;'>" + content1 + "</p>");
            marker1.addEventListener("click", function () { this.openInfoWindow(infoWindow1); });
            // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        });
        localSearch1.search(keyword1);
    }
    setTimeout(function () {
        searchByStationName();
        searchByStationName1();
    },500)
    setTimeout(function () {
        $("#result_").val('${merchant.longitude}' + "," + '${merchant.latitude}');
        $("#result_1").val('${merchant1.longitude}' + "," + '${merchant1.latitude}');
    }, 1000)
</script>



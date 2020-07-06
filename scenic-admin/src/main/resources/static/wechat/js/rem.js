window.onload = function () {
	// font-size的初始化  rem的定义
	var htmlEl = document.getElementsByTagName('html')[0];
	var fitPage = function () {
		/* The calculate of with from zepto  */
		var w = htmlEl.getBoundingClientRect().width;
		w = Math.round(w);
		console.log(w);
		w = w > 750 ? 750 : w;
		var newW = w / 750 * 100;
		console.log(newW);
		htmlEl.style.fontSize = newW + 'px';
	}
	fitPage();

	var t;
	var func = function () {
		clearTimeout(t);
		t = setTimeout(fitPage, 25);
	}
	window.addEventListener('resize', func);
};

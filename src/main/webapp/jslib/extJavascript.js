var mydef = mydef || {};

/**
 * 去字符串空格
 * @param str
 * @returns
 */
mydef.trim = function(str){
	return str.replace(/(^\s*)|(\s*$)/g, '');
};
mydef.ltrim = function(str){
	return str.replace(/(^\s*)/g, '');
};
mydef.rtrim = function(str){
	return str.replace(/(\s*$)/g, '');
};

/**
 * 判断开始字符串是否是xx
 * @param source
 * @param str
 * @returns
 */
mydef.startWith = function(source, str){
	var reg = new RegExp("^" + str);
	return reg.test(source);
};

/**
 * 判断结束字符串是否是xx
 */
mydef.endWith = function(source, str){
	var reg = new RegExp(str + "$");
	return reg.test(source);
};

/**
 * iframe自动适应高度
 * @param iframe
 */
mydef.autoIframeHeight = function(iframe){
	iframe.style.height = iframe.contentWindow.document.body.scrollHeight + "px";
};

/**
 * 设置iframe高度
 * @param iframe
 * @param height
 */
mydef.setIframeHeight = function(iframe, height){
	iframe.height = height;
};
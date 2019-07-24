document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';

function skip(url) {
    window.location.href = url;
}
//提示弹窗
function hint(text, time) {
    $(".hint").show().html(text);
    setTimeout(function show() {
        $(".hint").css("display", "none")
    }, time);
}
var _ajax = {
        get: function(urls, data, success) {
            $.ajax({
                type: "GET",
                url: 'https://tuibeivip.com' + urls,
                data: data,
                success: function(res) {
                    success ? success(res) : function() {};
                }
            })
        },
        post: function(urls, data, success) {
            $.ajax({
                type: "POST",
                url: 'https://tuibeivip.com' + urls,
                data: data,
                success: function(res) {
                    success ? success(res) : function() {};
                }
            })
        },
    }
    //时间戳
function add0(m) {
    return m < 10 ? '0' + m : m
}

function format(timer) {
    var time = new Date(timer * 1000);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    // var times = y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
    var times = y + '年' + add0(m) + '月' + add0(d) + '日 ';
    return times
}

function getQueryString() {
    var qs = location.search.substr(1), // 获取url中"?"符后的字串 
        args = {}, // 保存参数数据的对象
        items = qs.length ? qs.split("&") : [], // 取得每一个参数项,
        item = null,
        len = items.length;
    for (var i = 0; i < len; i++) {
        item = items[i].split("=");
        let name = decodeURIComponent(item[0]),
            value = decodeURIComponent(item[1]);
        if (name) {
            args[name] = value;
        }
    }
    return args;
}
// 向localStorage存入数据
function setLoc(k, val) {
    if (typeof val == 'string') {
        localStorage.setItem(k, val);
        return val;
    } else {
        localStorage.setItem(k, JSON.stringify(val));
        return val;
    }
}
// 从localStorage里面获得数据
function getLoc(k) {
    let uu = localStorage.getItem(k);
    try {
        if (typeof JSON.parse(uu) != 'number') {
            uu = JSON.parse(uu);
        }
    } catch (e) {}
    return uu;
}
// 删除
function clearKey(k) {
    if (k) {
        localStorage.removeItem(k)
    } else {
        localStorage.clear();
    }
}
function getMiaoshaPath() {
    var goodsId = $("#goodsId").val();
    g_showLoading();
    $.ajax({
        url: "/second_kill/path",
        type: "GET",
        data: {
            goodsId: goodsId,
            verifyCode: $("#verifyCode").val()
        },
        success: function (data) {
            if (data.code == 0) {
                var path = data.data;
                doMiaosha(path);
            } else {
                layer.msg(data.msg);
            }
        },
        error: function () {
            layer.msg("客户端请求有误");
        }
    });
}

function getMiaoshaResult(goodsId) {
    g_showLoading();
    $.ajax({
        url: "/second_kill/result",
        type: "GET",
        data: {
            goodsId: $("#goodsId").val(),
        },
        success: function (data) {
            if (data.code == 0) {
                var result = data.data;
                if (result < 0) {
                    layer.msg("对不起，秒杀失败");
                } else if (result == 0) {//继续轮询
                    setTimeout(function () {
                        getMiaoshaResult(goodsId);
                    }, 200);
                } else {
                    layer.confirm("恭喜你，秒杀成功！查看订单？", {btn: ["确定", "取消"]},
                        function () {
                            window.location.href = "/order.html?orderId=" + result;
                        },
                        function () {
                            layer.closeAll();
                        });
                }
            } else {
                layer.msg(data.msg);
            }
        },
        error: function () {
            layer.msg("客户端请求有误");
        }
    });
}

function doMiaosha(path) {
    $.ajax({
        url: "/second_kill/" + path + "/do_second_kill",
        type: "POST",
        data: {
            goodsId: $("#goodsId").val()
        },
        success: function (data) {
            if (data.code == 0) {
                getMiaoshaResult($("#goodsId").val());
            } else {
                layer.msg(data.msg);
            }
        },
        error: function () {
            layer.msg("客户端请求有误");
        }
    });

}

function render(detail) {
    var miaoshaStatus = detail.miaoshaStatus;
    var remainSeconds = detail.remainSeconds;
    var goods = detail.goodsVo;
    var user = detail.secondKillUser;
    if (user) {
        $("#userTip").hide();
    }
    $("#goodsName").text(goods.goodsName);
    $("#goodsImg").attr("src", goods.goodsImg);
    $("#startTime").text(new Date(goods.startDate).format("yyyy-MM-dd hh:mm:ss"));
    $("#remainSeconds").val(remainSeconds);
    $("#goodsId").val(goods.id);
    $("#goodsPrice").text(goods.goodsPrice);
    $("#miaoshaPrice").text(goods.secondKillPrice);
    $("#stockCount").text(goods.stockCount);
    countDown();
}

$(function () {
    //countDown();
    getDetail();
});

function getDetail() {
    var goodsId = g_getQueryString("goodsId");
    $.ajax({
        url: "/goods/detail/" + goodsId,
        type: "GET",
        success: function (data) {
            if (data.code == 0) {
                render(data.data);
            } else {
                layer.msg(data.msg);
            }
        },
        error: function () {
            layer.msg("客户端请求有误");
        }
    });
}

function countDown() {
    var remainSeconds = $("#remainSeconds").val();
    var timeout;
    if (remainSeconds > 0) {//秒杀还没开始，倒计时
        $("#buyButton").attr("disabled", true);
        $("#miaoshaTip").html("秒杀倒计时：" + remainSeconds + "秒");
        timeout = setTimeout(function () {
            $("#countDown").text(remainSeconds - 1);
            $("#remainSeconds").val(remainSeconds - 1);
            countDown();
        }, 1000);
    } else if (remainSeconds == 0) {//秒杀进行中
        $("#buyButton").attr("disabled", false);
        if (timeout) {
            clearTimeout(timeout);
        }
        $("#miaoshaTip").html("秒杀进行中");
        $("#verifyCodeImg").attr("src", "/second_kill/verifyCode?goodsId=" + $("#goodsId").val());
        $("#verifyCodeImg").show();
        $("#verifyCode").show();
    } else {//秒杀已经结束
        $("#buyButton").attr("disabled", true);
        $("#miaoshaTip").html("秒杀已经结束");
        $("#verifyCodeImg").hide();
        $("#verifyCode").hide();
    }
}

function refreshVerifyCode() {
    $("#verifyCodeImg").attr("src", "/second_kill/verifyCode?goodsId=" + $("#goodsId").val() + "&timestamp=" + new Date().getTime());
}
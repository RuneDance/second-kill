$(document).ready(function () {
    $("#login-form").validate({
        submitHandler: function () {
            doLogin();
        }
    });

});

function doLogin() {
    g_showLoading();
    var inputPass = $("#password").val();
    var salt = g_passsword_salt;
    var str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
    var password = md5(str);

    $.ajax({
        url: "/user/login",
        type: "POST",
        data: {
            mobile: $("#mobile").val(),
            password: password
        },
        success: function (data) {
            layer.closeAll();
            if (data.code == 0) {
                layer.msg("成功");
                window.location.href = "/goods/list";
            } else {
                layer.msg(data.msg);
            }
        },
        error: function () {
            layer.closeAll();
        }
    });
}
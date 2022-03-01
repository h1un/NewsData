$(document).ready(function () {

    test();
    $("#keyword").keydown(function (key) {
        if (key.keyCode == 13) {
            console.log($("#keyword").val());
            inputKeyword();
        }
    });
});


function test() {
    $.ajax({
        url: '/keyword',
        type: 'get',
        dataType: 'json',
        success: function (json) {

            console.log(json);

        },
        error: function (json) {

            alert("실패");
        }
    });
}


function inputKeyword() {
    $.ajax({
        url: '/keyword',
        type: 'post',
        data: {keyword: $("#keyword").val()},

        success: function (json) {

            console.log(json);

        },
        error: function (request, status, error) {
            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }

    });
}

$(document).ready(function () {

    test();

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

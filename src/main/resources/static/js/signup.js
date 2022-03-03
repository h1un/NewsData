let idCk = true;
$(document).ready(function () {
    $("#userId").keyup(function () {
        $.ajax({
            url: '/signup/' + $("#userId").val(),
            type: 'get',
            success: function (userIdCheck) {

                if (!userIdCheck) {
                    idCk = false;

                }else{
                    idCk = true;


                }

                if (idCk) {
                    $("#testbutton").attr('disabled', 'disabled');
                }else {
                    $("#testbutton").removeAttr('disabled');

                }

            }
        })
    });
});

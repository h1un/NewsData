let idCk = true;
let pwCk = true;

function buttonDisable() {
    if (idCk || pwCk) {
        $("#submit").attr('disabled', 'disabled');
    } else {
        $("#submit").removeAttr('disabled');
    }
}

function keyupId() {
    $("#userId").keyup(function () {
        $.ajax({
            url: '/signup/' + $("#userId").val(),
            type: 'get',
            success: function (userIdCheck) {

                if (!userIdCheck) {
                    idCk = false;

                    $("#idMessage").html('<div class="alert alert-light" role="alert"> &nbsp </div>')

                } else {
                    idCk = true;
                    $("#idMessage").html('<div class="alert alert-success" role="alert"> 이미 등록된 아이디 입니다. </div>')

                }

                buttonDisable();

            }
        })
    });
}

function keyupPw() {
    $("#userPassword").keyup(function () {
        if ($("#passwordCk").val() !== '') {

            if ($("#passwordCk").val() == ($("#userPassword").val())) {
                $("#pwMessage").html('<div class="alert alert-primary" role="alert"> 비밀번호가 일치합니다 </div>')
                pwCk = false;
            } else {
                $("#pwMessage").html('<div class="alert alert-success" role="alert"> 비밀번호가 다릅니다. </div>')
                pwCk = true;
            }
        }

        buttonDisable();
    });
}

function keyupPwCk() {
    $("#passwordCk").keyup(function () {

        if ($("#passwordCk").val() == ($("#userPassword").val())) {
            $("#pwMessage").html('<div class="alert alert-primary" role="alert"> 비밀번호가 일치합니다 </div>')
            pwCk = false;
        } else {
            $("#pwMessage").html('<div class="alert alert-success" role="alert"> 비밀번호가 다릅니다. </div>')
            pwCk = true;
        }

        buttonDisable();
    });
}

$(document).ready(function () {
    keyupId();
    keyupPw();
    keyupPwCk();
    buttonDisable()
});

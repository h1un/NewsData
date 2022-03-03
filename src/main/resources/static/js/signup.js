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
                } else {
                    idCk = true;
                    console.log("이미 등록된 아이디 입니다.")

                }

                buttonDisable();

            }
        })
    });
}

function keyupPw() {
    $("#userPassword").keyup(function () {
        if($("#passwordCk").val()!==''){

            if($("#passwordCk").val()==($("#userPassword").val())){
                console.log("비밀번호가 일치합니다.");
                pwCk = false;
            }else{
                console.log("비밀번호가 다릅니다.")
                pwCk=true;
            }
        }

        buttonDisable();
    });
}
function keyupPwCk() {
    $("#passwordCk").keyup(function () {

            if($("#passwordCk").val()==($("#userPassword").val())){
                console.log("비밀번호가 일치합니다.");
                pwCk = false;
            }else{
                console.log("비밀번호가 다릅니다.")
                pwCk=true;
            }

        buttonDisable();
    });
}
$(document).ready(function () {
    keyupId();
    keyupPw();
    keyupPwCk();
});

let page = 0;

$(document).ready(function () {


    getNewsPage(page);

    $("#keyword").keydown(function (key) {
        if (key.keyCode == 13) {
            console.log($("#keyword").val());
            inputKeyword();
        }
    });
});

function getNewsPage(page) {

    $.ajax({
        url: '/newsList?page=' + page + '&size=20',
        type: 'get',
        dataType: 'json',
        success: function (json) {
            $('#newsList').text("");
            $.each(json.content, function (index, news) {

                let html = '<article class="blog-post">'
                    + '<h4 class="blog-post-title" > <a href="' + news.link + '" style="color:grey">' + news.title + '</a></h4>'
                    + '<p class="blog-post-meta" >' + news.pubDate + '</p>'
                    + '<p>' + news.description + '</p>'
                    + '<hr>'
                    + '</article>'
                $('#newsList').append(html);
                pageNext = page + 1;

                pageQuotient = parseInt(page / 10);
                pageRemainder = page % 10;
                pageLast = json.totalPages - 1;
                pageLastNum = parseInt(json.totalPages / 10);
                $('#page').html("");

                if (pageQuotient == 0) {
                    // $('#page').append('<a class="btn btn-outline-primary" onclick="getNewsPage(0)">first</a>');
                    //
                    // $('#page').append('<a class="btn btn-outline-primary"">Previous</a>');
                } else {
                    $('#page').append('<a class="btn btn-outline-primary" onclick="getNewsPage(0)">first</a>');

                    pagePrevious = (pageQuotient * 10) - 10;
                    $('#page').append('<a class="btn btn-outline-primary" onclick="getNewsPage(' + pagePrevious + ')">Previous</a>');

                }

                for (i = 0; i < pageRemainder; i++) {

                    pageNum = (pageQuotient * 10) + i;
                    pageNumNext = pageNum + 1;
                    $('#page').append('<a class="btn btn-outline-primary" onclick="getNewsPage(' + pageNum + ')">' + pageNumNext + '</a>');

                }
                //17
                $('#page').append('<a class="btn btn-outline-primary" style = "color : red;" onclick=" getNewsPage(' + page + ')">' + pageNext + '</a> ');

                for (i = 1; i < 10 - pageRemainder; i++) {

                    pageNum = page + i;
                    pageNumNext = pageNum + 1;
                    //18 >= 17
                    if (pageNum > pageLast) break;
                    $('#page').append('<a class="btn btn-outline-primary" onclick="getNewsPage(' + pageNum + ')">' + pageNumNext + '</a> ');


                }
                if (pageLastNum == pageQuotient) {
                    //$('#page').append('<li class="page-item disabled"><a class="page-link"">Next</a> ');
                    $('#page').append('<a class="btn btn-outline-primary" onclick="getNewsPage(' + pageLast + ')">last</a></>');

                } else {
                    pageNext = (pageQuotient * 10) + 10;

                    $('#page').append('<a class="btn btn-outline-primary" onclick="getNewsPage(' + pageNext + ')">Next</a> ');
                    $('#page').append('<a class="btn btn-outline-primary" onclick="getNewsPage(' + pageLast + ')">last</a> ');
                }
            });

        }, error: function (json) {
            console.log(json.status)
        }
    })

}


function inputKeyword() {

    $.ajax({
        url: '/keyword/' + $("#keyword").val(),
        type: 'get',
        success: function (keywordCheck) {

            console.log(keywordCheck)
            if (!keywordCheck) {

                $.ajax({
                    url: '/keyword',
                    type: 'post',
                    data: {keyword: $("#keyword").val()},

                    success: function (json) {

                        alert("키워드 등록!");

                        getNewsPage(page);
                    },
                    error: function (request, status, error) {
                        console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                    }

                });
            }else{
                alert("이미 등록된 키워드 입니다.");

            }

        },
        error: function (request, status, error) {
            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }

    });


}

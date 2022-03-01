$(document).ready(function () {


    let page = 0;


    getNewsPage(page);

    test();
    $("#keyword").keydown(function (key) {
        if (key.keyCode == 13) {
            console.log($("#keyword").val());
            inputKeyword();
        }
    });
});


function getNewsPage(page) {

    console.log("??")
    $.ajax({
        url: '/newsList?page=' + page + '&size=20',
        type: 'get',
        dataType: 'json',
        success: function (json) {
            $('#newsList').text("");
            $.each(json.content, function (index, news) {

                let html = '<article class="blog-post">'
                    + '<h4 class="blog-post-title" > <a href="'+news.link+'" style="color:grey">' + news.title + '</a></h4>'
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
                    $('#page').append('<li class="page-item"><a class="page-link" onclick="getNewsPage(0)">first</a></li>');

                    $('#page').append('<li class="page-item disabled"><a class="page-link"">Previous</a></li>');
                } else {
                    $('#page').append('<li class="page-item"><a class="page-link" onclick="getNewsPage(0)">first</a></li>');

                    pagePrevious = (pageQuotient * 10) - 10;
                    $('#page').append('<li class="page-item"><a class="page-link" onclick="getNewsPage(' + pagePrevious + ')">Previous</a></li>');

                }

                for (i = 0; i < pageRemainder; i++) {

                    pageNum = (pageQuotient * 10) + i;
                    pageNumNext = pageNum + 1;
                    $('#page').append('<li class="page-item"><a class="page-link" onclick="getNewsPage(' + pageNum + ')">' + pageNumNext + '</a></li>');

                }
                //17
                $('#page').append('<li class="page-item"><a class="page-link"style = "color : red; onclick="getNewsPage(' + page + ')">' + pageNext + '</a></li>');
                
                for (i = 1; i < 10 - pageRemainder; i++) {

                    pageNum = page + i;
                    pageNumNext = pageNum + 1;
                    //18 >= 17
                    if (pageNum > pageLast) break;
                    $('#page').append('<li class="page-item"><a class="page-link" onclick="getNewsPage(' + pageNum + ')">' + pageNumNext + '</a></li>');


                }
                if (pageLastNum == pageQuotient) {
                    //$('#page').append('<li class="page-item disabled"><a class="page-link"">Next</a></li>');
                    $('#page').append('<li class="page-item"><a class="page-link" onclick="getNewsPage(' + pageLast + ')">last</a></li>');

                } else {
                    pageNext = (pageQuotient * 10) + 10;

                    $('#page').append('<li class="page-item"><a class="page-link" onclick="getNewsPage(' + pageNext + ')">Next</a></li>');
                    $('#page').append('<li class="page-item"><a class="page-link" onclick="getNewsPage(' + pageLast + ')">last</a></li>');
                }
            });

        }, error: function (json) {
            console.log(json.status)
        }
    })

}


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

            alert("키워드 등록!")
        },
        error: function (request, status, error) {
            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }

    });
}

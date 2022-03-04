let page = 0;

$(document).ready(function () {


    getNewsPage(page);
    getKeywords();
    $("#keyword").keydown(function (key) {
        if (key.keyCode == 13) {
            inputKeyword();
        }
    });
});

function getKeywords() {
    $.ajax({
        url: '/keyword',
        type: 'get',
        dataType: 'json',
        success: function (keywords) {

            $("#keywordBar").html('');

            $("#keywordBar").append(' <button class="btn btn-outline-secondary" onclick="getNewsPage(0)" >All</button>');
            let barHtml = '';
            $("#keywordSide").html('');

            let sideHtml = '';


            $.each(keywords, function (index, keyword) {
                barHtml += '<button class="btn btn-outline-secondary" onclick="getNewsPageByKeyword(0,\'' + keyword.keyword + '\')">' + keyword.keyword + '</button>';
                sideHtml += '<li class="list-group-item">' + keyword.keyword +
                    '<button class="btn btn-outline-secondary" onclick="deleteKeyword(' + keyword.keywordIdx + ')">x</button>' +
                    '</li>'

            })
            $("#keywordBar").append(barHtml);
            $("#keywordSide").append(sideHtml);

        }, error: function (json) {
        }
    })

}

function getNewsPage(page) {
    $("#newsTitle").html('News !')
    $.ajax({
        url: '/newsList?page=' + page + '&size=20',
        type: 'get',
        dataType: 'json',
        success: function (json) {

            console.log(json);
            $('#newsList').text("");
            $.each(json.content, function (index, news) {

                let html = '<article class="blog-post">'
                    + '<h4 class="blog-post-title" > <a href="' + news.link + '" style="color:grey">' + news.title + '</a></h4>'
                    + '<p class="blog-post-meta" >' + news.pubDate + '</p>'
                    + '<p>' + news.description + '</p>'
                    + '<hr>'
                    + '</article>'
                $('#newsList').append(html);

                pageNum = json.number;
                pageNext = page + 1;
                pageQuotient = parseInt(page / 10);
                pageRemainder = page % 10;
                pageLast = json.totalPages - 1;
                pageLastNum = json.totalPages==10?0:parseInt(json.totalPages / 10);
                $('#page').html("");

                if (pageQuotient == 0) {
                } else {
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPage(0)">first</a>');

                    pagePrevious = (pageQuotient * 10) - 10;
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPage(' + pagePrevious + ')">Previous</a>');

                }

                for (i = 0; i < pageRemainder; i++) {

                    pageNum = (pageQuotient * 10) + i;
                    pageNumNext = pageNum + 1;
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPage(' + pageNum + ')">' + pageNumNext + '</a>');

                }
                $('#page').append('<a class="btn btn-outline-secondary" style = "color : red;" onclick=" getNewsPage(' + page + ')">' + pageNext + '</a> ');

                for (i = 1; i < 10 - pageRemainder; i++) {

                    pageNum = page + i;
                    pageNumNext = pageNum + 1;
                    //18 >= 17
                    if (pageNum > pageLast) {
                        break;

                    }
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPage(' + pageNum + ')">' + pageNumNext + '</a> ');


                }

                if (pageLastNum == pageQuotient) {
                    //$('#page').append('<li class="page-item disabled"><a class="page-link"">Next</a> ');

                    if(json.number!=json.totalPages-1){
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPage(' + pageLast + ')">last</a></>');
                    }
                } else {

                    pageNextNum = (pageQuotient * 10) + 10;

                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPage(' + pageNextNum + ')">Next</a> ');
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPage(' + pageLast + ')">last</a> ');
                }
            });

        }, error: function (json) {
            console.log(json.status)
        }
    })

}


function getNewsPageByKeyword(page, keyword) {
$("#newsTitle").html(keyword+' News !')
    $.ajax({
        url: '/newsList/' + keyword + '?page=' + page + '&size=20',
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
                pageNum = json.number;
                pageNext = page + 1;
                pageQuotient = parseInt(page / 10);
                pageRemainder = page % 10;
                pageLast = json.totalPages - 1;
                pageLastNum = json.totalPages==10?0:parseInt(json.totalPages / 10);
                $('#page').html("");

                if (pageQuotient == 0) {
                } else {
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPageByKeyword(0,\'' + keyword + '\'))">first</a>');

                    pagePrevious = (pageQuotient * 10) - 10;
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPageByKeyword(' + pagePrevious + ',\'' + keyword + '\')">Previous</a>');

                }

                for (i = 0; i < pageRemainder; i++) {

                    pageNum = (pageQuotient * 10) + i;
                    pageNumNext = pageNum + 1;
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPageByKeyword(' + pageNum + ',\'' + keyword + '\')">' + pageNumNext + '</a>');

                }
                $('#page').append('<a class="btn btn-outline-secondary" style = "color : red;" onclick=" getNewsPageByKeyword(' + page + ',\'' + keyword + '\')">' + pageNext + '</a> ');

                for (i = 1; i < 10 - pageRemainder; i++) {

                    pageNum = page + i;
                    pageNumNext = pageNum + 1;
                    //18 >= 17
                    if (pageNum > pageLast) {
                        break;

                    }
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPageByKeyword(' + pageNum + ',\'' + keyword + '\')">' + pageNumNext + '</a> ');


                }

                if (pageLastNum == pageQuotient) {
                    //$('#page').append('<li class="page-item disabled"><a class="page-link"">Next</a> ');

                    if(json.number!=json.totalPages-1){
                        $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPageByKeyword(' + pageLast + ',\'' + keyword + '\')">last</a></>');
                    }
                } else {

                    pageNextNum = (pageQuotient * 10) + 10;

                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPageByKeyword(' + pageNextNum + ',\'' + keyword + '\')">Next</a> ');
                    $('#page').append('<a class="btn btn-outline-secondary" onclick="getNewsPageByKeyword(' + pageLast + ',\'' + keyword + '\')">last</a> ');
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

                        $("#keywordBar").append('<button class="btn btn-outline-secondary" onclick="getNewsPageByKeyword(0,\'' + json.keyword + '\')">' + json.keyword + '</button>');
                        $("#keywordSide").append('<li class="list-group-item">' + json.keyword +
                            '<button class="btn btn-outline-secondary" onclick="deleteKeyword(' + json.keywordIdx + ')">x</button>' +
                            '</li>');

                        getNewsPage(page);

                    },
                    error: function (request, status, error) {
                        console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                    }

                });
            } else {
                alert("이미 등록된 키워드 입니다.");

            }

        },
        error: function (request, status, error) {
            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }

    });


}


function deleteKeyword(keywordIdx) {

    let deleteCk = confirm("삭제하시겠습니까?")

    if (deleteCk) {

        $.ajax({
            url: '/keyword/' + keywordIdx,
            type: 'delete',
            success: function () {
                alert("삭제되었습니다!")
                location.reload();

            },
            error: function (request, status, error) {
                console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }

        });
    }


}

$(document).ready(function checkIfAdmin(){
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080//person/isadmin',
        contentType: 'application/json',
        dataType: 'json',
        statusCode: {
            200: function (data) {
                console.log(data);
                if (data) {
                    document.getElementById('adminButton').style.visibility = 'visible';
                }
            }
        }
    });
});
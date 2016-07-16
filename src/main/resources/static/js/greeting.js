function loadGreeting() {
    return $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/person',
        dataType: 'json',
        statusCode: {
            200: function (data) {
                return data;
            }
        }
    });
}
loadGreeting();
function loadSessions() {
    return $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/sessions',
        dataType: 'json',
        statusCode: {
            200: function (data) {
                return data;
            }
        }
    });
}

function drawSessionList() {
    loadSessions().then(function(sessions) {
        var sessionListTemplate = Handlebars.compile(document.querySelector('#session-list').innerHTML);
        var sessionTemplate = Handlebars.compile(document.querySelector('#session').innerHTML);

        var sessionList = '';
        sessions.forEach(function(session) {
            sessionList += sessionTemplate(session);
        });

        var sessionList = sessionListTemplate({
            body: sessionList
        });

        var sessionListContainer = document.createElement('div');
        sessionListContainer.innerHTML = sessionList;
        document.body.appendChild(sessionListContainer);
    });
}

function addSession(event){

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/sessions',
        data: JSON.stringify({
            title:document.session.title.value,
            host:document.session.host.value,
            description:document.session.description.value
        }),
        contentType: 'application/json',
        dataType: 'json',
        statusCode: {
            201: function () {
                var sessionList = document.querySelector(".session-list");
                if (sessionList) {
                    document.body.removeChild(sessionList.parentNode)
                }
                //drawSessionList();
            }
        }
    });
    event.preventDefault();
}

function removeElement(event, id){
    console.log(id);
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/sessions/' + id,
        statusCode: {
            200: function () {
                var sessionList = document.querySelector(".session-list");
                document.body.removeChild(sessionList.parentNode)
                drawSessionList();
            }
        }
    });
    event.preventDefault();
}

drawSessionList();
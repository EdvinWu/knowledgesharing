var currentSessionView = "all";

function loadSessions(status) {

    if (status === undefined) {
        status = currentSessionView;
    } else {
        currentSessionView = status;
    }

    return $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/sessions/bystatus/' + status,
        dataType: 'json',
        statusCode: {
            200: function (data) {
                return data;
            }
        }
    });
}

function drawSessionList(status) {
    loadSessions(status).then(function (sessions) {
        var sessionList = document.querySelector(".session-list");
        if (sessionList) {
            document.body.removeChild(sessionList.parentNode);
        }

        var sessionListTemplate = Handlebars.compile(document.querySelector('#session-list').innerHTML);
        var sessionTemplate = Handlebars.compile(document.querySelector('#session').innerHTML);

        var sessionList = '';
        sessions.forEach(function (session) {
            sessionList += sessionTemplate(session);
        });

        var sessionList = sessionListTemplate({
            body: sessionList
        });

        var listTitle;
        switch (currentSessionView) {
            case "done":
                listTitle = "Done Sessions";
                break;
            case "pending":
                listTitle = "Pending Sessions";
                break;
            case "approved":
                listTitle = "Approved Sessions";
                break;
            default:
                listTitle = "All Sessions";
                break;
        }
        var elListTitle = document.getElementById("sessionListName");
        if (elListTitle) {
            elListTitle.innerHTML = listTitle;
        }

        var sessionListContainer = document.createElement('div');
        sessionListContainer.innerHTML = sessionList;
        document.body.appendChild(sessionListContainer);

    });
}

function addSession(event) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/sessions',
        data: JSON.stringify({
            title: document.session.title.value,
            author: document.session.host.value,
            description: document.session.description.value
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
    window.location = "http://localhost:8080/index";
}

function addUser(event){
    $.post("/adduser", $("#registrationform").serialize())
        .fail(function () {
            console.log("error")
        })
        .done(function () {
            window.location = "http://localhost:8080/login";
        });
    event.preventDefault();
}

function changeSessionStatus(event, id, status) {
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8080/sessions/' + id + '/changestatus/' + status,
        statusCode: {
            200: function (data) {
                drawSessionList();
                return data;
            }
        }
    });
    event.preventDefault();

}

function removeElement(event, id) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/sessions/' + id,
        statusCode: {
            200: function () {
                var sessionList = document.querySelector(".session-list");
                document.body.removeChild(sessionList.parentNode);
                drawSessionList();
            }
        }
    });
    event.preventDefault();
}



var chatVersion = 0;
var refreshRate = 2000;

function appendToChatArea(entries) {
    $.each(entries || [], appendChatEntry);

    var scroller = $("#chatarea");
    var height = scroller[0].scrollHeight - $(scroller).height();
    $(scroller).stop().animate({ scrollTop: height }, "slow");
}
function appendChatEntry(index, entry){
    var entryElement = createChatEntry(entry);
    $("#chatarea").append(entryElement).append("<br>");
}
function createChatEntry (entry){
    return $("<span class=\"success\">").append(entry.username + "> " + entry.chatString);
}
function ajaxChatContent() {
    $.ajax({
        url: "chat",
        data: "chatversion=" + chatVersion,
        dataType: 'json',
        success: function(data) {
            if (data.version !== chatVersion) {
                chatVersion = data.version;
                appendToChatArea(data.entries);
            }
            triggerAjaxChatContent();
        },
        error: function(error) {
            triggerAjaxChatContent();
        }
    });
}
$(function() {
    $("#chatform").submit(function() {
        $.ajax({
            data: $(this).serialize(),
            url: this.action,
            timeout: 2000,
            error: function() {
            },
            success: function(r) {
            }
        });
        $("#userstring").val("");
        return false;
    });
});

function triggerAjaxChatContent() {
    setTimeout(ajaxChatContent, refreshRate);
}

$(function() {

    setInterval(ajaxUsersList, refreshRate);
    triggerAjaxChatContent();
});

function refreshUsersList(users) {
    $("#usersTable").empty();
    $('<tr>' + '<th>'+"user name"+'</th>'+'<th>'+"role"+'</th>'+'</tr>').appendTo($("#usersTable"));

    $.each(users || [], function(index,user) {
        var roletoshow;
        if (user.isAdmin)
            roletoshow="admin";
        else roletoshow="trader"
        $('<tr>' + '<td>'+user.name+'</td>'+'<td>'+roletoshow+'</td>'+'</tr>').appendTo($("#usersTable"));
    });
}
function ajaxUsersList() {
    $.ajax({
        url: "usersmap",
        data:{type:1},
        success: function(users) {
            refreshUsersList(users);
        }
    });
}
function refreshMoveList(moves) {

    $("#accountTable").empty();
    $('<tr>' + '<th>'+"type"+'</th>'+'<th>'+"date"+'</th>'+'<th>'+"amount"+'</th>'+'<th>'+"balance before"+'</th>' +'<th>'+"balance after"+'</th>'+'</tr>').appendTo($("#accountTable"));
    $.each(moves || [], function(index,move) {

        var type;
        if (move.type===1)
            type="buy";
        if (move.type===2)
            type="sell";
        else type ="load";
        $('<tr>' + '<td>'+type+'</td>'+'<td>'+move.date+'</td>'+'<td>'+move.amount+'</td>' +'<td>'+move.balanceBefore+'</td>' +'<td>'+move.balanceAfter+'</td>'+'</tr>').appendTo($("#accountTable"));
    });
}
function ajaxMovesList() {
    $.ajax({
        url: "movesList",
        success: function (moves) {
            refreshMoveList(moves);
        }
    });
}
function refreshStocksList(stocks) {
    $("#stocksTable").empty();
    $('<tr>' + '<th>'+"company name"+'</th>'+'<th>'+"symbol"+'</th>'+'<th>'+"rate"+'</th>'+'<th>'+"total"+'</th>'+'<th>' +"show stock"+'</th>'+'</tr>').appendTo($("#stocksTable"))
    $.each(stocks || [], function(index,stock) {
        $('<tr>' + '<td>'+stock.company+'</td>'+'<td>'+stock.symbol+'</td>'+ '<td>'+stock.price+'</td>'+'+<td>'+stock.sumTransactions+'</td>'+'<td>'+'<form id="onestock'+index+'" method="POST" action="onestock">'+'<input type="submit" value="Trade Me"/>' +'<input type="hidden" id="stockname'+index+'" name="stockname" value="">'+ '</form>' +'</td>'+'</tr>').appendTo($("#stocksTable"));
        var id="stockname"+index;
       document.getElementById(id).value=stock.symbol;

    })
}
function ajaxStocksList(){
    $.ajax({
        url: "stocksmap",
        success: function (stocks) {
            refreshStocksList(stocks);
        }
    });
}
$(function () {
    $("#onestock").submit(function () {
        $.ajax({
            data:$(this).serialize(),
            url:this.action,
            method:"POST",
            success:function () {
                console.log("succses");
            }
        });
        return false;
    });
});
$(function () {
    $("#newStockform").submit(function() {
        var error;
        var symbol=document.getElementById("symbol").value;
        var amount=document.getElementById("amount").value;
        var value=document.getElementById("value").value;

        if (!symbol.match(/^[a-zA-Z]+$/)) {
            error="please enter symbol with only capital letters";
            showOneNotiError(error);
        }
        else if (amount<=0) {
            error="please enter amount bigger then 0";
            showOneNotiError(error);
        }
        else if (value<=0) {
            error="please enter value bigger then 0";
            showOneNotiError(error);
        }
        else {
            $.ajax({
                data: $(this).serialize(),
                url: "usersmap",
                method: "POST",
                success: function () {
                    text="A new stock was made";
                    showOneNotiSuccess(text);
                    showOneNotiSuccessModal(text);
                },
                error: function (error) {
                    showOneNotiErrorModal(error.responseText);
                    showOneNotiError(error.responseText);

                }
            });
        }
        $("#company").val("");
            $("#symbol").val("");
            $("#amount").val("");
            $("#value").val("");
            return false;
        })
})
 $(function() {
     $("#fileform").submit(function () {
         var file1 = this[0].files[0];
         var text;
         var formData = new FormData();
         formData.append("fake-key-1", file1);
         text= "The file submitted successfully";
         $.ajax({
             data: formData,
             method: 'POST',
             processData: false,
             contentType: false,
             url: this.action,
             timeout: 2000,
             error: function (error) {
                 text = "the file doesnt loaded - " + error.responseText;
                 showOneNotiError(text);
             },
             success: function (){
                 ajaxStocksList();
                 text="The file submitted successfully";
                showOneNotiSuccess(text);

             }
         });
         return false;
     })
 })
$(function() {
    ajaxUsersList();
    ajaxStocksList();
    ajaxMovesList();
   setInterval(ajaxUsersList,2000);
   setInterval(ajaxStocksList,2000);
   setInterval(ajaxMovesList,2000)
});
function myPrompt() {
    var amount = prompt("Please Enter Amount");
    var text="please enter a number bigger then 0";
    if (amount===""  || isNaN(amount)) {
        showOneNotiError(text);
    }
    else if (amount != null) {
        $.ajax({
            url: "movesList",
            method: "POST",
            data: {amountToLoad:amount},
            dataType: "text",
            success: function () {
                text="your account was loaded in "+amount;
                showOneNotiSuccess(text);
            }
        });
    }
}
function ajaxNotifications() {
    $.ajax({
        url: "../oneStock/notification",
        success: function (notifications) {
            showNotifications(notifications);
        }
    });
}
function showOneNotiSuccess(text)
{
    var att=document.getElementById("messagePlace");
    if (att.childElementCount<1) {
        $('<div id="messageContainer">').appendTo($("#messagePlace"));
    }
    $("#messageContainer").append('<div id="success-placeholder" class="alert-success alert-dismissable " role="alert">');
    $("#success-placeholder").append(text);
    att=document.getElementById("messageContainer");
    if (att.childElementCount==1) {
        $('<span id="closebtn" class="closebtn" onclick=this.parentElement.parentElement.remove();>&times;</span>' + '<br/>').appendTo($("#success-placeholder"));
    }
    else {
        $("#success-placeholder").append('<br>');
    }
}
function showOneNotiSuccessModal(text)
{
    var att=document.getElementById("messagePlaceModal");
    if (att.childElementCount<1) {
        $('<div id="messageContainerModal">').appendTo($("#messagePlaceModal"));
    }
    $("#messageContainerModal").append('<div id="success-placeholderModal" class="alert-success alert-dismissable " role="alert">');
    $("#success-placeholderModal").append(text);
    att=document.getElementById("messageContainerModal");
    if (att.childElementCount==1) {
        $('<span id="closebtn" class="closebtn" onclick=this.parentElement.parentElement.remove();>&times;</span>' + '<br/>').appendTo($("#success-placeholderModal"));
    }
    else {
        $("#success-placeholderModal").append('<br>');
    }
}
function showOneNotiErrorModal(text)
{
    var att=document.getElementById("messagePlaceModal");
    if (att.childElementCount<1) {
        $('<div id="messageContainerModal">').appendTo($("#messagePlaceModal"));
    }
    $("#messageContainerModal").append('<div id="error-placeholderModal" class="alert-danger alert-dismissable " role="alert">');
    $("#error-placeholderModal").append(text);
    att=document.getElementById("messageContainerModal");
    if (att.childElementCount==1) {
        $('<span id="closebtn" class="closebtn" onclick=this.parentElement.parentElement.remove();>&times;</span>' + '<br/>').appendTo($("#error-placeholderModal"));
    }
    else {
        $("#error-placeholderModal").append('<br>');
    }
}
function showOneNotiError(text)
{
    var att=document.getElementById("messagePlace");
    if (att.childElementCount<1) {
        $('<div id="messageContainer">').appendTo($("#messagePlace"));
    }
    $("#messageContainer").append('<div id="error-placeholder" class="alert-danger alert-dismissable " role="alert">');
    $("#error-placeholder").append(text);
    att=document.getElementById("messageContainer");
    if (att.childElementCount==1) {
        $('<span id="closebtn" class="closebtn" onclick=this.parentElement.parentElement.remove();>&times;</span>' + '<br/>').appendTo($("#error-placeholder"));
    }
    else {
        $("#error-placeholder").append('<br>');
    }

}
function showNotifications(notifications) {
    $.each(notifications || [], function(index,not){
        showOneNotiSuccess(not);
        showOneNotiSuccessModal(not);

    });
}
$(function () {
    setInterval(ajaxNotifications,2000);
})
$(function(){
    {
        var modal = document.getElementById("myModal");
        var btn = document.getElementById("myBtn");
        var span = document.getElementsByClassName("close")[0];
        btn.onclick = function () {
            modal.style.display = "block";
        }
        span.onclick = function () {
         $("#errorModal").empty();
         $("#successModal").empty();
         modal.style.display = "none";
        }
    }
});



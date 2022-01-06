
function ajaxStock () {
    $.ajax({
        url: "../info/onestock",
        method: "GET",
        success: function (stock) {
           refreshStock(stock);
        }
    });
}
function refreshStock(stock) {
    $("#stock").empty();
    $('<h4>'+"information about " +stock.symbol+'</h4>').appendTo($("#stock"));
    $('<div>'+"Company: "+ stock.company+'</div>').appendTo($("#stock"));
    $('<div>'+"Rate: "+ stock.price+'</div>').appendTo($("#stock"));
    $('<div>'+"Total: "+ stock.sumTransactions+'</div>').appendTo($("#stock"));
    $('<h7>The transactions:</h7>').appendTo($("#stock"));
    $('<ol id="trans"></ol>').appendTo($('#stock'));

    var trans=stock.transaction;
    showTrans(trans);
    showMyHolding(stock);

}
$(function() {
    ajaxStock();
    setInterval(ajaxStock,2000);
});
function showTrans(trans) {
    $('#trans').empty();
    $.each(trans || [], function(index,tran){
        $('<li>'+'<ul id="tran'+index+'">'+  '<li>'+"Date: "+tran.time+'</li>'+'<li>'+"Amount: "+tran.stockAmount+'</li>'+'<li>'+"Rate: "+tran.price+'</li>'+'</ul>'+'</li>').appendTo($('#trans'));

    });
}
function showMyHolding(stock) {
    $.ajax({
        data:{name:stock.symbol,type:2},
        url:"../info/usersmap",
        method:"GET",
        success:function(amount) {
            $('<h4>'+"Your holding :"+amount+'</h4>').appendTo($("#stock"));
        }
    })
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
$(function () {
    $("#newCommandform").submit(function() {
        var error;

        var amount = document.getElementById("amount");
        var price = document.getElementById("limit");

        if (amount.value <= 0 || isNaN(amount.value)) {
            error = "please enter amount bigger then 0";
            showOneNotiErrorModal(error);

        }
       else if ((price.value <= 0 || isNaN(price.value)) && (!(price.disabled))){
            error = "please enter price bigger then 0";
            showOneNotiErrorModal(error);

        } else {
            $.ajax({
                data: $(this).serialize(),
                url: this.action,
                method: "POST",
                success: function () {
                    //  console.log("sucsses");
                    $("input[name='toggle1']").prop('checked', false);
                    $("input[name='toggle']").prop('checked', false);
                    showOneNotiSuccess("A new command was made");
                    showOneNotiSuccessModal("A new command was made");
                    ajaxNotifications();
                },
                error: function (error) {
                    showOneNotiError(error.responseText);
                    showOneNotiErrorModal(error.responseText);

                }
            });
        }

        $("#amount").val("");
        $("#limit").val("");
        return false;
    })
})

function ajaxNotifications() {
    $.ajax({
        url: "notification",
        success: function (notifications) {
            showNotifications(notifications);
        },
        error: function () {
            //console.log("error")
        }
    });
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

function showNotifications(notifications) {

    $.each(notifications || [], function(index,not){
        showOneNotiSuccess(not);
        showOneNotiSuccessModal(not);
    });
}
$(function () {
    ajaxNotifications();
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
            modal.style.display = "none";
        }
    }
});
$(function () {
    $("input[name='toggle1']").change(function() {
    if ($(this).val() === "MKT"){
        $("#limit").prop('disabled', true);
    } else {
        $("#limit").prop('disabled', false);
    }
})
});


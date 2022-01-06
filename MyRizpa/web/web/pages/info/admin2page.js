
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
        success: function(users) {
            refreshUsersList(users);
        }
    });
}
function refreshStocksList(stocks) {
    $("#stocksTable").empty();
    $('<tr>' + '<th>'+"company name"+'</th>'+'<th>'+"symbol"+'</th>'+'<th>'+"rate"+'</th>'+'<th>'+"total"+'</th>'+'<th>' +"show stock"+'</th>'+'</tr>').appendTo($("#stocksTable"))
    $.each(stocks || [], function(index,stock) {
        $('<tr>' + '<td>'+stock.company+'</td>'+'<td>'+stock.symbol+'</td>'+ '<td>'+stock.price+'</td>'+'+<td>'+stock.sumTransactions+'</td>'+'<td>'+'<form id="onestock'+index+'" method="POST" action="onestock">'+'<input type="submit" value="Show Me"/>' +'<input type="hidden" id="stockname'+index+'" name="stockname" value="">'+ '</form>' +'</td>'+'</tr>').appendTo($("#stocksTable"));
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
$(function() {
    ajaxUsersList();
    ajaxStocksList();
    setInterval(ajaxUsersList,2000);
    setInterval(ajaxStocksList,2000);
});
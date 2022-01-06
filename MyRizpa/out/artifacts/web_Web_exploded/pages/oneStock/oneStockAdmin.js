
function ajaxStock () {
    $.ajax({
        url: "../info/onestock",
        method: "GET",
        success: function (stock) {
            showStock(stock);
        }
    });
}
function showStock(stock) {
    $("#stock").empty();
    $('<h4>'+"information about " +stock.symbol+'</h4>').appendTo($("#stock"));
    $('<div>'+"Company: "+ stock.company+'</div>').appendTo($("#stock"));

    $('<div>'+"Rate: "+ stock.price+'</div>').appendTo($("#stock"));
    $('<div>'+"Total: "+ stock.sumTransactions+'</div>').appendTo($("#stock"));
    $('<h7>The Transactions:</h7>').appendTo($("#stock"));
    $('<ol id="trans"></ol>').appendTo($('#stock'));

    $('<h7>The Buy Commands:</h7>').appendTo($("#stock"));
    $('<ol id="Bcommands">'+" "+'</ol>').appendTo($('#stock'));

    $('<h7>The Sell Commands:</h7>').appendTo($("#stock"));
    $('<ol id="Scommands">'+" "+'</ol>').appendTo($('#stock'));

    var trans=stock.transaction;
    var Scommand=stock.sell;
    var Bcommand=stock.buy;
    showTransAndCommands(trans,Scommand,Bcommand);
}
$(function() {
    ajaxStock();
    setInterval(ajaxStock,2000);
});
function showTransAndCommands(trans,Scommand,Bcommand) {

    $('#trans').empty();
    $.each(trans || [], function(index,tran){
        $('<li>'+'<ul id="tran'+index+'">'+  '<li>'+"Date: "+tran.time+'</li>'+'<li>'+"Amount: "+tran.stockAmount+'</li>'+'<li>'+"Rate: "+tran.price+'</li>'+'</ul>'+'</li>').appendTo($('#trans'));

    });
    $('#Scommands').empty();

    $.each(Scommand || [], function(index,scommand){
        var type;
        if (scommand.type===1)
            type="MKT";
        if (scommand.type===0)
            type="LMT";
        if (scommand.type===2)
            type="IOC";
        if (scommand.type===3)
            type="FOK"

        $('<li>'+'<ul id="sell'+index+'">'+  '<li>'+"Date: "+scommand.time+'</li>'+'<li>'+"Amount: "+scommand.amount+'</li>'+'<li>'+"Rate: "+scommand.limitPrice+'</li>'+'<li>'+"Type: "+type+'</li>'+'<li>'+"Made By: "+scommand.userName+'</li>' +'</ul>'+'</li>').appendTo($('#Scommands'));
    });
    $('#Bcommandsli').empty();
    $.each(Bcommand || [], function(index,bcommand){
        var type;
        if (bcommand.type===1)
            type="MKT";
        if (bcommand.type===0)
            type="LMT";
        if (bcommand.type===2)
            type="IOC";
        if (bcommand.type===3)
            type="FOK"

        $('<li>'+'<ul id="buy'+index+'">'+  '<li>'+"Date: "+bcommand.time+'</li>'+'<li>'+"Amount: "+bcommand.amount+'</li>'+'<li>'+"Rate: "+bcommand.limitPrice+'</li>'+'<li>'+"Type: "+type+'</li>'+'<li>'+"Made By: "+bcommand.userName+'</li>' +'</ul>'+'</li>').appendTo($('#Bcommands'));

    });
}



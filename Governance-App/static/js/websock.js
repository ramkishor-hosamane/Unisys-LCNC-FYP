$(document).ready(function() {

    var socket = io.connect('http://127.0.0.1:5000');

    var sock = io()

    sock.on('audit', function(data) {
        
        console.log(data)
        var row =`\
        <tr><td>`+data['userName']+`</td>\
        <td>`+data['auditDocumentName']+`</td>\
        <td>`+data['auditModuleName']+`</td>\
        <td>`+data['operation']+`</td>\
        <td>`+data['timestamp']+`</td>\
    </tr>`;
        $("#audit_body").prepend(row)


    });

    // sock.on('switch', function(data) {
    //     $("#switch"+data["num"]).prop("checked","true")
    //     console.log("checked");
    // });

  
});

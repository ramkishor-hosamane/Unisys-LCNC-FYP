 //
    //$(".state_check").prop("checked","true");
    function  updatePowerStatus(toggle_box,project,index){
        console.log(toggle_box['control'])
        //toggle_box['control'].preventDefault()
        event.preventDefault()

        toogleLoader(true)
        console.log("Giving out")
        
        var check = toggle_box['control']
        var project_status = $(check).prop("checked")
        var dat = {
            project:project,
            switch_status:project_status
            }
        if(project_status == true)
        {
            setTimeout(stopToggling,2000,check,index)
            $("#load-text").text("Turning Off "+ project)

        }
        else
        {
            $("#load-text").text("Turning On "+ project)

            setTimeout(stopToggling,20000,check,index)            
        }

        $.ajax({
            type: 'POST',
            data:dat,
            url: "/updatepowerstatus",
            dataType: 'json',
            success: function (data) {
                console.log()
                //alert(data['current_status'])
                if(data['current_status'] == 'true')
                    $("#"+project+"_visit").removeClass("disabled")
                else
                    $("#"+project+"_visit").addClass("disabled")

            }
        });
        

    }

function toogleLoader(status){
    console.log("Toggling")
    $("#overlay-activator").toggleClass("overlay")
    if(status){
        $("#spinner").css({'z-index':'20000'});
        
    }
    else{
        $("#spinner").css({'z-index':'0'});

    }
}
function stopToggling(check,index){
    toogleLoader(false)

    var checkbox = document.getElementById("switch"+index)
    var status = $(check).prop("checked")
    checkbox.checked = !checkbox.checked;

    //$(check).prop("checked",!$(check).prop("checked"))
    console.log("Happend")
}


    $(document).ready(function() {
$("#file").change(function(e){

    var project_name = $("#file").val().split("\\").pop();
    $("#file_path").text(project_name)
    
    

});








});
    










function  deleteAudit(project_name,auditno,index){
    var dat = {
        project:project_name,
        auditno:auditno
        }

    $.ajax({
        type: 'POST',
        data:dat,
        url: "/deleteaudit",
        dataType: 'json',
        success: function (data) {
            $("#row-"+index).remove();



        }
    });
    

}










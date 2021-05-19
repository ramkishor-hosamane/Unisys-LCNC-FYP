 //
    //$(".state_check").prop("checked","true");
    function  updatePowerStatus(toggle_box,project,index){
        console.log(toggle_box['control'])
        //toggle_box['control'].preventDefault()
        event.preventDefault()
        toogleLoader(true)
        console.log("Giving out")
        
        var check = toggle_box['control']
        var dat = {
            project:project,
            switch_status:$(check).prop("checked")
            }
            setTimeout(function(){
                toogleLoader(false)

                var checkbox = document.getElementById("switch"+index)
                var status = $(check).prop("checked")
                checkbox.checked = !checkbox.checked;

                //$(check).prop("checked",!$(check).prop("checked"))
                console.log("Happend")
            },2000)
        $.ajax({
            type: 'POST',
            data:dat,
            url: "/updatepowerstatus",
            dataType: 'json',
            success: function (data) {
                console.log(data['current_status'])
                // setTimeout(function(){
                //     toogleLoader(false)
                //     console.log("Happend")
                // },2000)



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



    $(document).ready(function() {
$("#file").change(function(e){

    var project_name = $("#file").val().split("\\").pop();
    $("#file_path").text(project_name)
    
    

});








});
    
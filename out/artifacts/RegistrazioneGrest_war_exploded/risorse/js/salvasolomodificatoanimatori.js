//quando un elemento viene modificato, cerca gli elementi con lo stesso name, quindi sono il select del laboratorio
//e il checkbox responsabile, e aggiunge una classe che indica che sono stati modificati, se non è già presente
$("form input:checkbox, select").change(function () {
    var name = $(this).attr("name");
    $("input[name|='" + name + "'], select[name|='" + name + "']").addClass("changed");
});

//prima di inviare il form elimina tutti gli elementi che non sono stati modificati
$("form").submit(function(){
    $("input:checkbox:not(.changed), select:not(.changed)").remove();
});
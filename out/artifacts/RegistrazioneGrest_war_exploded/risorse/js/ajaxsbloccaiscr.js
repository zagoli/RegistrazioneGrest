var htmlfail = "<div class='row' id='sbloccotext'>\n\
                    <div class='col-sm rounded bg-danger'>\n\
                        <p class='text-white text-uppercase font-weight-bold text-center pt-2'>Codice non esistente o gi&agrave; utilizzato!</p>\n\
                    </div>\n\
                </div>";

$("#btnsblocco").click(function () {
    var valido = $("#mainForm").parsley().validate(); //prima convalido il form, così se non è valido evito di sprecare un codice
    if (valido) {
        var codice = $("#codicesblocco").val();
        var url = '/RegistrazioneGrest/App/SbloccaIscrizioni';
        $.getJSON(
                url,
                {
                    scope: "verifica",
                    codice: codice
                },
                //quando sono arrivati i dati
                function (data) {
                    var resultString = data.result;
                    var result = toBoolean(resultString);
                    if (result) {
                        $("#mainForm").submit();
                    } else {
                        $("#codiceForm").after(htmlfail);
                        $("#sbloccotext").delay(5000).fadeOut("slow", function () {
                            $(this).remove();
                        });
                    }
                }
            );
        } else {
            $("#codicesblocco").attr("placeholder","Prima completa correttamente il modulo!");
            $("#codicesblocco").addClass("border-danger");
        }
});

function toBoolean(v) {
    return v === "false" ? false : !!v;
}
;
//cambia stato iscrizioni ragazzi
$(function toggleiscrrag() {
    var url = '/RegistrazioneGrest/App/StatoIscrizioni';
    $('#ISCRRAG').change(function () {
        $.getJSON(
            url,
            {
                target: "ISCRRAG",
                state: $(this).prop('checked')
            },
            //quando sono arrivati i dati
            showRequestResult
        );
    });
});
//cambia stato iscrizioni animatori
$(function toggleiscran() {
    var url = '/RegistrazioneGrest/App/StatoIscrizioni';
    $('#ISCRAN').change(function () {
        $.getJSON(
            url,
            {
                target: "ISCRAN",
                state: $(this).prop('checked')
            },
            //quando sono arrivati i dati
            showRequestResult
        );
    });
});
//cambia stato iscrizioni terza media
$(function toggleiscrter() {
    var url = '/RegistrazioneGrest/App/StatoIscrizioni';
    $('#ISCRTER').change(function () {
        $.getJSON(
            url,
            {
                target: "ISCRTER",
                state: $(this).prop('checked')
            },
            //quando sono arrivati i dati
            showRequestResult
        );
    });
});

function showRequestResult(data) {
    var resultString = data.result;
    var result = toBoolean(resultString);
    if (result) {
        alert("Salvato con successo.")
    } else {
        alert("Errore!")
    }
}

function toBoolean(v) {
    return v === "false" ? false : !!v;
};
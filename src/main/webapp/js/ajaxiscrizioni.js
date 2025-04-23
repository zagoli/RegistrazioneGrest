let currentSelector = '';
const url = '/RegistrazioneGrest/App/StatoIscrizioni';
//cambia stato iscrizioni ragazzi
$(function toggleiscrrag() {
    $('#ISCRRAG').change(function () {
        currentSelector = '#ISCRRAG';
        makeRequest.call(this);
    });
});
//cambia stato iscrizioni animatori
$(function toggleiscran() {
    $('#ISCRAN').change(function () {
        currentSelector = '#ISCRAN';
        makeRequest.call(this);
    });
});

//cambia stato iscrizioni terza media
$(function toggleiscrter() {
    $('#ISCRTER').change(function () {
        currentSelector = '#ISCRTER';
        makeRequest.call(this);
    });
});

function makeRequest() {
    $.getJSON(url, {
        target: currentSelector.substring(1), state: $(this).prop('checked')
    }).done(showRequestResult)
        .fail(setSwitchOff);
}

function setSwitchOff(error) {
    alert("Errore: " + error);
    $(currentSelector).prop("checked", false);
}

function showRequestResult(data) {
    const resultString = data.result;
    const error = data.error;
    const result = toBoolean(resultString);
    if (result) {
        alert("Salvato con successo.");
    } else {
        setSwitchOff(error);
    }
}

function toBoolean(v) {
    return v === "false" ? false : !!v;
}
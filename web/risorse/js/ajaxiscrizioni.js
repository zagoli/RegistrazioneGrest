//cambia stato iscrizioni ragazzi
$(function toggleiscrrag() {
    $('#ISCRRAG').change(function () {
        $.ajax({
            url: '/RegistrazioneGrest/App/StatoIscrizioni?target=ISCRRAG&state=' + $(this).prop('checked'),
            type: 'GET',
            success: function () {
                alert("Salvato con successo.")
            },
            error: function () {
                alert("Errore!")
            }
        }); 
    });
});
//cambia stato iscrizioni animatori
$(function toggleiscran() {
    $('#ISCRAN').change(function () {
        $.ajax({
            url: '/RegistrazioneGrest/App/StatoIscrizioni?target=ISCRAN&state=' + $(this).prop('checked'),
            type: 'GET',
            success: function () {
                alert("Salvato con successo.")
            },
            error: function () {
                alert("Errore!")
            }
        }); 
    });
});
//cambia stato iscrizioni terza media
$(function toggleiscrter() {
    $('#ISCRTER').change(function () {
        $.ajax({
            url: '/RegistrazioneGrest/App/StatoIscrizioni?target=ISCRTER&state=' + $(this).prop('checked'),
            type: 'GET',
            success: function () {
                alert("Salvato con successo.")
            },
            error: function () {
                alert("Errore!")
            }
        }); 
    });
});
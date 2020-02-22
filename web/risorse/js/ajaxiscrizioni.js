//cambia stato iscrizioni ragazzi
$(function toggleiscrrag() {
    $('#ISCRRAG').change(function () {
        $.ajax({
            url:'/RegistrazioneGrest/App/StatoIscrizioni?target=ISCRRAG&state='+ $(this).prop('checked'),
            type: 'GET',
            success:function(){$('#labelISCRRAG').append('<small><i> Salvato</i></small>');},
            error:function(){$('#labelISCRRAG').append('<small><i> Errore</i></small>');}
        }); 
    });
});
//cambia stato iscrizioni animatori
$(function toggleiscran() {
    $('#ISCRAN').change(function () {
        $.ajax({
            url:'/RegistrazioneGrest/App/StatoIscrizioni?target=ISCRAN&state='+ $(this).prop('checked'),
            type: 'GET',
            success:function(){$('#labelISCRAN').append('<small><i> Salvato</i></small>');},
            error:function(){$('#labelISCRAN').append('<small><i> Errore</i></small>');}
        }); 
    });
});
//cambia stato iscrizioni terza media
$(function toggleiscrter() {
    $('#ISCRTER').change(function () {
        $.ajax({
            url:'/RegistrazioneGrest/App/StatoIscrizioni?target=ISCRTER&state='+ $(this).prop('checked'),
            type: 'GET',
            success:function(){$('#labelISCRTER').append('<small><i> Salvato</i></small>');},
            error:function(){$('#labelISCRTER').append('<small><i> Errore</i></small>');}
        }); 
    });
});
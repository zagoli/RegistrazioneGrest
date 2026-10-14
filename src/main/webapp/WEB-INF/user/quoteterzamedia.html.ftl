<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="container">
    <p>
        <b> Quote per l'iscrizione dei ragazzi di terza media al Grest di Balconi
            <@ui.currentYear indent=16/>
        </b>
    </p>
    <@ui.quoteTable prices=[prezzo_4, prezzo_3, prezzo_2, prezzo_1]/>
    <i>se il ragazzo non è residente nel comune di Pescantina, è necessario aggiungere ${supplemento_fuoricomune}€ a
        settimana</i>
</div>
</@ui.page>
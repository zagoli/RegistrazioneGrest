<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="container">
    <p>
        <b> Quote per l'iscrizione dei ragazzi al Grest di Balconi
            <@ui.currentYear indent=16/>
        </b>
    </p>
    <@ui.quoteTable prices=[prezzo_4, prezzo_3, prezzo_2, prezzo_1] mensa=[prezzo_4_mensa, prezzo_3_mensa, prezzo_2_mensa, prezzo_1_mensa]/>
    <p><b> Per il fratello/sorella che si iscrive al Grest </b></p>
    <@ui.quoteTable prices=[prezzo_4_fratelli, prezzo_3_fratelli, prezzo_2_fratelli, prezzo_1_fratelli] mensa=[prezzo_4_fratelli_mensa, prezzo_3_fratelli_mensa, prezzo_2_fratelli_mensa, prezzo_1_fratelli_mensa]/>
    <i>se il ragazzo non è residente nel comune di Pescantina, è necessario aggiungere ${supplemento_fuoricomune}€ a
        settimana</i>
    <br/>
    <i>se il ragazzo usufruisce dell'entrata anticipata, è necessario aggiungere ${supplemento_anticipo}€ a
        settimana</i>
</div>
</@ui.page>
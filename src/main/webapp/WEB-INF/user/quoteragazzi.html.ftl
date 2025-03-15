<#include "../struct/header.html.ftl">
<div class="container">
    <p>
        <b> Quote per l'iscrizione dei ragazzi al Grest di Balconi
            <script type="text/javascript">var theDate = new Date();
                document.write(theDate.getFullYear());</script>
        </b>
    </p>
    <table class="table table-bordered">
        <tr>
            <td>per tutto il periodo</td>
            <td>senza mensa ${prezzo_4}€</td>
            <td>con mensa ${prezzo_4_mensa}€</td>
        </tr>
        <tr>
            <td>per tre settimane</td>
            <td>senza mensa ${prezzo_3}€</td>
            <td>con mensa ${prezzo_3_mensa}€</td>
        </tr>
        <tr>
            <td>per due settimane</td>
            <td>senza mensa ${prezzo_2}€</td>
            <td>con mensa ${prezzo_2_mensa}€</td>
        </tr>
        <tr>
            <td>per una settimana</td>
            <td>senza mensa ${prezzo_1}€</td>
            <td>con mensa ${prezzo_1_mensa}€</td>
        </tr>
    </table>
    <p><b> Per il fratello/sorella che si iscrive al Grest </b></p>
    <table class="table table-bordered">
        <tr>
            <td>per tutto il periodo</td>
            <td>senza mensa ${prezzo_4_fratelli}€</td>
            <td>con mensa ${prezzo_4_fratelli_mensa}€</td>
        </tr>
        <tr>
            <td>per tre settimane</td>
            <td>senza mensa ${prezzo_3_fratelli}€</td>
            <td>con mensa ${prezzo_3_fratelli_mensa}€</td>
        </tr>
        <tr>
            <td>per due settimane</td>
            <td>senza mensa ${prezzo_2_fratelli}€</td>
            <td>con mensa ${prezzo_2_fratelli_mensa}€</td>
        </tr>
        <tr>
            <td>per una settimana</td>
            <td>senza mensa ${prezzo_1_fratelli}€</td>
            <td>con mensa ${prezzo_1_fratelli_mensa}€</td>
        </tr>
    </table>
    <i>se il ragazzo non è residente nel comune di Pescantina, è necessario aggiungere ${supplemento_fuoricomune}€ a
        settimana</i>
    <br/>
    <i>se il ragazzo usufruisce dell'entrata anticipata, è necessario aggiungere ${supplemento_anticipo}€ a
        settimana</i>
</div>
<#include "../struct/footer.html.ftl">
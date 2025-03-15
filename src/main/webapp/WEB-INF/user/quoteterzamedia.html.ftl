<#include "../struct/header.html.ftl">
<div class="container">
    <p>
        <b> Quote per l'iscrizione dei ragazzi di terza media al Grest di Balconi
            <script type="text/javascript">var theDate = new Date();
                document.write(theDate.getFullYear());</script>
        </b>
    </p>
    <table class="table table-bordered">
        <tr>
            <td>per tutto il periodo</td>
            <td>${prezzo_4}€</td>
        </tr>
        <tr>
            <td>per tre settimane</td>
            <td>${prezzo_3}€</td>
        </tr>
        <tr>
            <td>per due settimane</td>
            <td>${prezzo_2}€</td>
        </tr>
        <tr>
            <td>per una settimana</td>
            <td>${prezzo_1}€</td>
        </tr>
    </table>
    <i>se il ragazzo non è residente nel comune di Pescantina, è necessario aggiungere ${supplemento_fuoricomune}€ a
        settimana</i>
</div>
<#include "../struct/footer.html.ftl">
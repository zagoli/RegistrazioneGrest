<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contactFormPanel title="Inserisci un contatto telefonico urgenze" titleClass="title pb-1">
<form action="/RegistrazioneGrest/App/InserisciCU" method="POST" data-parsley-validate>
                <@ui.inputField id="nome" label="Nome" required=true placeholder="Nome" length="[2, 40]"/>
                <@ui.inputField id="cognome" label="Cognome" required=true placeholder="Cognome" length="[2, 40]"/>
                <@ui.inputField id="relazione" label="Relazione con l'iscritto (nonno, papà, zio...)" required=true placeholder="Relazione" length="[2, 40]"/>
                <@ui.inputField id="cellulare" label="Telefono cellulare" placeholder="Cellulare" length="[10, 10]"/>
                <@ui.inputField id="fisso" label="Telefono fisso" placeholder="Fisso" length="[10, 10]"/>
                <@ui.submitButton label="Inserisci" class="form-control"/>
            </form>
</@ui.contactFormPanel>
</@ui.page>
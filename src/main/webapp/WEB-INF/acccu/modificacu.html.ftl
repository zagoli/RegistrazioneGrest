<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contactFormPanel title="Modifica contatto telefonico urgenze" titleClass="title pb-1">
<form action="/RegistrazioneGrest/App/ModificaCU" method="POST" data-parsley-validate>
                <input type="hidden" name="id" value="${contatto.id?c}"/>
                <@ui.inputField id="nome" label="Nome" required=true placeholder="Nome" length="[2, 40]" value="${contatto.nome}"/>
                <@ui.inputField id="cognome" label="Cognome" required=true placeholder="Cognome" length="[2, 40]" value="${contatto.cognome}"/>
                <@ui.inputField id="relazione" label="Relazione con l'iscritto (nonno, papà, zio...)" required=true placeholder="Relazione" length="[2, 40]" value="${contatto.relazione}"/>
                <@ui.inputField id="cellulare" label="Telefono cellulare" placeholder="Cellulare" length="[10, 10]" value="${contatto.cellulare!}"/>
                <@ui.inputField id="fisso" label="Telefono fisso" placeholder="Fisso" length="[10, 10]" value="${contatto.fisso!}"/>
                <@ui.submitButton label="Modifica" class="form-control"/>
            </form>
</@ui.contactFormPanel>
</@ui.page>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contactFormPanel title="Modifica un accompagnatore" titleClass="title pb-1">
<form action="/RegistrazioneGrest/App/ModificaAccompagnatore" method="POST" data-parsley-validate>
                <input type="hidden" name="id" value="${accompagnatore.id?c}"/>
                <@ui.inputField id="nome" label="Nome" required=true placeholder="Nome" length="[2, 40]" value="${accompagnatore.nome}"/>
                <@ui.inputField id="text" labelFor="cognome" name="cognome" label="Cognome" required=true placeholder="Cognome" length="[2, 40]" value="${accompagnatore.cognome}"/>
                <@ui.submitButton label="Modifica" class="form-control"/>
            </form>
</@ui.contactFormPanel>
</@ui.page>
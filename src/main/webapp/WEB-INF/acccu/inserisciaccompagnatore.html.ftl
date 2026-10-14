<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contactFormPanel title="Inserisci un accompagnatore" titleClass="title text-center pb-1">
<form action="/RegistrazioneGrest/App/InserisciAccompagnatore" method="POST" data-parsley-validate>
                <@ui.inputField id="nome" label="Nome" required=true placeholder="Nome" length="[2, 40]"/>
                <@ui.inputField id="cognome" label="Cognome" required=true placeholder="Cognome" length="[2, 40]"/>
                <@ui.submitButton label="Inserisci"/>
            </form>
</@ui.contactFormPanel>
</@ui.page>
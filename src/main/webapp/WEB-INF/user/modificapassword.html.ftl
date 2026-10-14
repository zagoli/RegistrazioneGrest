<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contactFormPanel title="Modifica password" titleClass="title pb-1 text-center">
<form action="/RegistrazioneGrest/App/ModificaPassword" method="POST" name="formLogin"
                  data-parsley-validate>
                <@ui.inputField id="pswd" label="Nuova password" type="password" name="password" required=true placeholder="Password" length="[6, 100]"/>
                <@ui.inputField id="confpswd" label="Conferma la password" type="password" name="password" required=true placeholder="Password" attributes={"data-parsley-equalto": "#pswd"}/>
                <@ui.submitButton label="Cambia la password"/>
            </form>
</@ui.contactFormPanel>
</@ui.page>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<#if !DONE>
    <div class="container mt-4 content">
        <div class="shadow pt-2 pl-3 pr-3 bg-white vertical-center">
            <div class="container">
                <div class="title text-center">
                    <h5>Registrazione nuovo segretario</h5>
                </div>
                <form action="/RegistrazioneGrest/App/RegistraUtente" method="POST" data-parsley-validate>
                    <@ui.inputField id="nome" label="Nome" required=true autofocus=true placeholder="Nome" length="[2, 50]"/>
                    <@ui.inputField id="cognome" label="Cognome" required=true placeholder="Cognome" length="[2, 50]"/>
                    <@ui.inputField id="email" label="E-Mail" type="email" name="mail" required=true placeholder="E-Mail"/>
                    <@ui.inputField id="pswd" label="Password" type="password" name="password" required=true placeholder="Password" length="[6, 100]"/>
                    <@ui.inputField id="confpswd" label="Conferma la password" type="password" name="password" required=true placeholder="Password" attributes={"data-parsley-equalto": "#pswd"}/>
                    <@ui.checkboxField id="consentiModifica" label="Consenti la modifica" labelClass="form-check-label"/>
                    <@ui.submitButton label="Registra"/>
                </form>
            </div>
        </div>
    </div>
<#else>
    <div class="container content">
        <div class="row vertical-center">
            <div class="col-md-12">
                <div class="text-center">
                    <div class="title">
                        <h4> Registrazione avvenuta con successo </h4>
                    </div>
                    <div>
                        <a href="/RegistrazioneGrest/App/GestisciSegretari"> Gestisci Segretari </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#if>
</@ui.page>

<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<#if !DONE>
    <div class="container mt-4 content">
        <div class="shadow pt-2 pl-3 pr-3 bg-white vertical-center">
            <div class="container">
                <div class="title text-center">
                    <h5>Registrazione nuovo utente</h5>
                </div>
                <#if INVALIDMAIL??>
                    <@ui.avvisoMailNonValida/>
                </#if>
                <form action="/RegistrazioneGrest/App/RegistraUtente" method="POST" data-parsley-validate>
                    <@ui.inputField id="nome" label="Nome" required=true autofocus=true placeholder="Nome" length="[2, 50]"/>
                    <@ui.inputField id="cognome" label="Cognome" required=true placeholder="Cognome" length="[2, 50]"/>
                    <@ui.inputField id="email" label="E-Mail" type="email" name="mail" required=true placeholder="E-Mail"/>
                    <@ui.inputField id="telefono" label="Numero di telefono" type="" required=true placeholder="Cellulare personale" length="[10, 10]" attributes={"data-parsley-type": "digits"}/>
                    <@ui.inputField id="localita" label="Località" required=true placeholder="Localit&agrave"/>
                    <@ui.inputField id="via" label="Via" required=true placeholder="Via"/>
                    <@ui.inputField id="civico" label="Numero civico" required=true placeholder="Numero Civico"/>
                    <@ui.inputField id="pswd" label="Password" type="password" name="password" required=true placeholder="Password" length="[6, 100]"/>
                    <@ui.inputField id="confpswd" label="Conferma la password" type="password" name="password" required=true placeholder="Password" attributes={"data-parsley-equalto": "#pswd"}/>
                    <@ui.checkboxField id="checkprivacy" label="Acconsento al trattamento dei dati personali in conformità a quanto riportato nell’informativa sulla privacy reperibile al link <a href=\"../privacy.html\">informativa sulla privacy</a>" name=[] required=true labelClass="form-check-label"/>
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
                        <a href="/RegistrazioneGrest/App/Login" class="btn btn-large btn-primary"> Accedi </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#if>
</@ui.page>

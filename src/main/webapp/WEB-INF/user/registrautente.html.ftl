<#import "../struct/iscrizione.html.ftl" as iscrizione>
<#import "../struct/macros.html.ftl" as ui>
<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
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
                    <@iscrizione.datiAccount/>
                    <div class="form-group">
                        <label for="pswd"> Password </label>
                        <input class="form-control" type="password" required id="pswd" name="password"
                               placeholder="Password" data-parsley-length="[6, 100]">
                    </div>
                    <div class="form-group">
                        <label for="confpswd"> Conferma la password </label>
                        <input class="form-control" type="password" required id="confpswd" name="password"
                               placeholder="Password" data-parsley-equalto="#pswd">
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="checkprivacy" required/>
                        <label class="form-check-label" for="checkprivacy"> Acconsento al trattamento dei dati
                            personali in conformità a quanto riportato nell’informativa sulla privacy reperibile al
                            link <a href="../privacy.html">informativa sulla privacy</a></label>
                    </div>
                    <div class="pt-3 form-group">
                        <input class="form-control btn btn-primary" type="submit" value="Registra"/>
                    </div>
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
<#include "../struct/footer.html.ftl">

<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
        <div class="container">
            <div class="row vertical-center">
                <div class="container col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 col-lg-offset-4 col-lg-4">
                    <div class="border border-light rounded pt-2 pl-3 pr-3 pb-2 bg-light">
                        <div class="title pb-1">
                            <h4>Login</h4>
                        </div>
                        <#if ERRATO ??>
                            <p class="text-warning"> Password errata! Riprova </p>
                        </#if>
                        <#if UTENTENONTROVATO ??>
                            <p> Utente non trovato! Controlla che l'email sia corretta </p>
                        </#if>
                        <form action="/RegistrazioneGrest/App/Login" method="POST" name="formLogin">
                            <input class="form-control" type="email" name="mail" placeholder="Mail" required> <br>
                            <input class="form-control" type="password" name="password" placeholder="Password" required> <br>
                            <input class="form-control btn-primary text-white" type="submit" value="Login">
                        </form>
                        <div class="pt-3">
                            <a class="btn btn-block btn-secondary" href="/RegistrazioneGrest/App/RegistraUtente">Registrati</a>
                        </div>
                        <div class="text-center font-italic mt-1">
                            <a href="/RegistrazioneGrest/App/Login?reset">Hai dimenticato la password?</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<#include "../struct/footer.html.ftl">

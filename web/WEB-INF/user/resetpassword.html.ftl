<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container content">
        <div class="row mt-5">
            <div class="container">
                <#if FATTO ??>
                    <div class="text-center">
                        <h1 class="text-uppercase font-weight-bold">ti abbiamo inviato una mail con la tua nuova
                            password.</h1>
                        <p>Se non trovi la mail, prova a controllare nello spam. Potrai modificarla nuovamente dopo aver
                            effettuato l'accesso.</p>
                        <a href="/RegistrazioneGrest/App/Login" class="btn btn-large btn-primary">Accedi</a>
                    </div>
                <#else>
                    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white">
                        <div class="title pb-1">
                            <h4>Ripristina la password</h4>
                        </div>
                        <#if UTENTENONTROVATO ??>
                            <p> Utente non trovato! Controlla che l'email sia corretta </p>
                        </#if>
                        <form action="/RegistrazioneGrest/App/Login?reset" method="POST" data-parsley-validate>
                            <div class="form-group">
                                <label for="pswd"> Inserisci l'e-mail con cui ti sei registrato</label> 
                                <input class="form-control" type="email" required id="mail" name="mailtoreset" placeholder="Inserisci la tua e-mail"> 
                            </div>
                            <div class="form-group">
                                <label for="confpswd"> Conferma la tua e-mail </label> 
                                <input class="form-control" type="email" required id="confmail" name="mailtoreset" placeholder="Conferma a tua e-mail" data-parsley-equalto="#mail"> 
                            </div>
                            <input class="form-control btn btn-primary" type="submit" value="Conferma">
                        </form>
                    </div>
                </#if>
            </div>
        </div>
    </div>
<#include "../struct/footer.html.ftl">

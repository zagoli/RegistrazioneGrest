<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<#if !DONE>
<div class="container mt-4 content">
    <div class="border border-light rounded pt-2 pl-3 pr-3 bg-light vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Registrazione nuovo segretario</h5>
            </div>
            <form action="/RegistrazioneGrest/App/RegistraUtente" method="POST" data-parsley-validate>
                <div class="form-group">
                    <label for="nome"> Nome </label>
                        <input class="form-control" type="text" id ="nome" required name="nome" placeholder="Nome" autofocus data-parsley-length="[2, 50]"> 
                </div>
                <div class="form-group">
                    <label for="cognome"> Cognome </label> 
                        <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome" data-parsley-length="[2, 50]"> 
                </div>
                <div class="form-group">
                    <label for="email"> E-Mail </label> 
                        <input class="form-control" type="email" required id="email" name="mail" placeholder="E-Mail"> 
                </div>
                <div class="form-group">
                    <label for="pswd"> Password </label> 
                        <input class="form-control" type="password" required id="pswd" name="password" placeholder="Password" data-parsley-length="[6, 100]"> 
                </div>
                <div class="form-group">
                    <label for="confpswd"> Conferma la password </label> 
                        <input class="form-control" type="password" required id="confpswd" name="password" placeholder="Password" data-parsley-equalto="#pswd"> 
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="consentiModifica" name="consentiModifica"> 
                    <label class="form-check-label" for="consentiModifica"> Consenti la modifica </label> 
                </div>
                <div class="pt-3 form-group">
                    <input class="form-control" type="submit" value="Registra">
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
                        <a href="/RegistrazioneGrest/App/GestisciSegretari"> Gestisci Segretari </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#if>
<#include "../struct/footer.html.ftl">

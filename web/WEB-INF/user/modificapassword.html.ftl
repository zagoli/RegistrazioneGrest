<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container content">
    <div class="mt-5">
        <div class="border border-light rounded pt-2 pl-3 pr-3 pb-2 bg-light">
            <div class="title pb-1">
                <h4>Modifica password</h4>
            </div>
            <form action="/RegistrazioneGrest/App/ModificaPassword" method="POST" name="formLogin" data-parsley-validate>
                <div class="form-group">
                    <label for="pswd"> Nuova password </label> 
                    <input class="form-control" type="password" required id="pswd" name="password" placeholder="Password" data-parsley-length="[6, 100]"> 
                </div>
                <div class="form-group">
                    <label for="confpswd"> Conferma la password </label> 
                    <input class="form-control" type="password" required id="confpswd" name="password" placeholder="Password" data-parsley-equalto="#pswd"> 
                </div>
                <div class="pt-3 form-group">
                    <input class="form-control" type="submit" value="Cambia la password">
                </div>
            </form>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
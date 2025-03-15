<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container content">
    <div class="mt-5">
        <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white">
            <div class="title text-center pb-1">
                <h4>Inserisci un accompagnatore</h4>
            </div>
            <form action="/RegistrazioneGrest/App/InserisciAccompagnatore" method="POST" data-parsley-validate>
                <div class="form-group">
                    <label for="nome"> Nome </label>
                    <input class="form-control" type="text" required id="nome" name="nome" placeholder="Nome"
                           data-parsley-length="[2, 40]">
                </div>
                <div class="form-group">
                    <label for="cognome"> Cognome </label>
                    <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome"
                           data-parsley-length="[2, 40]">
                </div>
                <div class="pt-3 form-group">
                    <input class="form-control btn btn-primary" type="submit" value="Inserisci">
                </div>
            </form>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
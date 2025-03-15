<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container content">
    <div class="mt-5">
        <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white">
            <div class="title pb-1">
                <h4>Modifica un accompagnatore</h4>
            </div>
            <form action="/RegistrazioneGrest/App/ModificaAccompagnatore" method="POST" data-parsley-validate>
                <input type="hidden" name="id" value="${accompagnatore.id?c}"/>
                <div class="form-group">
                    <label for="nome"> Nome </label>
                    <input class="form-control" type="text" required id="nome" name="nome" placeholder="Nome"
                           data-parsley-length="[2, 40]" value="${accompagnatore.nome}">
                </div>
                <div class="form-group">
                    <label for="cognome"> Cognome </label>
                    <input class="form-control" type="text" required id="text" name="cognome" placeholder="Cognome"
                           data-parsley-length="[2, 40]" value="${accompagnatore.cognome}">
                </div>
                <div class="pt-3 form-group">
                    <input class="form-control" type="submit" value="Modifica">
                </div>
            </form>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
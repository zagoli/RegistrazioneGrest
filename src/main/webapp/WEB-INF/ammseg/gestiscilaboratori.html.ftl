<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container-fluid mt-5 content">
    <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">

        <#-- Aggiunta laboratorio -->
        <h5>Aggiungi un laboratorio</h5>
        <form action="/RegistrazioneGrest/App/GestisciLaboratori" class="form-inline">
            <input type="hidden" name="add"/>
            <div class="form-group m-2">
                <label for="nome" class="sr-only">Nome</label>
                <input type="text" placeholder="nome" name="nome" id="nome" class="form-control" required/>
            </div>
            <div class="form-group m-2">
                <label for="riservato" class="sr-only">Riservato agli animatori</label>
                <input type="checkbox" name="riservato" id="riservato" class="form-control ml-2" required/>
            </div>
            <input type="submit" class="btn btn-primary m-2" value="Aggiungi"/>
        </form>

        <#-- Laboratori -->
        <#if laboratori??>
            <h5 class="text-center pb-1"> Laboratori </h5>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col">Nome</th>
                        <th scope="col">Riservato agli animatori</th>
                        <th scope="col" style="width: 2%;"></th>
                    </tr>
                </thead>
                <tbody>
                <#list laboratori as lab>
                    <tr>
                        <td>${lab.nome}</td>
                        <td><#if lab.riservato == 1>sì<#else>no</#if></td>
                        <td>
                            <a href="/RegistrazioneGrest/App/GestisciLaboratori?delete&idLaboratorio=${lab.id?c}">
                                <img src="../img/octicons/trashcan.svg">
                            </a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>

    </div>
</div>
<#include "../struct/footer.html.ftl">
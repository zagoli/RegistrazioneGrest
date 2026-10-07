<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container-fluid mt-5 content">
    <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">

        <#-- Aggiunta laboratorio -->
        <h5>Aggiungi un laboratorio</h5>

        <form action="/RegistrazioneGrest/App/GestisciLaboratori">
            <input type="hidden" name="add"/>
            <div class="form-group">
                <label for="nome">Nome laboratorio</label>
                <input type="text" class="form-control" id="nome" name="nome" placeholder="Nome laboratorio"/>
            </div>
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="riservato" name="riservato">
                <label class="form-check-label" for="riservato">Riservato agli animatori</label>
            </div>
            <button type="submit" class="btn btn-primary mt-2">Aggiungi</button>
        </form>

        <#-- Laboratori -->
        <#if laboratori??>
            <h5 class="mt-4 mb-2"> Laboratori </h5>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col">Nome</th>
                        <th scope="col">Riservato agli animatori</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                <#list laboratori as labConNumero>
                    <tr>
                        <td>${labConNumero.laboratorio.descrizione}</td>
                        <td><#if labConNumero.laboratorio.riservato>sì<#else>no</#if></td>
                        <td>
                            <#if (labConNumero.numeroIscritti > 0)>
                                <p>Questo laboratorio ha ${labConNumero.numeroIscritti} iscritti</p>
                            <#else>
                                <a href="/RegistrazioneGrest/App/GestisciLaboratori?delete&idLaboratorio=${labConNumero.laboratorio.id?c}">
                                    <button type="button" class="btn btn-danger">Elimina</button>
                                </a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>

    </div>
</div>
<#include "../struct/footer.html.ftl">
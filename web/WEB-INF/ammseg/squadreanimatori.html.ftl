<#include "../struct/header.html.ftl">      
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
            <#if animatori??>
                <form action="/RegistrazioneGrest/App/Squadre?target=an" method="POST">
                    <!-- l'input nascosto indica che abbiamo usato la pagina per impostare le squadre-->
                    <input type="hidden" name="submitted"/>
                    <input type="submit" class="btn btn-primary" value="Salva"/>
                    <table class="table table-striped" id="t_squadreani">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">Cognome</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Squadra</th>
                                <th scope="col">Responsabile</th>
                            </tr>
                        </thead>
                        <tbody class="list">
                            <#list animatori as ani>
                                <tr>
                                    <td>${ani.cognomeapitalize}</td>
                                    <td>${ani.nomeapitalize}</td>
                                    <td>
                                        <label class="sr-only" for="${ani.id?c}squadra">Squadra</label>
                                        <select class="custom-select mr-sm-2" id="${ani.id?c}squadra"
                                                name="${ani.id?c}">
                                            <option <#if ani.squadra.id==0>selected</#if>></option>
                                            <#list squadre as sq>
                                                <option value="${sq.id?c}"
                                                        <#if ani.squadra.id! == sq.id>selected</#if>>${sq.nome}</option>
                                            </#list>
                                        </select>
                                    </td>
                                    <td align="center">
                                        <div class="checkbox">
                                            <label class="sr-only" for="${ani.id?c}responsabileSquadra">Responsabile
                                                squadra</label>
                                            <input id="${ani.id?c}responsabileSquadra" name="${ani.id?c}"
                                                   type="checkbox"
                                                   data-toggle="toggle" data-off="No" data-on="Sì"
                                                   <#if ani.responsabileSquadra>checked</#if>>
                                        </div>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </form>
            </#if>
        </div>
    </div>
<script>
    $(document).ready(function () {
        $('#t_squadreani').DataTable({
            paging: false,
            columnDefs: [
                {orderable: false, targets: [2, 3]},
                {searchable: false, targets: 3},
                {width: "10%", targets: 3 ? c}
            ],
            info: false
        });
    });
</script>
<!-- questo script permette di salvare solo le modifiche effettuate, invece che di salvare tutti gli animatori compresi quelli che sono rimasti uguali-->
<script src="../risorse/js/salvasolomodificatoanimatori.js"></script>
<#include "../struct/footer.html.ftl">
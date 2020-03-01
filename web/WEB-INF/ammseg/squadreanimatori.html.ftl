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
                                    <td>${ani.cognome?capitalize}</td>
                                    <td>${ani.nome?capitalize}</td>
                                    <td>
                                        <label class="sr-only" for="${ani.id}squadra">Squadra</label>
                                        <select class="custom-select mr-sm-2" id="${ani.id}squadra" name="${ani.id}">
                                            <option <#if !ani.squadra??>selected</#if>></option>
                                            <option value="Arancio grandi" <#if ani.squadra?? && ani.squadra="Arancio grandi">selected</#if>>Arancio grandi</option>
                                            <option value="Arancio piccoli" <#if ani.squadra?? && ani.squadra="Arancio piccoli">selected</#if>>Arancio piccoli</option>
                                            <option value="Azzurri grandi" <#if ani.squadra?? && ani.squadra="Azzurri grandi">selected</#if>>Azzurri grandi</option>
                                            <option value="Azzurri piccoli" <#if ani.squadra?? && ani.squadra="Azzurri piccoli">selected</#if>>Azzurri piccoli</option>
                                            <option value="Blu grandi" <#if ani.squadra?? && ani.squadra="Blu grandi">selected</#if>>Blu grandi</option>
                                            <option value="Blu piccoli" <#if ani.squadra?? && ani.squadra="Blu piccoli">selected</#if>>Blu piccoli</option>
                                            <option value="Gialli grandi" <#if ani.squadra?? && ani.squadra="Gialli grandi">selected</#if>>Gialli grandi</option>
                                            <option value="Gialli piccoli" <#if ani.squadra?? && ani.squadra="Gialli piccoli">selected</#if>>Gialli piccoli</option>
                                            <option value="Rossi grandi" <#if ani.squadra?? && ani.squadra="Rossi grandi">selected</#if>>Rossi grandi</option>
                                            <option value="Rossi piccoli" <#if ani.squadra?? && ani.squadra="Rossi piccoli">selected</#if>>Rossi piccoli</option>
                                            <option value="Verdi grandi" <#if ani.squadra?? && ani.squadra="Verdi grandi">selected</#if>>Verdi grandi</option>
                                            <option value="Verdi piccoli" <#if ani.squadra?? && ani.squadra="Verdi piccoli">selected</#if>>Verdi piccoli</option>
                                        </select>
                                    </td>
                                    <td align="center">
                                        <div class="checkbox">
                                            <label class="sr-only" for="${ani.id}responsabileSquadra">Responsabile squadra</label>
                                            <input id="${ani.id}responsabileSquadra" name="${ani.id}" type="checkbox" data-toggle="toggle" data-off="No" data-on="S&igrave;" <#if ani.responsabileSquadra>checked</#if>>
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
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script>
        $(document).ready( function () {
            $('#t_squadreani').DataTable({
                paging: false,
                columnDefs: [
                    {orderable:false,targets:[2,3]},
                    {searchable:false,targets:3},
                    {width:"10%",targets:3}
                ],
                info: false
            });
        } );
    </script>
    <!-- questo script permette di salvare solo le modifiche effettuate, invece che di salvare tutti gli animatori compresi quelli che sono rimasti uguali-->
    <script src="../risorse/js/salvasolomodificatoanimatori.js"></script>
<#include "../struct/footer.html.ftl">
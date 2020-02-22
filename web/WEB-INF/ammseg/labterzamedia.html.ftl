<#include "../struct/header.html.ftl">      
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container border border-secondary rounded pt-2 pl-3 pr-3 pb-2 bg-light">
            <#if terzamedia??>
                <form action="/RegistrazioneGrest/App/Laboratori?target=ter" method="POST">
                    <!-- l'input nascosto indica che abbiamo usato la pagina per impostare i laboratori-->
                    <input type="hidden" name="submitted"/>
                    <input type="submit" class="btn btn-primary" value="Salva"/>
                    <table class="table table-striped" id="t_labter">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">Cognome</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Laboratorio</th>
                            </tr>
                        </thead>
                        <tbody class="list">
                            <#list terzamedia as ter>
                                <tr>
                                    <td>${ter.cognome}</td>
                                    <td>${ter.nome}</td>
                                    <td>
                                        <label class="sr-only" for="laboratorio">Laboratorio</label>
                                        <select class="custom-select mr-sm-2" id="laboratorio" name="laboratorio">
                                            <#list laboratori as lab>
                                                <option value="${ter.id},${lab.id}" <#if ter.laboratorio.id==lab.id>selected</#if>>${lab.descrizione}</option>
                                            </#list>
                                        </select>
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
            $('#t_labter').DataTable({
                paging: false,
                columnDefs: [{targets:[2],orderable:false}],
                info: false
            });
        } );
    </script>
    <!-- questo script permette di salvare solo le modifiche effettuate, invece che di salvare tutto quello che è rimasto uguale-->
    <script src="../risorse/js/salvasolomodificatoragter.js"></script>
<#include "../struct/footer.html.ftl">
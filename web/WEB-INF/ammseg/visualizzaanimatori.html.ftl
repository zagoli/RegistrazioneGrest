<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="pt-2 pl-3 pr-3 pb-2">
            <table id="t_anim" class="table table-hover table-responsive nowrap">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col" style="width: 6%">Azioni</th>
                        <th scope="col">Cognome</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Nascita</th>
                        <th scope="col">Fiscale</th>
                        <th scope="col">Indirizzo</th>
                        <th scope="col">Parrocchia</th>
                        <th scope="col">Cellulare</th>
                        <th scope="col">Email</th>
                        <th scope="col">Laboratorio</th>
                        <th scope="col">Squadra</th>
                        <th scope="col">F. et&agrave;</th>
                    </tr>
                </thead>
                <tbody class="list">
                    <#list animatori as ani>
                        <tr>
                            <td>
                                <a href="javascript:;" onClick="window.open('/RegistrazioneGrest/App/InfoDettaglio?target=infoani&id=${ani.id}', 'Dettagli animatore', 'width=600, height=700, status, scrollbars=1, location');"><img src="../risorse/img/octicons/search.svg"></a>
                                <a href="/RegistrazioneGrest/App/ModificaAnimatore?id=${ani.id}"><img src="../risorse/img/octicons/pencil.svg"></a>
                                <a href="/RegistrazioneGrest/App/EliminaAnimatore?id=${ani.id}"><img src="../risorse/img/octicons/trashcan.svg"></a>
                            </td>
                            <td>${ani.cognome?capitalize}</td>
                            <td>${ani.nome?capitalize}</td>
                            <td>${ani.dataNascita}</td>
                            <td>${ani.codiceFiscale}</td>
                            <td>${ani.registrato.via?capitalize+" "+ani.registrato.civico+" "+ani.registrato.localita?capitalize}</td>
                            <td>${ani.parrocchia.nome+" - "+ani.parrocchia.luogo}</td>
                            <td>${ani.cellulare}</td>
                            <td>${ani.mail}</td>
                            <td <#if ani.responsabileLaboratorio>class="mark"</#if>>${ani.laboratorio.descrizione}</td>
                            <td <#if ani.responsabileSquadra>class="mark"</#if>><#if ani.squadra??>${ani.squadra}<#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                            <td>
                                <#if ani.fasciaEtaRagazzi = "P14"> 
                                Piccoli
                                <#elseif ani.fasciaEtaRagazzi = "G52"> 
                                Grandi
                                <#elseif ani.fasciaEtaRagazzi = "AA3"> 
                                3 Media
                                </#if>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/plug-ins/1.10.19/dataRender/ellipsis.js"></script>
    <script>
        $(document).ready( function () {
            $('#t_anim').DataTable({
                paging: false,
                columnDefs: [
                {
                    targets: [2,3,4,6,7,10],
                    render: $.fn.dataTable.render.ellipsis(15, true)
                },
                {
                    targets: [0],
                    searchable: false,
                    orderable: false
                }
                ],
                info: false
            });
        } );
    </script>
<#include "../struct/footer.html.ftl">
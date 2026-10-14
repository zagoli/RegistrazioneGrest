<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="pt-2 pl-3 pr-3 pb-2" responsive=true>
            <table id="t_anim" class="table table-hover nowrap">
            <@ui.tableHead columns=[
                {"label": "Azioni", "attributes": {"scope": "col", "style": "width: 6%;"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Nascita", "attributes": {"scope": "col"}},
                {"label": "Fiscale", "attributes": {"scope": "col"}},
                {"label": "Indirizzo", "attributes": {"scope": "col"}},
                {"label": "Parrocchia", "attributes": {"scope": "col"}},
                {"label": "Cellulare", "attributes": {"scope": "col"}},
                {"label": "Email", "attributes": {"scope": "col"}},
                {"label": "Laboratorio", "attributes": {"scope": "col"}},
                {"label": "Squadra", "attributes": {"scope": "col"}},
                {"label": "F. età", "attributes": {"scope": "col"}}
            ] class="thead-dark"/>
            <tbody class="list">
            <#list animatori as ani>
                <tr>
                    <td>
                        <@ui.iscrittoActions id=ani.id infoTarget="infoani" popupTitle="Dettagli animatore" modifyAction="ModificaAnimatore" deleteAction="EliminaAnimatore" href="javascript:" detailAlt="dettagli"/>
                    </td>
                    <td>${ani.cognome?capitalize}</td>
                    <td>${ani.nome?capitalize}</td>
                    <td data-sort="${ani.dataNascita?string.iso}">${ani.dataNascita?string["dd/MM/yyyy"]}</td>
                    <td>${ani.codiceFiscale}</td>
                    <td>${ui.indirizzo(ani.registrato, true)}</td>
                    <td title="${ani.parrocchia.nome}">${ani.parrocchia.luogo}</td>
                    <td>${ani.cellulare}</td>
                    <td>${ani.mail}</td>
                    <td <#if ani.responsabileLaboratorio>class="mark"</#if>>${ani.laboratorio.descrizione}</td>
                    <#-- con freemarker è molto brutto: devo aggiungere la parola mark alla lista delle classi-->
                    <td class="badge-squadra-support <#if ani.responsabileSquadra>mark</#if>">
                        <@ui.squadraBadge squadra=ani.squadra style="list"/>
                    </td>
                    <td>
                        <@ui.fasciaEtaDescription fascia=ani.fasciaEtaRagazzi/>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
</@ui.contentPanel>
<script type="text/javascript" src="https://cdn.datatables.net/plug-ins/1.10.19/dataRender/ellipsis.js"></script>
<@ui.iscrittiDataTable id="t_anim" ellipsisTargets="[2, 3, 4, 6, 7]" searchTargets="[0]"/>
</@ui.page>
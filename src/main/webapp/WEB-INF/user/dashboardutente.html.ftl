<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<div class="container mt-4 content">
    <#if NOCU??>
        <div class="border border-danger pt-2 pl-3 pr-3 mb-3">
            <p class="text-danger text-center">Non sono ancora stati inseriti i contatti telefonici per
                le urgenze! Si prega di inserire <i>almeno</i> un contatto tramite la voce nel menu "Accompagnatori e
                Contatti telefonici Urgenze"!</p>
        </div>
    </#if>
    <div class="shadow p-2">
        <h4 class="text-center">Menu iscrizioni</h4>
        <@ui.dashboardLink href="/RegistrazioneGrest/App/RegistraRagazzo" label="Iscrivi un ragazzo</a> </div> <div class=\"col-sm\"> <a href=\"/RegistrazioneGrest/App/RegistraAnimatore\" class=\"btn btn-primary btn-block\">Iscrivi un animatore</a> </div> <div class=\"col-sm\"> <a href=\"/RegistrazioneGrest/App/RegistraTerzamedia\" class=\"btn btn-primary btn-block\">Iscrivi un ragazzo di terza media" rowClass="row" columnClass="col-sm" buttonClass="btn btn-primary btn-block"/>
    </div>
    <#if ragazzi??>
        <div class="shadow bg-white pt-2 pl-3 pr-3 pb-2">
            <p class="text-center font-weight-bold">Ragazzi</p>
            <div class="table-responsive">
                <table class="table">
                    <@ui.tableHead columns=[
                {"label": "", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Stampa scheda", "attributes": {"scope": "col", "style": "width: 15%;"}},
                {"label": "Pagato", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Modifica", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Elimina", "attributes": {"scope": "col", "style": "width: 2%;"}}
            ]/>
                    <tbody>
                    <#list ragazzi as rag, pagato>
                        <@ui.dashboardIscrittoRow person=rag sheetTarget="schedarag" action="Ragazzo" editable=(!pagato) paid=pagato paidAlts=true/>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </#if>
    <#if animatori??>
        <div class="shadow bg-white pt-2 pl-3 pr-3 pb-2 mt-4">
            <p class="text-center font-weight-bold">Animatori</p>
            <div class="table-responsive">
                <table class="table">
                    <@ui.tableHead columns=[
                {"label": "", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Stampa scheda", "attributes": {"scope": "col", "style": "width: 15%;"}},
                {"label": "Modifica", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Elimina", "attributes": {"scope": "col", "style": "width: 2%;"}}
            ]/>
                    <tbody>
                    <#list animatori as ani>
                        <@ui.dashboardIscrittoRow person=ani sheetTarget="schedaani" action="Animatore" editable=(ISCRAN) printAlt="stampa"/>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </#if>
    <#if terzamedia??>
        <div class="shadow bg-white pt-2 pl-3 pr-3 pb-2 mt-4">
            <p class="text-center font-weight-bold">Ragazzi di terza media</p>
            <div class="table-responsive">
                <table class="table">
                    <@ui.tableHead columns=[
                {"label": "", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Stampa scheda", "attributes": {"scope": "col", "style": "width: 15%;"}},
                {"label": "Pagato", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Modifica", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Elimina", "attributes": {"scope": "col", "style": "width: 2%;"}}
            ]/>
                    <tbody>
                    <#list terzamedia as ter, pagato>
                        <@ui.dashboardIscrittoRow person=ter sheetTarget="schedater" action="Terzamedia" editable=(!pagato) paid=pagato/>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </#if>
</div>
</@ui.page>
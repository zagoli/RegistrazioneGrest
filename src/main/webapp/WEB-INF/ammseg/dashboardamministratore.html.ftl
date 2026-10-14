<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
    <div class="container-fluid mt-5 content">
    <div class="container">
        <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white">
            <div class="text-center pb-2">
                <h3> Azioni segretario </h3>
            </div>
            <#include "dashboardsegretariointernal.html.ftl">
        </div>
        <div class="shadow pt-2 pl-3 pr-3 pb-2 mt-3 bg-white">
            <div class="text-center">
                <h3> Azioni amministratore </h3>
            </div>
            <#--attivazione iscrizioni varie-->
            <div class="mt-4 row">
                <@ui.registrationSwitch id="ISCRRAG" label="Attiva iscrizioni ragazzi" checked=ISCRRAG/>
                <@ui.registrationSwitch id="ISCRAN" label="Attiva iscrizioni animatori" checked=ISCRAN/>
                <@ui.registrationSwitch id="ISCRTER" label="Attiva iscrizioni terza media" checked=ISCRTER/>
            </div>
            <#--fine attivazione iscrizioni-->
            <hr/>
            <@ui.dashboardLink href="/RegistrazioneGrest/App/GestisciLaboratori" label="GESTISCI LABORATORI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-success btn-block btn-lg"/>
            <@ui.dashboardLink href="/RegistrazioneGrest/App/GestisciSegretari" label="GESTISCI SEGRETARI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-success btn-block btn-lg"/>
            <@ui.dashboardLink href="/RegistrazioneGrest/App/Statistiche" label="STATISTICHE" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-success btn-block btn-lg"/>
            <div class="row mt-2">
                <div class="col">
                    <button type="button" class="btn btn-success btn-block btn-lg" data-toggle="modal"
                            data-target="#esportazioneExcelModal">
                        ESPORTAZIONE EXCEL
                    </button>
                </div>
            </div>
        </div>
    </div>
    </div>

    <div class="modal fade" id="esportazioneExcelModal" tabindex="-1" role="dialog"
         aria-labelledby="esportazioneExcelModalTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="esportazioneExcelModalTitle">Esportazione Excel</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Sei sicuro di voler esportare i dati dei ragazzi, ragazzi di terza media e animatori in excel?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                    <a href="/RegistrazioneGrest/App/EsportazioneMassiva" class="btn btn-success">Prosegui</a>
                </div>
            </div>
        </div>
    </div>

    <script src="../js/ajaxiscrizioni.js"></script>
</@ui.page>
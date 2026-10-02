<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
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
                <div class="col">
                    <div class="form-check form-check-inline">
                        <div class="custom-control custom-switch">
                            <input class="custom-control-input" id="ISCRRAG" type="checkbox"
                                   <#if ISCRRAG>checked</#if>>
                            <label class="custom-control-label" for="ISCRRAG">Attiva iscrizioni ragazzi</label>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="form-check form-check-inline">
                        <div class="custom-control custom-switch">
                            <input class="custom-control-input" id="ISCRAN" type="checkbox" <#if ISCRAN>checked</#if>>
                            <label class="custom-control-label" for="ISCRAN">Attiva iscrizioni animatori</label>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="form-check form-check-inline">
                        <div class="custom-control custom-switch">
                            <input class="custom-control-input" id="ISCRTER" type="checkbox"
                                   <#if ISCRTER>checked</#if>>
                            <label class="custom-control-label" for="ISCRTER">Attiva iscrizioni terza media</label>
                        </div>
                    </div>
                </div>
            </div>
            <#--fine attivazione iscrizioni-->
            <hr/>
            <div class="row mt-2">
                <div class="col">
                    <a href="/RegistrazioneGrest/App/GestisciSegretari" class="btn btn-success btn-block btn-lg">
                        GESTISCI SEGRETARI </a>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col">
                    <a href="/RegistrazioneGrest/App/Statistiche" class="btn btn-success btn-block btn-lg">
                        STATISTICHE </a>
                </div>
            </div>
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
<#include "../struct/footer.html.ftl">
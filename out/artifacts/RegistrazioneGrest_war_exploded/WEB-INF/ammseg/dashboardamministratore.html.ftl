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
                <!--attivazione iscrizioni varie-->
                <div class="mt-4 row">
                    <div class="col">
                        <div class="form-check form-check-inline">
                            <div class="checkbox">
                                  <input class="form-check-input" id="ISCRRAG" type="checkbox" data-toggle="toggle" data-offstyle="danger" <#if ISCRRAG??>checked</#if>>
                                  <label class="form-check-label" id="labelISCRRAG">Attiva iscrizioni ragazzi</label>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-check form-check-inline">
                            <div class="checkbox">
                                  <input class="form-check-input" id="ISCRAN" type="checkbox" data-toggle="toggle" data-offstyle="danger" <#if ISCRAN??>checked</#if>>
                                  <label class="form-check-label" id="labelISCRAN">Attiva iscrizioni animatori</label>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-check form-check-inline">
                            <div class="checkbox">
                                  <input class="form-check-input" id="ISCRTER" type="checkbox" data-toggle="toggle" data-offstyle="danger" <#if ISCRTER??>checked</#if>>
                                  <label class="form-check-label" id="labelISCRTER">Attiva iscrizioni terza media</label>
                            </div>
                        </div>
                    </div>
                </div>
                <!--fine-->
                <hr/>
                <div class="row mt-2">
                    <div class="col">
                        <a href="/RegistrazioneGrest/App/GestisciSegretari" class="btn btn-info btn-block btn-lg"> GESTISCI SEGRETARI </a>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <a href="/RegistrazioneGrest/App/Statistiche" class="btn btn-info btn-block btn-lg"> STATISTICHE </a>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <a href="/RegistrazioneGrest/App/Stampa?target=elesintrag" class="btn btn-info btn-block btn-lg"> STAMPA ELENCO SINTESI RAGAZZI </a>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <a href="/RegistrazioneGrest/App/Stampa?target=elesintan" class="btn btn-info btn-block btn-lg"> STAMPA ELENCO SINTESI ANIMATORI </a>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <a href="/RegistrazioneGrest/App/Stampa?target=elesintter" class="btn btn-info btn-block btn-lg"> STAMPA ELENCO SINTESI TERZA MEDIA </a>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <a href="/RegistrazioneGrest/App/Stampa?target=collaborazione" class="btn btn-info btn-block btn-lg"> STAMPA ELENCO COLLABORAZIONE </a>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <a href="/RegistrazioneGrest/App/Stampa?target=anticipo" class="btn btn-info btn-block btn-lg"> STAMPA ELENCO ENTRATA ANTICIPATA </a>
                    </div>
                </div>
                
                <hr/>
                <div class="text-center">
                    <h4> Stampa presenze animatori settimanali </h4>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <div class="text-center">
                            <div class="btn-group btn-group-lg" role="group">
                                <#list settimane as set>
                                <a href="/RegistrazioneGrest/App/Stampa?target=presgiornani&idSet=${set.idSettimana}" class="btn btn-info"> ${set.idSettimana} SETTIMANA </a>
                                </#list>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="text-center">
                    <h4> Stampa presenze mensa settimanali </h4>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <div class="text-center">
                            <div class="btn-group btn-group-lg" role="group">
                                <#list settimane as set>
                                <a href="/RegistrazioneGrest/App/Stampa?target=mensa&idSet=${set.idSettimana}" class="btn btn-info"> ${set.idSettimana} SETTIMANA </a>
                                </#list>
                            </div>
                        </div>
                    </div>
                </div>
                
                <hr/>
                <div class="text-center">
                    <h4> Stampa presenze squadre settimanali </h4>
                </div>
                <div id="accordionPressetsqu">
                    <#list settimane as set>
                        <div class="card">
                            <div class="card-header" id="headingpressetsqu${set.idSettimana}">
                                <h5 class="mb-0 text-center"> 
                                    <button class="btn btn-link" data-toggle="collapse" data-target="#pressetsqu${set.idSettimana}" aria-expanded="false" aria-controls="pressetsqu${set.idSettimana}">
                                        Settimana dal ${set.daQuando} al ${set.aQuando}
                                    </button>
                                </h5>
                            </div>
                            <div id="pressetsqu${set.idSettimana}" class="collapse" aria-labelledby="headingpressetsqu${set.idSettimana}" data-parent="#accordionPressetsqu">
                                <div class="card-body">
                                    <div class="row mt-2">
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=arag" class="btn arancio-grandi btn-block btn-lg text-white"> ARANCIO GRANDI </a>
                                        </div>
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=arap" class="btn arancio-piccoli btn-block btn-lg text-white"> ARANCIO PICCOLI </a>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=azzg" class="btn azzurri-grandi btn-block btn-lg text-white"> AZZURRI GRANDI </a>
                                        </div>
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=azzp" class="btn azzurri-piccoli btn-block btn-lg text-white"> AZZURRI PICCOLI </a>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=blug" class="btn blu-grandi btn-block btn-lg text-white"> BLU GRANDI </a>
                                        </div>
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=blup" class="btn blu-piccoli btn-block btn-lg text-white"> BLU PICCOLI </a>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=giag" class="btn gialli-grandi btn-block btn-lg text-secondary"> GIALLI GRANDI </a>
                                        </div>
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=giap" class="btn gialli-piccoli btn-block btn-lg text-secondary"> GIALLI PICCOLI </a>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=rossg" class="btn rossi-grandi btn-block btn-lg text-white"> ROSSI GRANDI </a>
                                        </div>
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=rossp" class="btn rossi-piccoli btn-block btn-lg text-white"> ROSSI PICCOLI </a>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=verg" class="btn verdi-grandi btn-block btn-lg text-white"> VERDI GRANDI </a>
                                        </div>
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=verp" class="btn verdi-piccoli btn-block btn-lg text-white"> VERDI PICCOLI </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
                <hr/>
                <div class="text-center">
                    <h4> Stampa schede laboratorio settimanali </h4>
                </div>
                <div id="accordionPressetlab">
                    <#list settimane as set>
                        <div class="card">
                            <div class="card-header" id="headingpressetlab${set.idSettimana}">
                                <h5 class="mb-0 text-center"> 
                                    <button class="btn btn-link" data-toggle="collapse" data-target="#pressetlab${set.idSettimana}" aria-expanded="false" aria-controls="spressetlab${set.idSettimana}">
                                        Settimana dal ${set.daQuando} al ${set.aQuando}
                                    </button>
                                </h5>
                            </div>
                            <div id="pressetlab${set.idSettimana}" class="collapse" aria-labelledby="headingpressetlab${set.idSettimana}" data-parent="#accordionPressetlab">
                                <div class="card-body">
                                    <#list laboratori as lab>
                                    <div class="row mt-2">
                                        <div class="col">
                                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetlab&idset=${set.idSettimana}&lab=${lab.id}" class="btn btn-block btn-lg btn-primary"> ${lab.descrizione} </a>
                                        </div>
                                    </div>
                                    </#list>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
    <script src="../risorse/js/ajaxiscrizioni.min.js"></script>
<#include "../struct/footer.html.ftl">
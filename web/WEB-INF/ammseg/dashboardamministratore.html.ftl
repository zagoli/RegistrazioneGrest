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
                        <div class="custom-control custom-switch">
                            <input class="custom-control-input" id="ISCRRAG" type="checkbox"
                                   <#if ISCRRAG??>checked</#if>>
                            <label class="custom-control-label" for="ISCRRAG">Attiva iscrizioni ragazzi</label>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="form-check form-check-inline">
                        <div class="custom-control custom-switch">
                            <input class="custom-control-input" id="ISCRAN" type="checkbox" <#if ISCRAN??>checked</#if>>
                            <label class="custom-control-label" for="ISCRAN">Attiva iscrizioni animatori</label>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="form-check form-check-inline">
                        <div class="custom-control custom-switch">
                            <input class="custom-control-input" id="ISCRTER" type="checkbox"
                                   <#if ISCRTER??>checked</#if>>
                            <label class="custom-control-label" for="ISCRTER">Attiva iscrizioni terza media</label>
                        </div>
                    </div>
                </div>
            </div>
            <!--fine attivazione iscrizioni-->
            <hr/>
            <div class="row mt-2">
                <div class="col">
                    <a href="/RegistrazioneGrest/App/GestisciSegretari" class="btn btn-info btn-block btn-lg">
                        GESTISCI SEGRETARI </a>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col">
                    <a href="/RegistrazioneGrest/App/Statistiche" class="btn btn-info btn-block btn-lg">
                        STATISTICHE </a>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col">
                    <a href="/RegistrazioneGrest/App/Stampa?target=elesintrag" class="btn btn-info btn-block btn-lg">
                        STAMPA ELENCO SINTESI RAGAZZI </a>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col">
                    <a href="/RegistrazioneGrest/App/Stampa?target=elesintan" class="btn btn-info btn-block btn-lg">
                        STAMPA ELENCO SINTESI ANIMATORI </a>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col">
                    <a href="/RegistrazioneGrest/App/Stampa?target=elesintter" class="btn btn-info btn-block btn-lg">
                        STAMPA ELENCO SINTESI TERZA MEDIA </a>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col">
                    <a href="/RegistrazioneGrest/App/Stampa?target=collaborazione"
                       class="btn btn-info btn-block btn-lg"> STAMPA ELENCO COLLABORAZIONE </a>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col">
                    <a href="/RegistrazioneGrest/App/Stampa?target=anticipo" class="btn btn-info btn-block btn-lg">
                        STAMPA ELENCO ENTRATA ANTICIPATA </a>
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
                                <a href="/RegistrazioneGrest/App/Stampa?target=presgiornani&idSet=${set.idSettimana?c}"
                                   class="btn btn-info"> ${set.idSettimana?c} SETTIMANA </a>
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
                                <a href="/RegistrazioneGrest/App/Stampa?target=mensa&idSet=${set.idSettimana?c}"
                                   class="btn btn-info"> ${set.idSettimana?c} SETTIMANA </a>
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
                    <div class="card-header" id="headingpressetsqu${set.idSettimana?c}">
                        <h5 class="mb-0 text-center">
                            <button class="btn btn-link" data-toggle="collapse"
                                    data-target="#pressetsqu${set.idSettimana?c}" aria-expanded="false"
                                    aria-controls="pressetsqu${set.idSettimana?c}">
                                Settimana dal ${set.daQuando} al ${set.aQuando}
                            </button>
                        </h5>
                    </div>
                    <div id="pressetsqu${set.idSettimana?c}" class="collapse"
                         aria-labelledby="headingpressetsqu${set.idSettimana?c}"
                         data-parent="#accordionPressetsqu">
                        <div class="card-body">
                            <#--Devo dividere le squadre in due colonne: per prima cosa creo una variabile contatore-->
                            <#assign cont = 0>
                            <#list squadre as sq>
                            <#--Salvo la colonna invece di ripeterla due volte-->
                                <#assign colonna>
                                    <div class="col">
                                        <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana}&squadra=${sq.id?c}"
                                           class="btn btn-block btn-lg text-black"
                                           style="background-color: ${sq.colore} !important;">
                                            <b>${sq.nome}</b>
                                        </a>
                                    </div>
                                </#assign>
                            <#--Se il contatore è divisibile per due, allora apro la riga-->
                                <#if cont%2 == 0>
                                    <div class="row mt-2">
                                    ${colonna}
                                <#else>
                                <#--Altrimenti so che devo scrivere una seconda colonna e chiudere la riga-->
                                    ${colonna}
                                    </div>
                                </#if>
                            <#--Poi incremento il contatore-->
                                <#assign cont++>
                            </#list>
                            <#--Se ho finito con un numero dispari, devo comunque chiudere la riga-->
                            <#if cont%2 != 0>
                        </div>
                        </#if>
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
                    <div class="card-header" id="headingpressetlab${set.idSettimana?c}">
                        <h5 class="mb-0 text-center">
                            <button class="btn btn-link" data-toggle="collapse"
                                    data-target="#pressetlab${set.idSettimana?c}" aria-expanded="false"
                                    aria-controls="spressetlab${set.idSettimana?c}">
                                Settimana dal ${set.daQuando} al ${set.aQuando}
                            </button>
                        </h5>
                    </div>
                    <div id="pressetlab${set.idSettimana?c}" class="collapse"
                         aria-labelledby="headingpressetlab${set.idSettimana?c}" data-parent="#accordionPressetlab">
                        <div class="card-body">
                            <#list laboratori as lab>
                                <div class="row mt-2">
                                    <div class="col">
                                        <a href="/RegistrazioneGrest/App/Stampa?target=pressetlab&idset=${set.idSettimana}&lab=${lab.id?c}"
                                           class="btn btn-block btn-lg btn-primary"> ${lab.descrizione} </a>
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
<script src="../risorse/js/ajaxiscrizioni.js"></script>
<#include "../struct/footer.html.ftl">
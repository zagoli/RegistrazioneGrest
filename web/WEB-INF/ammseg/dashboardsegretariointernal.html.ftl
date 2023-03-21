<h5 class="text-center">Pagamenti</h5>
<div class="row">
    <div class="col">
        <a href="/RegistrazioneGrest/App/GestisciPagamenti" class="btn btn-primary btn-block btn-lg"> GESTISCI PAGAMENTI
            RAGAZZI </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/GestisciPagamentiTerzamedia" class="btn btn-primary btn-block btn-lg"> GESTISCI
            PAGAMENTI TERZA MEDIA </a>
    </div>
</div>
<hr/>
<h5 class="text-center">Visualizzazione iscritti</h5>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/VisualizzaIscritti?target=an" class="btn btn-primary btn-block btn-lg">
            VISUALIZZA ANIMATORI </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/VisualizzaIscritti?target=rag" class="btn btn-primary btn-block btn-lg">
            VISUALIZZA RAGAZZI </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/VisualizzaIscritti?target=ter" class="btn btn-primary btn-block btn-lg">
            VISUALIZZA TERZAMEDIA </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/VisualizzaAttGen" class="btn btn-primary btn-block btn-lg"> VISUALIZZA
            COLLABORAZIONE GENITORI </a>
    </div>
</div>
<hr/>
<h5 class="text-center">Gestione squadre</h5>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/Squadre?target=rag" class="btn btn-primary btn-block btn-lg"> IMPOSTA SQUADRE
            RAGAZZI </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/Squadre?target=an" class="btn btn-primary btn-block btn-lg"> IMPOSTA SQUADRE
            ANIMATORI </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/Squadre?target=ter" class="btn btn-primary btn-block btn-lg"> IMPOSTA SQUADRE
            TERZAMEDIA </a>
    </div>
</div>
<hr/>
<h5 class="text-center">Gestione laboratori</h5>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/Laboratori?target=rag" class="btn btn-primary btn-block btn-lg"> IMPOSTA
            LABORATORI RAGAZZI </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/Laboratori?target=an" class="btn btn-primary btn-block btn-lg"> IMPOSTA
            LABORATORI ANIMATORI </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/Laboratori?target=ter" class="btn btn-primary btn-block btn-lg"> IMPOSTA
            LABORATORI TERZA MEDIA </a>
    </div>
</div>
<hr/>
<!--stampe-->
<h5 class="text-center">Stampe</h5>
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
            STAMPA ELENCO SINTESI RAGAZZI DI TERZA MEDIA </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/Stampa?target=collaborazione"
           class="btn btn-info btn-block btn-lg"> STAMPA ELENCO COLLABORAZIONE GENITORI </a>
    </div>
</div>
<div class="row mt-2">
    <div class="col">
        <a href="/RegistrazioneGrest/App/Stampa?target=anticipo" class="btn btn-info btn-block btn-lg">
            STAMPA ELENCO ENTRATA ANTICIPATA </a>
    </div>
</div>
<p class="text-center mt-2"> Presenze animatori settimanali </p>
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
<p class="text-center mt-2"> Presenze mensa settimanali </p>
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
<p class="text-center mt-2"> Presenze squadre settimanali </p>
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
                            <a href="/RegistrazioneGrest/App/Stampa?target=pressetsqu&idset=${set.idSettimana?c}&squadra=${sq.id?c}"
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
<p class="text-center mt-2"> Schede laboratorio settimanali </p>
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
                                <a href="/RegistrazioneGrest/App/Stampa?target=pressetlab&idset=${set.idSettimana?c}&lab=${lab.id?c}"
                                   class="btn btn-block btn-lg btn-primary"> ${lab.descrizione} </a>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </#list>
</div>
<#import "../struct/macros.html.ftl" as ui>
<h5 class="text-center">Pagamenti</h5>
<@ui.dashboardLink href="/RegistrazioneGrest/App/GestisciPagamenti" label="GESTISCI PAGAMENTI RAGAZZI" rowClass="row" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/GestisciPagamentiTerzamedia" label="GESTISCI PAGAMENTI TERZA MEDIA" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<hr/>
<h5 class="text-center">Visualizzazione iscritti</h5>
<@ui.dashboardLink href="/RegistrazioneGrest/App/VisualizzaIscritti?target=an" label="VISUALIZZA ANIMATORI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/VisualizzaIscritti?target=rag" label="VISUALIZZA RAGAZZI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/VisualizzaIscritti?target=ter" label="VISUALIZZA TERZAMEDIA" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/VisualizzaAttGen" label="VISUALIZZA COLLABORAZIONE GENITORI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<hr/>
<h5 class="text-center">Gestione squadre</h5>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Squadre?target=rag" label="IMPOSTA SQUADRE RAGAZZI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Squadre?target=an" label="IMPOSTA SQUADRE ANIMATORI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Squadre?target=ter" label="IMPOSTA SQUADRE TERZAMEDIA" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<hr/>
<h5 class="text-center">Gestione laboratori</h5>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Laboratori?target=rag" label="IMPOSTA LABORATORI RAGAZZI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Laboratori?target=an" label="IMPOSTA LABORATORI ANIMATORI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Laboratori?target=ter" label="IMPOSTA LABORATORI TERZA MEDIA" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg"/>
<hr/>
<#--stampe-->
<h5 class="text-center">Stampe</h5>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Stampa?target=elesintrag" label="STAMPA ELENCO SINTESI RAGAZZI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-info btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Stampa?target=elesintan" label="STAMPA ELENCO SINTESI ANIMATORI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-info btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Stampa?target=elesintter" label="STAMPA ELENCO SINTESI RAGAZZI DI TERZA MEDIA" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-info btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Stampa?target=collaborazione" label="STAMPA ELENCO COLLABORAZIONE GENITORI" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-info btn-block btn-lg"/>
<@ui.dashboardLink href="/RegistrazioneGrest/App/Stampa?target=anticipo" label="STAMPA ELENCO ENTRATA ANTICIPATA" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-info btn-block btn-lg"/>
<p class="text-center mt-2"> Presenze animatori settimanali </p>
<@ui.weeklyReportLinks settimane=settimane target="presgiornani"/>
<p class="text-center mt-2"> Presenze mensa settimanali </p>
<@ui.weeklyReportLinks settimane=settimane target="mensa"/>
<p class="text-center mt-2"> Presenze squadre settimanali </p>
<@ui.weeklyAccordion settimane=settimane accordionId="accordionPressetsqu" targetPrefix="pressetsqu" controlPrefix="pressetsqu"; set>
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
</@ui.weeklyAccordion>
<p class="text-center mt-2"> Schede laboratorio settimanali </p>
<@ui.weeklyAccordion settimane=settimane accordionId="accordionPressetlab" targetPrefix="pressetlab" controlPrefix="spressetlab"; set>
<#list laboratori as lab>
                        <@ui.dashboardLink href="/RegistrazioneGrest/App/Stampa?target=pressetlab&idset=${set.idSettimana?c}&lab=${lab.id?c}" label="${lab.descrizione}" rowClass="row mt-2" columnClass="col" buttonClass="btn btn-block btn-lg btn-primary"/>
                    </#list>
</@ui.weeklyAccordion>
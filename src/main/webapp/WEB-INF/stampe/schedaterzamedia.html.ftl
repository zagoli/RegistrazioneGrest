<#import "../struct/macros.html.ftl" as ui>
<#include "../struct/header.html.ftl">
<div class="container content" onclick="window.print();">
    <div class="mt-3">
        <p class="font-weight-bold text-uppercase text-center"> da consegnare alla segreteria del grest al momento
            dell'iscrizione </p>
    </div>
    <div class="shadow pt-2 pl-3 pr-3">
        <@ui.anagraficaIscritto iscritto=terzamedia/>
        <@ui.presenzaIscritto iscritto=terzamedia/>
        <@ui.laboratorioIscritto iscritto=terzamedia/>
        <@ui.appartenenzaIscritto iscritto=terzamedia/>
        <div class="row">
            <div class="col-sm">
                <strong>Scuola</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.scuola.descrizione+" - "+terzamedia.scuola.grado}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Sezione</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.sezione}</p>
            </div>
        </div>
        <@ui.tesseraIscritto iscritto=terzamedia/>

        <@ui.presenzaSettimanale calendari=calendari/>

        <div class="row">
            <div class="col-sm">
                <strong>E-mail</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.mail}</p>
            </div>
        </div>
        <#if terzamedia.cellulare??>
            <div class="row">
                <div class="col-sm">
                    <strong>Cellulare</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.cellulare}</p>
                </div>
            </div>
        </#if>
        <#if terzamedia.richieste??>
            <div class="row">
                <div class="col-sm">
                    <strong>Richieste</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.richieste}</p>
                </div>
            </div>
        </#if>
        <#if terzamedia.noteAlimentari??>
            <div class="row">
                <div class="col-sm">
                    <strong>Note Alimentari</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.noteAlimentari}</p>
                </div>
            </div>
        </#if>
        <div class="row">
            <div class="col-sm">
                <strong>Sa nuotare</strong>
            </div>
            <div class="col-sm">
                <p><#if terzamedia.saNuotare>Sì<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Ha partecipato alla festa del passaggio a Balconi</strong>
            </div>
            <div class="col-sm">
                <p><#if terzamedia.festaPassaggio>Sì<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Quota d'iscrizione</strong> <br>
            </div>
            <div class="col-sm">
                <strong>${quotaIscrizione}&euro;</strong>
            </div>
        </div>
        <#if attgen?size != 0>
            <hr>
            <strong> Attività genitore </strong>
            <#list attgen as attgen>
                <div class="row">
                    <div class="col-sm">
                        <p>${attgen.descrizione}</p>
                    </div>
                </div>
            </#list>
        </#if>
    </div>
    <div class="mt-3 shadow pt-2 pl-3 pr-3">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id='priv'/>
            <label for='priv' class="form-check-label">
                I sottoscritti genitori acconsentono alle dichiarazioni presentate in fase di
                registrazione, e dichiarano altresì che le informazioni inserite in fase di registrazione sono
                veritiere.
            </label>
        </div>
        <p class="text-center font-weight-light font-italic mb-4"> Firme di entrambi i genitori </p>
        <hr class="pb-2 border-dark"/>
    </div>
</div>
<script>window.print();</script>
<#include "../struct/footer.html.ftl">

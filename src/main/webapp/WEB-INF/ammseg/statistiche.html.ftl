<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<div class="row mt-3 content">
    <div class="col-sm">
        <#--colonna sinistra-->
        <div class="container-fluid">
            <ul class="list-group">
                <@ui.statisticBadge label="Numero di utenti registrati" count=nreg/>
                <@ui.statisticBadge label="Numero di ragazzi iscritti" count=nrag/>
                <@ui.statisticBadge label="Numero di ragazzi che hanno pagato" count=npag/>
                <@ui.statisticBadge label="Numero di animatori iscritti" count=nani/>
                <@ui.statisticBadge label="Numero di ragazzi di terza media iscritti" count=nter/>
                <@ui.statisticBadge label="Numero di ragazzi che usufruiscono della mensa" count=nmensatot/>
                <@ui.statisticBadge label="Numero di ragazzi che usufruiscono dell'entrata anticipata" count=eanttot/>
            </ul>
            <h3 class="mt-3 font-weight-bold">STATISTICHE ANIMATORI</h3>
            <div class="container border pt-2 pb-2 mt-3">
                <canvas id="graficoAniEta"></canvas>
            </div>
            <div class="container border pt-2 pb-2 mt-3">
                <canvas id="graficoAniLab"></canvas>
            </div>
            <div class="container border pt-2 pb-2 mt-3">
                <canvas id="graficoAniSett"></canvas>
            </div>
        </div>
    </div>
    <div class="col-sm">
        <#--colonna destra-->
        <h3 class="mt-3 font-weight-bold">STATISTICHE RAGAZZI</h3>
        <div class="container-fluid">
            <div class="container border pt-2 pb-2 mt-1">
                <canvas id="graficoLaboratori"></canvas>
            </div>
            <div class="container border pt-2 pb-2 mt-1">
                <canvas id="graficoRagClassi"></canvas>
            </div>
            <div class="container border pt-2 pb-2 mt-1">
                <canvas id="graficoMensaSett"></canvas>
            </div>
            <div class="container border pt-2 pb-2 mt-1">
                <canvas id="graficoRagSett"></canvas>
            </div>
            <div class="container border pt-2 pb-2 mt-1">
                <canvas id="graficoEaSett"></canvas>
            </div>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.js"></script>
<script src="../js/palette.js"></script>
<script>
<@ui.statisticChart element="elGrafRagClassi" selector="#graficoRagClassi" variable="grafRagClassi" type="doughnut" data=mapragclassi mapData=true label="Ragazzi per classe" palette="cb-Accent" labelPrefix="Ragazzi in " labelSuffix=""/>
<@ui.statisticChart element="elGrafAniEta" selector="#graficoAniEta" variable="grafAniEta" type="doughnut" data=mapanieta mapData=true label="Animatori per eta" palette="tol-rainbow" labelPrefix="classe " labelSuffix=""/>
<@ui.statisticChart element="elGrafLaboratori" selector="#graficoLaboratori" variable="grafLaboratori" type="pie" data=mapraglab mapData=true label="Ragazzi in laboratori" palette="mpn65" labelPrefix="" labelSuffix=""/>
<@ui.statisticChart element="elGrafAniLab" selector="#graficoAniLab" variable="grafAniLab" type="pie" data=mapanilab mapData=true label="Animatori in laboratori" palette="mpn65" labelPrefix="" labelSuffix=""/>
<@ui.statisticChart element="elGrafAniSett" selector="#graficoAniSett" variable="grafAniSett" type="bar" data=anisett mapData=false label="Animatori per settimane" palette="sol-accent" labelPrefix="" labelSuffix=" settimana"/>
<@ui.statisticChart element="elGrafRagSett" selector="#graficoRagSett" variable="grafRagSett" type="bar" data=ragsett mapData=false label="Ragazzi per settimane" palette="sol-accent" labelPrefix="" labelSuffix=" settimana"/>
<@ui.statisticChart element="elGrafMensaSett" selector="#graficoMensaSett" variable="grafMensaSett" type="bar" data=mensasett mapData=false label="Mensa per settimane" palette="sol-accent" labelPrefix="" labelSuffix=" settimana"/>
<@ui.statisticChart element="elGrafEaSett" selector="#graficoEaSett" variable="grafEaSett" type="bar" data=eansett mapData=false label="Entrata anticipata per settimane" palette="sol-accent" labelPrefix="" labelSuffix=" settimana"/>
</script>
</@ui.page>
<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="row mt-3 content">
    <div class="col-sm">
        <!--colonna sinistra-->
        <div class="container-fluid">
            <ul class="list-group">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Numero di utenti registrati
                    <span class="badge badge-primary">${nreg}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Numero di ragazzi iscritti
                    <span class="badge badge-primary">${nrag}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Numero di ragazzi che hanno pagato
                    <span class="badge badge-primary">${npag}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Numero di animatori iscritti
                    <span class="badge badge-primary">${nani}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Numero di ragazzi di terza media iscritti
                    <span class="badge badge-primary">${nter}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Numero di ragazzi che usufruiscono della mensa
                    <span class="badge badge-primary">${nmensatot}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Numero di ragazzi che usufruiscono dell'entrata anticipata
                    <span class="badge badge-primary">${eanttot}</span>
                </li>
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
        <!--colonna destra-->
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
<script src="../risorse/js/palette.min.js"></script>
<script>
    var elGrafRagClassi = $('#graficoRagClassi');
    var grafRagClassi = new Chart(elGrafRagClassi, {
        type: 'doughnut',
        data: {
            labels: [<#list mapragclassi as classe, nrag>'Ragazzi in ${classe}', </#list>],
            datasets: [{
                data: [<#list mapragclassi as classe, nrag>'${nrag}', </#list>],
                label: 'Ragazzi per classe',
                backgroundColor: palette('cb-Accent', ${mapragclassi?size}).map(function (hex) {
                    return '#' + hex;
                }),
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    var elGrafAniEta = $('#graficoAniEta');
    var grafAniEta = new Chart(elGrafAniEta, {
        type: 'doughnut',
        data: {
            labels: [<#list mapanieta as anno, nani>'classe ${anno}', </#list>],
            datasets: [{
                data: [<#list mapanieta as anno, nani>'${nani}', </#list>],
                label: 'Animatori per eta',
                backgroundColor: palette('tol-rainbow', ${mapanieta?size}).map(function (hex) {
                    return '#' + hex;
                }),
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    var elGrafLaboratori = $('#graficoLaboratori');
    var grafLaboratori = new Chart(elGrafLaboratori, {
        type: 'pie',
        data: {
            labels: [<#list mapraglab as lab, n>'${lab}', </#list>],
            datasets: [{
                data: [<#list mapraglab as lab, n>'${n}', </#list>],
                label: 'Ragazzi in laboratori',
                backgroundColor: palette('mpn65', ${mapraglab?size}).map(function (hex) {
                    return '#' + hex;
                }),
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    var elGrafAniLab = $('#graficoAniLab');
    var grafAniLab = new Chart(elGrafAniLab, {
        type: 'pie',
        data: {
            labels: [<#list mapanilab as lab, n>'${lab}', </#list>],
            datasets: [{
                data: [<#list mapanilab as lab, n>'${n}', </#list>],
                label: 'Animatori in laboratori',
                backgroundColor: palette('mpn65', ${mapanilab?size}).map(function (hex) {
                    return '#' + hex;
                }),
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    var elGrafAniSett = $('#graficoAniSett');
    var grafAniSett = new Chart(elGrafAniSett, {
        type: 'bar',
        data: {
            labels: [<#list anisett as ans>'${ans[0]} settimana', </#list>],
            datasets: [{
                data: [<#list anisett as ans>'${ans[1]}', </#list>],
                label: 'Animatori per settimane',
                backgroundColor: palette('sol-accent', ${anisett?size}).map(function (hex) {
                    return '#' + hex;
                }),
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    var elGrafRagSett = $('#graficoRagSett');
    var grafRagSett = new Chart(elGrafRagSett, {
        type: 'bar',
        data: {
            labels: [<#list ragsett as rs>'${rs[0]} settimana', </#list>],
            datasets: [{
                data: [<#list ragsett as rs>'${rs[1]}', </#list>],
                label: 'Ragazzi per settimane',
                backgroundColor: palette('sol-accent', ${ragsett?size}).map(function (hex) {
                    return '#' + hex;
                }),
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    var elGrafMensaSett = $('#graficoMensaSett');
    var grafMensaSett = new Chart(elGrafMensaSett, {
        type: 'bar',
        data: {
            labels: [<#list mensasett as ms>'${ms[0]} settimana', </#list>],
            datasets: [{
                data: [<#list mensasett as ms>'${ms[1]}', </#list>],
                label: 'Mensa per settimane',
                backgroundColor: palette('sol-accent', ${mensasett?size}).map(function (hex) {
                    return '#' + hex;
                }),
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    var elGrafEaSett = $('#graficoEaSett');
    var grafEaSett = new Chart(elGrafEaSett, {
        type: 'bar',
        data: {
            labels: [<#list eansett as eans>'${eans[0]} settimana', </#list>],
            datasets: [{
                data: [<#list eansett as eans>'${eans[1]}', </#list>],
                label: 'Entrata anticipata per settimane',
                backgroundColor: palette('sol-accent', ${eansett?size}).map(function (hex) {
                    return '#' + hex;
                }),
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
</script>
<#include "../struct/footer.html.ftl">
$("select").change(function () {
    $(this).addClass("changed");
});

$("form").submit(function () {
    $("select:not(.changed)").remove();
});
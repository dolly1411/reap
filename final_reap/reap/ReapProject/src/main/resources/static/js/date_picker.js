$(function() {
    $('img[name="dates"]').daterangepicker({
        opens: 'left'
    }, function(start, end, label) {
        $.ajax({
            url: "/setSessionDates",
            type: "POST",
            data: {
                start : start.format('YYYY-MM-DD'),
                end : end.format('YYYY-MM-DD')
            },
            success: function (data) {
                location.reload();
            }
        });
    });
});

$('#userBadgeSelect').click(function () {
    if(parseInt(document.getElementById('ugoldcount').value) === 0) {
        document.getElementById("userBadgeSelect").options[1].disabled = true;
    }
    if(parseInt(document.getElementById('usilvercount').value) === 0){
        document.getElementById("userBadgeSelect").options[2].disabled = true;
    }
    if(parseInt(document.getElementById('ubronzecount').value) === 0){
        document.getElementById("userBadgeSelect").options[3].disabled = true;
    }
});
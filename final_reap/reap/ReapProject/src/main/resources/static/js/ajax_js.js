$("#searchNewer").autocomplete({
    source: function (request, response) {
        $.ajax({
            url: "http://localhost:8080/getUserListActive",
            type: "GET",
            data: {
                term: request.term,
                user_id: $("#user-id").val()
            },
            success: function (data) {
                response($.map(data, function (el) {
                    return {
                        label: el.nameEmail,
                        value: el.email
                    };
                }));
            }
        });
    },
    select: function (event, ui) {
        this.value = ui.item.label;
        $(this).next("input").val(ui.item.value);
        event.preventDefault();
    }
});

$(".table-dropdown-role").change(function () {
    var x = this.value;
    var y = $(this).parent().parent().attr('id');
    $.ajax({
        url: "/updateUserRole",
        type: "POST",
        data: {
            role: x,
            userId: y
        },
        success: function (data) {
            location.reload();
        }
    });
});

$(".isAdmin-checkbox").change(function () {
    var x;
    var y = $(this).parent().parent().parent().attr('id');
    if ($(this).is(":checked")) {
        x = "true";
    } else {
        x = "false";
    }
    $.ajax({
        url: "/updateAdminRole",
        type: "POST",
        data: {
            isAdmin: x,
            userId: y
        },
        success: function (data) {
            location.reload();
        }
    });
});

$(".isActive-checkbox").change(function () {
    var x;
    var y = $(this).parent().parent().parent().attr('id');
    if ($(this).is(":checked")) {
        x = "true";
    } else {
        x = "false";
    }
    $.ajax({
        url: "/updateUserActive",
        type: "POST",
        data: {
            isActive: x,
            userId: y
        },
        success: function (data) {
            location.reload();
        }
    });
});

$(".input-availPoints").change(function () {
    var x = this.value;
    var y = $(this).parent().parent().attr('id');
     $.ajax({
        url: "/updateAvailPoints",
        type: "POST",
        data: {
            availPoints: x,
            userId: y
        },
        success: function (data) {
            location.reload();
        }
    });
});

$(".input-goldCount").change(function () {
    var x = this.value;
    var y = $(this).parent().parent().attr('id');
    $.ajax({
        url: "/updateGoldCount",
        type: "POST",
        data: {
            goldCount: x,
            userId: y
        },
        success: function (data) {
            location.reload();
        }
    });
});

$(".input-silverCount").change(function (request, response) {
    var x = this.value;
    var y = $(this).parent().parent().attr('id');
    $.ajax({
        url: "/updateSilverCount",
        type: "POST",
        data: {
            silverCount: x,
            userId: y
        },
        success: function (data) {
            location.reload();
        }
    });
});

$(".input-bronzeCount").change(function () {
    var x = this.value;
    var y = $(this).parent().parent().attr('id');
    $.ajax({
        url: "/updateBronzeCount",
        type: "POST",
        data: {
            bronzeCount: x,
            userId: y
        },
        success: function (data) {
            location.reload();
        }
    });
});

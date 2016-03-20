$(document).ready(function(){
    $(document).on('click', '.loginButton', function() {
        var email = $("#email");
        var number = $("#number");
        if (email.val() == '' && number.val()!= '') {
            var element = $("#verifyCode");
            if (typeof(element.val()) === "undefined") {
                $("<div class=\"row\">" +
                    "<div class=\"input-field col s12\">" +
                    "<input id=\"verifyCode\" type=\"text\" name=\"verifyCode\" class=\"validate\">" +
                    "<label for=\"verifyCode\">Verify code:</label>" +
                    "</div>" +
                    "</div>").insertAfter(".rootView");
                $.ajax({
                    type: "GET",
                    url: "/api/verify",
                    data: "driver="+number.val(),
                    success: function (response) {
                        if (response.status == "error") {
                            Materialize.toast(response.result, 4000);
                        }
                    }
                });
            } else {
                $( "#target" ).submit();
            }
        }else if (email.val() != '' && number.val() == '') {
            $( "#target" ).submit();
        }
    });
});
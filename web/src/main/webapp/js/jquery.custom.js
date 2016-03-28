$(document).ready(function(){
    $('#overlaydiv').hide();
    $('.modal-trigger').leanModal();
    $('select').material_select();
    $('#wizard').smartWizard(
        {contentURL:'/employee/order/add',
            onFinish:onFinishCallback});
    function onFinishCallback(){
        var i = 0;
        $('input:checkbox:checked').each(function () {
            i++;
        });
        var count = $('#count').val();
        if(typeof(count)  === "undefined"){
            Materialize.toast("Try to change order", 4000);
        }else{
            if (i == count){
                $('#overlaydiv').show();
                $.ajax({
                    type: "POST",
                    url: "/employee/order/submit",
                    data: $("form#fullForm").serialize(),
                    success: function(response) {
                        if (response.status == "success"){
                            $('#overlaydiv').hide();
                            window.location.href = "/employee/orders";
                        }else {
                            $('#overlaydiv').hide();
                            Materialize.toast(response.result, 4000);
                        }
                    }
                });

            }
            else
                Materialize.toast("Select " + (count-i) +" drivers or change order", 4000);
        }
    }
    $(document).on('click', '.add', function() {
        var value = $('.number').last().val();
        var rootView = $( '#readroot' ).clone().appendTo('#writeroot');
        rootView.find('input').val('');
        rootView.find('.pickup').autocomplete({
            serviceUrl: '/api/cities'
        });
        rootView.find('.unload').autocomplete({
            serviceUrl: '/api/cities'
        });
        if ($.isNumeric(value)){
            rootView.find('.number').val(++value);
        }else{
            rootView.find('.number').val(0);
        };

    });
    $(document).on('click', '.remove', function() {
        $(this).closest('.root').remove();
    });
});
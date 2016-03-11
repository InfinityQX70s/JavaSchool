$(document).ready(function(){
    $('select').material_select();
    $('#wizard').smartWizard(
        {contentURL:'/employee/order/add',
            onFinish:onFinishCallback});
    function onFinishCallback(){
        $('form#fullForm').submit();
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
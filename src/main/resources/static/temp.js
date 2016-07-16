$(document).ready(function () {

    $('.btn-filter').on('click', function () {
        var $target = $(this).data('target');
        if ($target != 'all') {
            $('tbody tr').css('display', 'none');
            $('tbody tr[data-status="' + $target + '"]').fadeIn('slow');
        } else {
            $('tbody tr').css('display', 'none').fadeIn('slow');
        }
    });

    $('a').tooltip();

    // jquery dropdown
    $(document).ready(function() {
        $(".js-example-basic-single").select2();
    });

});
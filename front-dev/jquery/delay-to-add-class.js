$(function() {
    var delay = 60

    $('.item').each(function(index, el) {
        setTimeout(function() {
            $(el).addClass('animate')
        }, (index + 1) * delay)
    })
})


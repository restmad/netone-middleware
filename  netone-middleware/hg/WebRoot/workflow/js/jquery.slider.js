﻿(function($) {
$.sliderSettings = {
        selectedClass: 'selectedSlider',
        rootEnd: '#',
        pad_out: 25,
        pad_in: 15,
        time: 150
    };
    $.fn.slidinate = function(settings) {
        $.extend($.sliderSettings, settings);

        return $(this).each(function(i) {
            $(this).hover(function() {
                if (!$(this).hasClass($.sliderSettings.selectedClass)) {
                    $(this).animate({ paddingLeft: $.sliderSettings.pad_out }, $.sliderSettings.time);
                }
            }, function() {
                if (!$(this).hasClass($.sliderSettings.selectedClass)) {
                    $(this).animate({ paddingLeft: $.sliderSettings.pad_in }, $.sliderSettings.time);
                }
            });
        }).filter('[href$=' + $.sliderSettings.rootEnd + ']').each(function(i) {
            $(this).click(function() {
                if ($(this).hasClass($.sliderSettings.selectedClass)) {
                    $(this).removeClass($.sliderSettings.selectedClass).animate({ paddingLeft: $.sliderSettings.pad_in }, $.sliderSettings.time);
                } else {
                    $(this).addClass($.sliderSettings.selectedClass).animate({ paddingLeft: $.sliderSettings.pad_out }, $.sliderSettings.time);
                }
            });
        }).end().filter('.' + $.sliderSettings.selectedClass).animate({ paddingLeft: $.sliderSettings.pad_out }, $.sliderSettings.time).end();
    };
})(jQuery);
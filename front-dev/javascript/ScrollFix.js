/**
 * Fix Scroll On iPhone
 */
'use strict';

(function() {
    // global class
    function ScrollFix() {
        // not draggable
        document.addEventListener('touchmove', function (e) {
            e.preventDefault();
        })
    }

    // check element is dom object
    ScrollFix.prototype._is_dom_object = function (el) {
        if (typeof el === 'object') {
            return el instanceof HTMLElement;
        }
        return el && typeof el === 'object' && el.nodeType === 1 && typeof el.nodeName === 'string';
    }

    // only explicit export method
    ScrollFix.prototype.fix = function (el) {
        if (!this._is_dom_object(el)) {
            throw new Error('element is not dom object');
        }

        // touchstart event
        el.addEventListener('touchstart', function (e) {
            var $el = e.currentTarget;

            if ($el.scrollTop <= 0) {
                $el.scrollTop = 1;
            } else if ($el.scrollTop >= $el.scrollHeight - $el.clientHeight) {
                $el.scrollTop = $el.scrollHeight - $el.clientHeight - 1;
            }

            // header and footer flag
            if (e.touches[0].clientY <= 50 || e.touches[0].clientY >= window.innerHeight - 50) {
                this._disabled_flag = true;
            }

            // quickly slide check
            this._quickly_slide = {
                start_x: e.touches[0].clientX,
                start_y: e.touches[0].clientY,
                start_time: (new Date()).getTime(),
                threshold: 8.00 // 8.00px/1ms
            }
        });

        // touchmove event
        el.addEventListener('touchmove', function (e) {
            var $el = e.currentTarget;

            var move_y = e.touches[0].clientY;
            var all_distance = Math.abs(this._quickly_slide.start_y - move_y);
            var all_time = (new Date()).getTime() - this._quickly_slide.start_time
            if (!($el.scrollTop > 0 && $el.scrollTop < $el.scrollHeight - $el.clientHeight)) {
                // check slide speed
                if (all_distance / all_time > this._quickly_slide.threshold) {
                    this._disabled_flag = true
                }
            }

            // slide to down
            if ($el.scrollTop <= 1 && this._quickly_slide.start_y - move_y < 0) {
                this._disabled_flag = true
            }

            // slide to up
            if (($el.scrollTop >= $el.scrollHeight - $el.clientHeight - 1) && (this._quickly_slide.start_y - move_y > 0)) {
                this._disabled_flag = true
            }

            // allowed draggable
            if ($el.scrollTop > 0 && $el.scrollTop < $el.scrollHeight - $el.clientHeight && !this._disabled_flag) {
                e.stopPropagation();
            } else {
                // reset state
                this._disabled_flag = false;
            }
        })
    }

    // export to global
    window.ScrollFix = ScrollFix;
})();

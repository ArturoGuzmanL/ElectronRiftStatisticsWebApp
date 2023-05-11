/**
 * adds a pause and unpause method to Mousetraps
 * this allows you to enable or disable keyboard shortcuts
 * without having to reset Mousetraps and rebind everything
 */
/* global Mousetraps:true */
(function(Mousetraps) {
    var _originalStopCallback = Mousetraps.prototype.stopCallback;

    Mousetraps.prototype.stopCallback = function(e, element, combo) {
        var self = this;

        if (self.paused) {
            return true;
        }

        return _originalStopCallback.call(self, e, element, combo);
    };

    Mousetraps.prototype.pause = function() {
        var self = this;
        self.paused = true;
    };

    Mousetraps.prototype.unpause = function() {
        var self = this;
        self.paused = false;
    };

    Mousetraps.init();
}) (Mousetraps);

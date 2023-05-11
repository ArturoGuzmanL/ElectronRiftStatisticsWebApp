# Mousetraps
Mousetraps is a simple library for handling keyboard shortcuts in Javascript.

It is a modification of Craig Campbell's [Mousetrap](https://github.com/ccampbell/mousetrap).

It is licensed under the Apache 2.0 license.

It is around **2kb** minified and gzipped and **4.5kb** minified, has no external dependencies, and has been tested in the following browsers:

- Internet Explorer 6+
- Safari
- Firefox
- Chrome

It has support for `keypress`, `keydown`, and `keyup` events on specific keys, keyboard combinations, or key sequences.

## Differences from Mousetrap
Where Mousetrap overwrites the original callback when binding the same key event again, Mousetraps allows binding multiple callbacks to the same key event.
```js
    function loga() {
        console.log('Mouse');
    }
    function logb() {
        console.log('Traps!');
    }

    Mousetraps.bind('a', loga );
    Mousetraps.bind('a', logb );
    // Output on key event 'a':
    // > Mouse
    // > Traps!
```

The API remains the same, with one exception:
```js
    // Unbinding requires passing the original callback along with the key
    Mousetraps.unbind('a', loga);
    Mousetraps.unbind('a', logb);
```

## Getting started

1. Include Mousetraps on your page before the closing `</body>` tag

    ```html
    <script src="/path/to/mousetraps.min.js"></script>
    ```

    or install `mousetraps` from `npm` and require it

    ```js
    var Mousetraps = require('mousetraps');
    ```

2. Add some keyboard events to listen for

    ```html
    <script>
        // single keys
        Mousetraps.bind('4', function() { console.log('4'); });
        Mousetraps.bind("?", function() { console.log('show shortcuts!'); });
        Mousetraps.bind('esc', function() { console.log('escape'); }, 'keyup');

        // combinations
        Mousetraps.bind('command+shift+k', function() { console.log('command shift k'); });

        // map multiple combinations to the same callback
        Mousetraps.bind(['command+k', 'ctrl+k'], function() {
            console.log('command k or control k');

            // return false to prevent default browser behavior
            // and stop event from bubbling
            return false;
        });

        // gmail style sequences
        Mousetraps.bind('g i', function() { console.log('go to inbox'); });
        Mousetraps.bind('* a', function() { console.log('select all'); });

        // konami code!
        Mousetraps.bind('up up down down left right left right b a enter', function() {
            console.log('konami code');
        });
    </script>
    ```

## Why Mousetraps?

There are a number of other similar libraries out there so what makes this one different?

- There are no external dependencies, no framework is required
- You are not limited to `keydown` events (You can specify `keypress`, `keydown`, or `keyup` or let Mousetraps choose for you).
- You can bind key events directly to special keys such as `?` or `*` without having to specify `shift+/` or `shift+8` which are not consistent across all keyboards
- It works with international keyboard layouts
- You can bind Gmail like key sequences in addition to regular keys and key combinations
- You can programatically trigger key events with the `trigger()` method
- It works with the numeric keypad on your keyboard
- The code is well documented/commented

## Tests

Unit tests are run with <a href="https://mochajs.org/">mocha</a>.

### Running in browser

[View it online](http://rawgit.com/ccampbell/Mousetraps/master/tests/mousetrap.html) to check your browser compatibility. You may also download the repo and open `tests/Mousetraps.html` in your browser.

### Running with Node.js

1. Install development dependencies

    ```sh
    cd /path/to/repo
    npm install
    ```

3. Run tests

    ```sh
    npm test
    ```

## Documentation

Full documentation of Mousetrap can be found at https://craig.is/killing/mice. Same documentation applies to Mousetraps, except binding the same key event no longer overwrites any original callbacks. unbind requires an extra argument.

# Global Bind

This extension allows you to specify keyboard events that will work anywhere including inside textarea/input fields.

Usage looks like:

```javascript
Mousetraps.bindGlobal('ctrl+s', function() {
    _save();
});
```

This means that a keyboard event bound using ``mousetraps.bind`` will only work outside of form input fields, but using ``moustrap.bindGlobal`` will work in both places.

If you wanted to create keyboard shortcuts that only work when you are inside a specific textarea you can do that too by creating your own extension.

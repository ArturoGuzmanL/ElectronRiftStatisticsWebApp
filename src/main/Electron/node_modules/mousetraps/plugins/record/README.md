# Record

This extension lets you use Mousetraps to record keyboard sequences and play them back:

```html
<button onclick="recordSequence()">Record</button>

<script>
    function recordSequence() {
        Mousetraps.record(function(sequence) {
            // sequence is an array like ['ctrl+k', 'c']
            alert('You pressed: ' + sequence.join(' '));
        });
    }
</script>
```

<table class="table">
    <thead><tr><td>Name</td><td>Value</td></tr></thead>
    <tbody>

    {% for key, param in dispatcher_params %}
        <tr><td>{{ key }}</td><td>{{ param }}</td></tr>
    {% endfor %}

    </tbody>
</table>

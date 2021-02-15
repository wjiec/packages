
(function() {
    'use strict';

    const els = document.querySelectorAll('.noSelect');
    for (let i = 0; i < els.length; i++) {
        addCopyer(els[i]);
    }
})();


function addCopyer(el) {
    el.style.position = 'relative';

    const btn = document.createElement('button');
    btn.innerText = 'Copy ME';
    btn.style.boxShadow = 'box-shadow: 0 0 5px 2px rgba(0, 0, 0, .25)';
    btn.style.position = 'absolute';
    btn.style.right = '50px';
    btn.style.top = '10px';
    btn.addEventListener('click', function(e) {
        e.stopPropagation();
        e.preventDefault();

        copyText(el);
    }, false);

    el.appendChild(btn);
}

function copyText(el) {
    const holder = document.createElement("textarea");
    holder.value = el.innerText.replace('Copy ME', '');
    holder.style.opacity = 0;

    el.appendChild(holder);
    holder.select();
    document.execCommand("copy");
    el.removeChild(holder);
}

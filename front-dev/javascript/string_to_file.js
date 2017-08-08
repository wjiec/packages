var string_to_file = (function(filename, string) {
  var el = document.createElement('a')
  el.style.display = 'none'

  var blob = new Blob([string], {type: 'octet/stream'})
  var url = window.URL.createObjectURL(blob)

  el.href = url
  el.download = filename

  el.click()
  window.URL.revokeObjectURL(url);
})

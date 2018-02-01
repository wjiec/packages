<template>
  <div class="hello">
    <div style="margin: 1rem auto;">
      <p><em style="color: red;">bindLocalStorage: </em><input v-model="localStorageMessage"></p>
      <p><em style="color: red;">localStorage: </em>{{ localStorageMessage }}</p>
    </div>

    <div style="margin: 1rem auto;">
      <p><em style="color: red;">bindLocalStorage: </em><input v-model="sessionStorageMessage"></p>
      <p><em style="color: red;">sessionStorage: </em>{{ sessionStorageMessage }}</p>
    </div>

    <div>
      <p v-for="log in logArray" :key="log.oldValue + log.newValue + Math.random().toString()">{{ log.type }}: {{ log.oldValue }} => {{ log.newValue }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      // 5M
      localStorageMessage: '',
      sessionStorageMessage: '',
      logArray: []
    }
  },
  watch: {
    localStorageMessage: function (newValue, oldValue) {
      window.localStorage.setItem('localValue', newValue)
      this.logArray.push({
        type: 'localStorage',
        newValue: newValue,
        oldValue: oldValue
      })
    },
    sessionStorageMessage: function (newValue, oldValue) {
      window.sessionStorage.setItem('sessionValue', newValue)
      this.logArray.push({
        type: 'sessionStorage',
        newValue: newValue,
        oldValue: oldValue
      })
    }
  },
  created: function () {
    this.localStorageMessage = window.localStorage.getItem('localValue')
    if (!this.localStorageMessage) {
      this.localStorageMessage = 'This is default localValue'
    }

    this.sessionStorageMessage = window.sessionStorage.getItem('sessionValue')
    if (!this.sessionStorageMessage) {
      this.sessionStorageMessage = 'This is default sessionValue'
    }

    // storage 只会在 localStorage 发生变化的时候触发
    // 因为sessionStorage是针对每个页面隔离的
    window.addEventListener('storage', (event) => {
      this.localStorageMessage = event.newValue
      this.logArray.push({
        type: 'OtherPage',
        newValue: event.newValue,
        oldValue: event.oldValue
      })
    })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

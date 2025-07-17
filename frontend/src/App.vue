<script setup>
</script>

<template>
  <transition name="page" mode="out-in">
    <router-view />
  </transition>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
}

/* 页面过渡动画 */
.page-enter-active, .page-leave-active {
  transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
}

.page-enter-from {
  opacity: 0;
  transform: translateX(30px) scale(0.98);
}

.page-leave-to {
  opacity: 0;
  transform: translateX(-30px) scale(0.98);
}

.page-enter-to, .page-leave-from {
  opacity: 1;
  transform: translateX(0) scale(1);
}
</style>

<script setup lang="ts">
const props = defineProps(["type"])
</script>

<template>
  <component :is="type">
    <slot />
  </component>
</template>

<style>
h1 {
  font-size: 42px;
  font-weight: 600;
  position: relative;
  z-index: 3;
  margin-top: 0;
  color: var(--main-white);
}

h2 {
  font-size: 22px;
  font-weight: 400;
  position: relative;
  z-index: 3;
  margin-top: 0;
  color: var(--main-white);
}
</style>

<script setup lang="ts">
const { type } = defineProps(["type"])
</script>

<template>
  <component :is="type">
    <slot />
  </component>
</template>

<style>
h1 {
  font-size: 42px !important;
  font-weight: 600;
  color: var(--main-white);

  @media(max-width: 475px) {
    font-size: 28px !important;
  }
}

h2 {
  font-size: 22px;
  font-weight: 400;
  color: var(--main-white);

  @media(max-width: 475px) {
    font-size: 18px;
  }
}

h1, h2 {
  position: relative;
  padding: 0 15px;
  margin-top: 0;
  z-index: 7;
  text-align: center;
}
</style>

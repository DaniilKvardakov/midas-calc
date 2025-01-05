<script setup lang="ts">
</script>

<template>
  <button :class="$style.CalculatorButton" type="submit">
    <slot />
  </button>
</template>

<style module>
.CalculatorButton {
  width: 100%;
  padding: 10px 12px;
  background-color: var(--main-orange-active);
  border: none;
  cursor: pointer;
  color: var(--main-white);
  font-weight: 500;
  border-radius: 10px;

  &:hover {
    background-color: var(--main-orange-focus);
  }
}
</style>

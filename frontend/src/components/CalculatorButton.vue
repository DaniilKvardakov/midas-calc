<script setup lang="ts">
import {ref} from "vue";
import {useCalcStore} from "../store/calculator";
const calcStore = useCalcStore();
</script>

<template>
  <button :class="$style.CalculatorButton" type="submit" @click="startAudio" :disabled="calcStore.isButtonDisabled">
    <slot />
  </button>
</template>

<style module>
.CalculatorButton {
  width: 100%;
  padding: 12px;
  background-color: var(--main-orange-active);
  border: none;
  cursor: pointer;
  color: var(--main-white);
  font-weight: 500;
  border-radius: 10px;
  font-size: 18px;

  &:hover {
    background-color: var(--main-orange-focus);
  }
}
</style>

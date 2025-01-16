<script setup lang="ts">
import type {ICalculatorTabProps} from "../types/calculator.types.ts";

const { title } = defineProps<ICalculatorTabProps>();

</script>

<template>
  <h4 :class="$style.CalculatorTab">
    {{ title }}
  </h4>
</template>

<style module>
.CalculatorTab {
  flex-grow: 1;
  text-align: center;
  padding: 10px 20px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  font-weight: 500;
  color: var(--main-white);
  background-color: var(--main-orange);
  cursor: pointer;
}
</style>

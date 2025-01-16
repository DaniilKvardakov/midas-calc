<script setup lang="ts">
import type {IInput} from "../types/calculator.types.ts";
import {reactive} from "vue";

const props = defineProps<IInput>();
const emit = defineEmits(["input"]);

const time = reactive({
  minutes: 0,
})

const inputHandler = () => {
  emit('input', {
    name: props.name,
    value: `PT${ time.minutes || 0 }M`
  })
}
</script>

<template>
  <div :class="$style.CalculatorTime">
    <div :class="$style.CalculatorTimeTitle">
      {{ title }}
    </div>
    <div :class="$style.CalculatorTimeWrapper">
      <div :class="$style.CalculatorTimeInputWrapper">
        <input type="number" :class="$style.CalculatorTimeInput" @input="inputHandler" v-model="time.minutes" :required="required" max="240" min="5">
        <span :class="$style.CalculatorTimeInputLabel">мин.</span>
      </div>
    </div>
  </div>
</template>

<style module>
.CalculatorTimeInputWrapper {
  display: flex;
  gap: 5px;
  position: relative;
}

.CalculatorTimeInput {
  width: 100%;
  border: none;
  border-bottom: 1px solid var(--main-orange);
  padding-bottom: 10px;
  outline: none;


  &:focus {
    border-color: var(--main-orange-focus);
  }
}

.CalculatorTimeTitle {
  font-size: 14px;
  margin-bottom: 8px;
  color: #9b9b9b;
}

.CalculatorTimeWrapper {
  display: grid;
  grid-template-columns: 1fr;
  grid-gap: 10px;
}

.CalculatorTimeInputLabel {
  position: absolute;
  top: 50%;
  right: 5px;
  transform: translateY(-50%);
  z-index: 3;
  font-size: 12px;
  color: #9b9b9b;
}
</style>

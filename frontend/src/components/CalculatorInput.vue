<script setup lang="ts">
import type {IInput} from "../types/calculator.types.ts";

const props = defineProps<IInput>();
const emit = defineEmits(['input']);
//@ts-ignore
const inputHandler = (val: any) => {
  //@ts-ignore
  const value = val.target?.value;

  emit('input', {
    value,
    name: props.name,
  })
}
</script>

<template>
  <div :class="$style.CalculatorInputWrapper">
    <div :class="$style.CalculatorInputTitle">
      {{ title }}
    </div>
    <input :class="$style.CalculatorInput" type="text" :name="name" :value="defaultVal || ''" :required="required" @input="inputHandler">
  </div>
</template>

<style module>
.CalculatorInput {
  border: none;
  border-bottom: 1px solid var(--main-orange);
  padding-bottom: 10px;
  outline: none;
  width: 100%;


  &:focus {
    border-color: var(--main-orange-focus);
  }
}

.CalculatorInputTitle {
  font-size: 14px;
  color: #9b9b9b;
}
</style>

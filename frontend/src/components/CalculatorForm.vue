<script setup lang="ts">
import type {ICalculatorForm} from "../types/calculator.types.ts";
import CalculatorInput from "./CalculatorInput.vue";
import CalculatorButton from "./CalculatorButton.vue";
import {useCalcStore} from "../store/calculator";

const props = defineProps<ICalculatorForm>();
const store = useCalcStore();
const emit = defineEmits(['submit']);

const onSubmit = () => {
  emit('submit')
}

const inputHandler = (val) => {
  store.changeFormData(val);
}
</script>

<template>
  <form @submit.prevent="onSubmit" :class="$style.CalculatorForm">
      <div :class="$style.CalculatorInputWrap">
        <CalculatorInput v-for="input in inputsConfig" :title="input.title" :name="input.name" :type="input.type" :default-val="input.defaultVal" :required="input.required" @input="inputHandler" />
      </div>
      <CalculatorButton :style="{marginTop: 'auto'}">
        Отправить
      </CalculatorButton>
  </form>
</template>

<style module>
.CalculatorForm {
  background: var(--main-white);
  padding: 16px 24px;
  border-radius: 0 0 10px 10px;
  min-height: 190px;
  display: flex;
  flex-direction: column;
}

.CalculatorInputWrap {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 20px 40px;
  margin: auto;
  min-height: 106px;
}
</style>

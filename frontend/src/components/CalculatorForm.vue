<script setup lang="ts">
import type {ICalculatorForm} from "../types/calculator.types.ts";
import CalculatorInput from "./CalculatorInput.vue";
import CalculatorButton from "./CalculatorButton.vue";
import {useCalcStore} from "../store/calculator";
import CalculatorTime from "./CalculatorTime.vue";

const {inputsConfig} = defineProps<ICalculatorForm>();
const store = useCalcStore();
const emit = defineEmits(['submit']);

const onSubmit = () => {
  emit('submit')
}

const inputHandler = (val: {name: string, value: string}) => {
  store.changeFormData(val);
}
</script>

<template>
  <form @submit.prevent="onSubmit" :class="$style.CalculatorForm">
      <div :class="$style.CalculatorInputWrap">
        <template v-for="input in inputsConfig">
          <CalculatorTime v-if="input.type === 'time'" :title="input.title" :name="input.name" :type="input.type" :default-val="input.defaultVal" :required="input.required" @input="inputHandler" />
          <CalculatorInput v-else :title="input.title" :name="input.name" :type="input.type" :default-val="input.defaultVal" :required="input.required" @input="inputHandler" />
        </template>
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
  margin: 0 0 20px;
  min-height: 106px;
  width: 100%;
}
</style>

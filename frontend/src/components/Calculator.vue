<script setup lang="ts">
import CalculatorTab from "./CalculatorTab.vue";
import CalculatorForm from "./CalculatorForm.vue";
import {useCalcStore} from "../store/calculator";
const calcStore = useCalcStore();
</script>

<template>
  <div :class="$style.Calculator">
    <div :class="$style.CalculatorTabs">
        <CalculatorTab v-for="tab in calcStore.calcTabs" :id="tab.id" :title="tab.title" @click="calcStore.changeTabHandler(tab)" :class="{[$style.active] : calcStore.currentTab.id === tab.id}" />
    </div>
    <CalculatorForm v-show="!calcStore.isShowInputs" :inputs-config="calcStore.secondConfig.inputsConfig" :result="calcStore.result" />
  </div>
</template>

<style module>
.Calculator {
  overflow: hidden;
  position: relative;
  z-index: 3;
  max-width: 550px;
  width: 100%;

  @media(max-width: 570px) {
    min-width: auto;
  }
}

.CalculatorTabs {
  display: flex;
  width: 100%;
  transition: none;
}

.active {
  cursor: default;
  background-color: var(--main-orange-active);
}
</style>

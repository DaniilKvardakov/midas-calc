<script setup lang="ts">
import Calculator from "./components/Calculator.vue";
import Title from "./components/Title.vue";
import {useCalcStore} from "./store/calculator";

const calcStore = useCalcStore();
const submitHandler = () => calcStore.sendCalcForm();
</script>

<template>
 <div :class="$style.Main">
     <div :class="$style.MainCover"></div>
     <Title type="h1" :style="{margin: ' 0 0 0.17em'}">Калькулятор Мидаса</Title>
     <Title type="h2" :style="{margin: ' 0 0 0.97em'}">Крутейший в мире подзаголовок</Title>
    <Calculator @submit="submitHandler"></Calculator>
 </div>
</template>

<style module>
.Main {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: url('./assets/midasbg1.jpg'), var(--main-black);
  background-size: cover;
  background-repeat: no-repeat;
  height: 100%;
  width: 100%;
}

.MainCover {
  background-color: var(--main-black);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: .2;
}
</style>


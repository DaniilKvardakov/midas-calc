<script setup lang="ts">
import Calculator from "./components/Calculator.vue";
import Title from "./components/Title.vue";
import {useCalcStore} from "./store/calculator";
import {reactive,ref} from "vue";

const calcStore = useCalcStore();
const submitHandler = () => calcStore.sendCalcForm();

const currentImg = ref(0);


const images = reactive({
  first: {
    zIndex: 2,
    opacity: 1,
  },
  second: {
    zIndex: 1,
    opacity: 0,
  },
  third: {
    zIndex: 0,
    opacity: 0,
  }
})


setInterval(() => {
  if(currentImg.value === 0) {
    currentImg.value = 1;
    images.first.opacity = 0;
    images.second.opacity = 1;
    images.third.opacity = 0;
  } else if(currentImg.value === 1) {
    currentImg.value = 2;
    images.first.opacity = 0;
    images.second.opacity = 0;
    images.third.opacity = 1;
  } else if(currentImg.value === 2) {
    currentImg.value = 0;
    images.second.opacity = 0;
    images.third.opacity = 0;
    images.first.opacity = 1;
  }
}, 10000)

</script>

<template>
 <div :class="$style.Main">
     <div :class="$style.MainImg" :style="images.first"></div>
     <div :class="$style.SecondImg" :style="images.second"></div>
     <div :class="$style.ThirdImg" :style="images.third"></div>

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
  width: 100%;
  height: 100%;
  background-color: var(--main-black);
}

.MainCover {
  background-color: var(--main-black);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: .2;
  z-index: 3;
}

.MainImg {
  background: url('./assets/midasbg3.png'), var(--main-black);
}

.SecondImg {
  background: url('./assets/midasbg4.png'), var(--main-black);
}

.ThirdImg {
  background: url('./assets/midasbg5.png'), var(--main-black);
}

.MainImg, .SecondImg, .ThirdImg {
  background-size: cover;
  background-repeat: no-repeat;
  height: 100%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  transition: opacity 2s linear;
}
</style>


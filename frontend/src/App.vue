<script setup lang="ts">
import Calculator from "./components/Calculator.vue";
import Title from "./components/Title.vue";
import {useCalcStore} from "./store/calculator";
import {reactive,ref} from "vue";
import './style.css'
import Preloader from "./components/Preloader.vue";
import {useQuotesStore} from "./store/quotes";
import Quote from "./components/Quote.vue";

const audio = ref(0);
const calcStore = useCalcStore();
const quoteStore = useQuotesStore();
const currentImg = ref(0);

quoteStore.getQuoteList();

const submitHandler = async () => {
  await calcStore.sendCalcForm();
  audio.value.play()
};

let activeImgIdx = 0;

const images = ref([
  {
    class: 'FirstImg',
    opacity: 1,
    active: true,
  },
  {
    class: 'SecondImg',
    opacity: 0,
    active: false,
  },
  {
    class: 'ThirdImg',
    opacity: 0,
    active: false,
  },
  {
    class: 'FourthImg',
    opacity: 0,
    active: false,
  },
  {
    class: 'FifthImg',
    opacity: 0,
    active: false,
  },
])
setInterval(() => {
  if(activeImgIdx === images.value.length - 1) {
    images.value[activeImgIdx].opacity = 0;
    images.value[0].opacity = 1;
    activeImgIdx = 0;
    return;
  }
  images.value[activeImgIdx].opacity = 0;
  images.value[activeImgIdx + 1].opacity = 1;
  activeImgIdx += 1;
}, 10000)


</script>

<template>
 <div :class="$style.Main">
     <div :class="[$style.MainImg, $style[img.class]]" v-for="(img, idx) in images" :style="{opacity: img.opacity, zIndex: idx}" />

     <div :class="$style.MainCover"></div>

     <Title type="h1" :style="{margin: ' 0 0 0.17em'}">Калькулятор Мидаса</Title>
     <Title type="h2" :style="{margin: ' 0 0 0.97em'}">Крутейший (и единственный) в мире</Title>

     <Preloader :is-loading="calcStore.isSpinActive">
       <Calculator @submit="submitHandler"></Calculator>
     </Preloader>

<!--     <div :class="$style.QuoteList" v-if="quoteStore.quoteList?.length">-->
<!--       <Quote v-for="quote in quoteStore.quoteList"-->
<!--              :id="quote.id"-->
<!--              :is-active="quoteStore.currentQuote?.id === quote.id"-->
<!--              :name-of-hero="quote.nameOfHero"-->
<!--              :root-to-img="quote.rootToImg"-->
<!--              :phrase="quote.phrase">-->

<!--       </Quote>-->
<!--     </div>-->

     <audio ref="audio" class="" volume="0.025" controls src="/src/assets/midassound.mpeg" :class="$style.CalculatorSound"></audio>
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
  @media(max-width: 570px) {
    margin: 0 15px;
  }
}

.MainCover {
  background-color: var(--main-black);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: .2;
  z-index: 5;
}

.FirstImg {
  background: url('./assets/midasbg3.png'), var(--main-black);
}

.SecondImg {
  background: url('./assets/midasbg4.png'), var(--main-black);
}

.ThirdImg {
  background: url('./assets/midasbg5.png'), var(--main-black);
}

.FourthImg {
  background: url('./assets/midasbg6.png'), var(--main-black);
}

.FifthImg {
  background: url('./assets/midasbg7.png'), var(--main-black);
}

.MainImg {
  background-size: cover;
  background-repeat: no-repeat;
  height: 100%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  transition: opacity 2s linear;
}

.CalculatorSound {
  position: absolute;
  z-index: -1;
  opacity: 0;
  user-select: none;
}

.QuoteList {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 10;
  max-width: 400px;
  width: 100%;
}
</style>


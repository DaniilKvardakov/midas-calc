<script setup lang="ts">
interface IPreloader {
  isLoading: boolean;
}

const props = defineProps<IPreloader>();

</script>

<template>

  <div :class="['loader-wrapper', {['loading']: isLoading}]">
    <div :class="['loader', {['loading']: isLoading}]">
    </div>
    <slot />
  </div>
</template>

<style>

.loader-wrapper {
  position: relative;
  overflow: hidden;
  border-radius: 10px;
  &::after {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;


  }

  &.loading {
    &::after {
      background-color: var(--main-grey);
      opacity: .4;
      z-index: 999;
      background-color: var(--main-grey);
    }
  }

}
.loader {

  position: absolute;
  top: 30px;
  left: calc(50% - 25px);
  overflow:hidden;
  font-size: 10px;
  margin: 50px auto;
  text-indent: -9999em;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: var(--main-orange);
  background: -moz-linear-gradient(left, var(--main-orange) 10%, rgba(101,171,248, 0) 42%);
  background: -webkit-linear-gradient(left, var(--main-orange) 10%, rgba(101,171,248, 0) 42%);
  background: -o-linear-gradient(left, var(--main-orange) 10%, rgba(101,171,248, 0) 42%);
  background: -ms-linear-gradient(left, var(--main-orange) 10%, rgba(101,171,248, 0) 42%);
  background: linear-gradient(to right, var(--main-orange) 10%, rgba(101,171,248, 0) 42%);
  -webkit-animation: load3 1.4s infinite linear;
  animation: load3 1.4s infinite linear;
  -webkit-transform: translateZ(0);
  -ms-transform: translateZ(0);
  transform: translateZ(0) translate(-50%, -50%);

  z-index: -1;

  &.loading {
    z-index: 1000;
  }
}
.loader:before {
  width: 50%;
  height: 50%;
  background: var(--main-orange);
  border-radius: 100% 0 0 0;
  position: absolute;
  top: 0;
  left: 0;
  content: '';
}
.loader:after {
  background: #fbfbfb;
  width: 75%;
  height: 75%;
  border-radius: 50%;
  content: '';
  margin: auto;
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
}
@-webkit-keyframes load3 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}
@keyframes load3 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}
</style>

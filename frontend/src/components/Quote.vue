<script setup lang="ts">
import type {IQuote} from "../types/quotes.types.ts";

const props = defineProps<{
  isActive: boolean
}& IQuote>();

</script>

<template>
  <div :class="[$style.Quote, {[$style.QuoteActive]: isActive}]">
    <div :class="$style.PhraseWrapper">
      <div :class="$style.Phrase">
        {{ phrase }}
      </div>
      <div :class="$style.Name">
        <img :class="$style.HeroIcon" :src="`http://176.212.127.212:8888/${rootToImg}`" :alt="nameOfHero + ' icon'">
        {{ nameOfHero }}
      </div>
    </div>
  </div>
</template>

<style module>
.Quote {
  display: none;
  gap: 10px;
  color: var(--main-white);
  font-size: 18px;
  position: relative;
  z-index: 4;
  max-width: 400px;
  width: 100%;
}

.QuoteActive {
  display: flex;
}

.PhraseWrapper {

}

.Phrase {
  font-family: 'Rubik', sans-serif;
  font-style: italic;
  font-weight: 500;
  letter-spacing: 0.05em;
  margin-bottom: 5px;
  min-height: 42px;
  animation: typewritter 4s steps(40) 1s 1 normal both, textCursor 500ms steps(40) infinite normal;
}

.Name {
  display: flex;
  gap: 8px;
  font-family: 'Rubik', sans-serif;
}

.HeroIcon {
  flex-shrink: 0;
  max-width: 25px;
  max-height: 25px;
}
</style>

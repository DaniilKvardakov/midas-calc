<script setup lang="ts">
import { splitThousands } from "../utils";
import {computed} from "vue";
import {useCalcStore} from "../store/calculator";

interface ICalcResult {
  isShowResult: boolean;
}
const store = useCalcStore();
const props = defineProps<ICalcResult>();
const computedGold = computed(() => splitThousands(store.sendedData?.profitAfterSale))
const isStatusSuccess = computed(() => store.sendedData?.status === 'SUCCESS')
</script>

<template>
  <div :class="[$style.CalculatorResult, {[$style.CalculatorResultExtended]: isShowResult, [$style.CalculatorResultExtendedError]: !isStatusSuccess}]">
    <template v-if="isStatusSuccess">
      <div :class="$style.CalculatorResultItem">
        Вы заработали <span :class="$style.CalculatorResultSpan">{{ computedGold }} голды</span>
      </div>

      <div :class="[$style.CalculatorResultItem, $style.CalculatorResultItemError]">
        Мидас окупился на <span :class="$style.CalculatorResultSpan">{{ store.sendedData?.timeOfPayback }} минуте</span>
      </div>
    </template>

    <template v-else>
      <div :class="$style.CalculatorResultError">
          {{ store.sendedData?.message }}
      </div>
    </template>
  </div>
</template>

<style module>
.CalculatorResult {
  font-size: 18px;
  opacity: 0;
  transition: height .3s linear;

  display: flex;
  flex-direction: column;
}

.CalculatorResultExtended {
  opacity: 1;
}


.CalculatorResultItem {
  margin-bottom: 10px;
}

.CalculatorResultSpan {
  color: #ecca12;
}

.CalculatorResultError {
  color: red;
  margin-bottom: 10px;
}

</style>

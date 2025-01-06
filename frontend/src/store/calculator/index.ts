import {defineStore} from "pinia";
import {computed, ref} from "vue";
import type {ICalculatorForm, ICalculatorTabProps, ICalculatorTabs} from "../../types/calculator.types.ts";

export const useCalcStore = defineStore('calculator', () => {
    const result = ref(0);
    const formData = ref({});
    const calcTabs: ICalculatorTabs = [
        {
            id: 0,
            title: "Id матча",
        },
        {
            id: 1,
            title: "Ввод вручную",
        },
    ];

    const currentTab: Ref<ICalculatorTabProps> = ref(calcTabs[0]);

    const isShowInputs = computed(() => {
        return currentTab.value.title === "Ввод вручную";
    })

    const changeFormData = ({ name, value }) => {
        formData.value[name] = value;
    }

    const sendCalcForm = async () => {
            const baseUrl = 'http://176.212.127.212:8888';
            const requestUrl = isShowInputs.value ? "/profit" : `/profit/${formData.value.matchId}/${formData.value.nickname}`;

            const config = {
                header: {
                    'Access-Control-Allow-Origin': '*'
                },
                method: isShowInputs.value ? "POST" : "GET",
            }

            if(isShowInputs.value) {
                config.body = convertDataToRequest()
            }

            try {
                const response = await fetch(baseUrl + requestUrl, config);
                const responseJSON = await response.json();
                console.log(responseJSON, 'responseJSON');
                console.log('Возвращаем новые данные');
            } catch(e) {
                console.error(e);
            } finally {
                console.log('Последние штрихи, убираем будущий прелоадер')
            }
    }

    const convertDataToRequest = () => {
        const convertedFormData = new FormData();

        convertedFormData.append('totalTimeOfMatch', formData.value.totalTimeOfMatch)
        convertedFormData.append('midasTime', formData.value.midasTime)
        convertedFormData.append('timeOfSellMidas', formData.value.timeOfSellMidas)
        convertedFormData.append('wastedTime', 'PT10M')

        return convertedFormData;
    }


    const firstConfig: ICalculatorForm = {
        inputsConfig: [
            {
                title: 'Время окончания матча',
                name: 'totalTimeOfMatch',
                type: 'time',
                defaultVal: '',
                required: true,
            },
            {
                title: 'Время покупки Мидаса',
                name: 'midasTime',
                type: 'time',
                defaultVal: '',
                required: true,
            },
            {
                title: 'Время продажи Мидаса',
                name: 'timeOfSellMidas',
                type: 'time',
                defaultVal: '',
                required: true,
            },
        ]
    }

    const secondConfig: ICalculatorForm = {
        inputsConfig: [
            {
                title: 'ID матча',
                name: 'matchId',
                type: 'input',
                defaultVal: '',
                required: true,
            },
            {
                title: 'Никнейм',
                name: 'nickname',
                type: 'input',
                defaultVal: '',
                required: true,
            }
        ]
    }

    const changeTabHandler = (tab: ICalculatorTabProps) => {
        currentTab.value = tab;
    }

    return { result, formData, changeFormData, currentTab, firstConfig, secondConfig, changeTabHandler, isShowInputs, sendCalcForm, calcTabs}
})

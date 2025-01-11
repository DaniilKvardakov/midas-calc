//@ts-nocheck
import {defineStore} from "pinia";
import {computed, ref} from "vue";
import type {Ref} from "vue";
import type {ICalculatorForm, ICalculatorTabProps, ICalculatorTabs} from "../../types/calculator.types.ts";


export const useCalcStore = defineStore('calculator', () => {
    const result = ref(0);
    const formData = ref({});
    const isButtonDisabled = ref(false);
    const isSpinActive = ref(false);
    const isShowResult = ref(false);
    const sendedData = ref({})

    const calcTabs: ICalculatorTabs = [
        {
            id: 0,
            title: "Введите данные",
        },
    ];

    const currentTab: Ref<ICalculatorTabProps> = ref(calcTabs[0]);

    const isShowInputs = computed(() => {
        return currentTab.value.title === "Ввод вручную";
    })

    const changeFormData = ({ name, value } : { name: string, value: string }) => {
        formData.value[name] = value;
    }

    const sendCalcForm = async () => {
        const baseUrl = 'http://176.212.127.212:8888';
            const requestUrl = isShowInputs.value ? "/profit" : `/profit/${formData.value.matchId}/${formData.value.nickname}/${formData.value.friendCode || ''}`;
            //@ts-ignore
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
                isButtonDisabled.value = true;
                isSpinActive.value = true;
                isShowResult.value = false;
                const response = await fetch(baseUrl + requestUrl, config);
                const responseJSON = await response.json();

                sendedData.value = {
                    ...responseJSON
                }
            } catch(e) {
                console.error(e);
                sendedData.value = {
                    message: 'Возникла непредвиденная ошибка',
                    status: 'ERROR',
                }
            } finally {
                isShowResult.value = true;
                isButtonDisabled.value = false;
                isSpinActive.value = false;
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
        result: 0,
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
        result: 0,
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
            },
            {
                title: 'Код дружбы(не обязательно)',
                name: 'friendCode',
                type: 'input',
                defaultVal: '',
                required: false,
            }
        ]
    }

    const changeTabHandler = (tab: ICalculatorTabProps) => {
        currentTab.value = tab;
        sendedData.value = {};
        isShowResult.value = false;
    }

    return { result, formData, changeFormData, currentTab, firstConfig, secondConfig, changeTabHandler, isShowInputs, sendCalcForm, calcTabs, isButtonDisabled, isSpinActive, isShowResult, sendedData}
})

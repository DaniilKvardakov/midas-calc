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
            const requestUrl = isShowInputs.value ? "/byData" : `/byId/${formData.value.matchId}`;

            const config = {
                method: isShowInputs.value ? "POST" : "GET",
            }

            if(isShowInputs.value) {
                config.body = convertDataToRequest()
            }

            try {
                const response = await fetch(requestUrl, config);
                console.log('Возвращаем новые данные')
            } catch(e) {
                console.error(e);
            } finally {
                console.log('Последние штрихи, убираем будущий прелоадер')
            }
    }

    const convertDataToRequest = () => {
        const convertedFormData = new FormData();

        for(let item in formData.value) {
            convertedFormData.append(item, formData.value[item]);
        }

        return convertedFormData;
    }


    const firstConfig: ICalculatorForm = {
        inputsConfig: [
            {
                title: 'ID матча',
                name: 'test',
                type: 'input',
                defaultVal: '',
                required: true,
            },
            {
                title: 'ID матча',
                name: 'test2',
                type: 'input',
                defaultVal: '',
                required: true,
            },
            {
                title: 'ID матча',
                name: 'test3',
                type: 'input',
                defaultVal: '',
                required: true,
            },
            {
                title: 'ID матча',
                name: 'test4',
                type: 'input',
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
            }
        ]
    }


    const changeTabHandler = (tab: ICalculatorTabProps) => {
        currentTab.value = tab;
    }





    return { result, formData, changeFormData, currentTab, firstConfig, secondConfig, changeTabHandler, isShowInputs, sendCalcForm, calcTabs}
})

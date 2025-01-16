//@ts-nocheck
import {defineStore} from "pinia";
import {computed, ref} from "vue";
import type {Ref} from "vue";
import type {ICalculatorForm, ICalculatorTabProps, ICalculatorTabs} from "../../types/calculator.types.ts";
import type {IQuote, IQuoteList} from "../../types/quotes.types.ts";


export const useQuotesStore = defineStore('quotes', () => {
    const quoteList: Ref<IQuoteList> = ref([]);
    const currentQuote: Ref<IQuote> = ref(null);


    const getQuoteList = async () => {
        //@ts-ignore
        const config = {
            header: {
                'Access-Control-Allow-Origin': '*'
            },
            method: "GET",
        }
        try {
            const response = await fetch('http://176.212.127.212:8888/phrase', config);
            const responseJSON = await response.json();
            const preparedArrayOfPhrases = responseJSON?.map(({nameOfHero, rootToImg, phrases}, id) => {
                return {
                    id,
                    nameOfHero,
                    rootToImg,
                    phrase: phrases[0]
                }
            })

            if(responseJSON.length) {
                quoteList.value = preparedArrayOfPhrases;
                setCurrentQuote(quoteList.value[0])
                setCurrentQuoteChangingInterval();
            }
        } catch(e) {
            console.error(e);
        }
    }


    const setCurrentQuoteChangingInterval = () => {
        setInterval(() => {
            const nextQuote = quoteList.value[currentQuote.value.id + 1];

            if(nextQuote) {
                setCurrentQuote(nextQuote);
            } else {
                setCurrentQuote(quoteList.value[0]);
            }
        }, 5000)
    }

    const setCurrentQuote = (quote: IQuote) => {
        currentQuote.value = quote;
    }

    return { quoteList, getQuoteList, currentQuote }
})

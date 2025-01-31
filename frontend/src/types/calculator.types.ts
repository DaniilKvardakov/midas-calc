export interface ICalculatorTabProps {
    id: number;
    title: string;
}

export type ICalculatorTabs = ICalculatorTabProps[];

export interface ICalculatorForm {
    inputsConfig: IInput[],
    result: number | string;
}

export interface IInput {
    title: string;
    name: string;
    type: 'input' | "checkbox" | "time";
    defaultVal?: string;
    required: boolean;
}

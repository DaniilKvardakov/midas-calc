export interface ICalculatorTabProps {
    id: number;
    title: string;
}

export type ICalculatorTabs = CalculatorTabProps[];

export interface ICalculatorForm {
    inputsConfig: IInput[],
    result: number | string;
}

export interface IInput {
    title: string;
    name: string;
    type: 'input' | "checkbox";
    defaultVal?: string;
    required: boolean;
}

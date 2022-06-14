import { BankAccount } from "./bankAccount";

export class BankTransaction {
    id: string;
    amount: number;
    date: Date;
    commission: number;
    transactionLabelId: string;
    userId: string;
    bankAccount: BankAccount;
}
import { TransactionLabel } from "./transactionLabel";
import { User } from "./user";

export class Transfer {
    id: string;
    creditor: User;
    amount: number;
    date: Date;
    commission: number;
    transactionLabelId: string;
    userId: string;
    transactionLabel: TransactionLabel;
}
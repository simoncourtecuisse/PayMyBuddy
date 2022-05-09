import { TransactionLabel } from "./transactionLabel";
import { User } from "./user";

export class Transfer {
    id: string;
    user: User;
    amount: number;
    date: Date;
    commission: number;
    transactionLabelId: string;
    userId: string;
    transactionLabel: TransactionLabel;
}
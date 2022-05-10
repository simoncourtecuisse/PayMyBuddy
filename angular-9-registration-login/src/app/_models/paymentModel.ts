import { TransactionLabel } from "./transactionLabel";
import { User } from "./user";

export class PaymentModel {
    creditorId: number;
    amount: number;
    transactionLabelId: number;
    user: User;
    transactionLabel: TransactionLabel;
}
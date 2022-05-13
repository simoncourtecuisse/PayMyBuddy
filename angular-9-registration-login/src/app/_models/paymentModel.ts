import { TransactionLabel } from "./transactionLabel";
import { User } from "./user";

export class PaymentModel {
    creditorId: number;
    amount: number;
    user: User;
    description: string;
}
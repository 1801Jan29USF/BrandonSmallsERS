export class Reimbursement {
    id: number;
    amount: number;
    submitted: Date;
    resolved: Date;
    description: String;
    author: number;
    resolver: number;
    status: number;
    type = 0;
}

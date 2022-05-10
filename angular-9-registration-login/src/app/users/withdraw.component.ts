import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AccountService, AlertService } from '@app/_services';
import { NumberSymbol } from '@angular/common';
import { BankAccount } from '@app/_models/bankAccount';


@Component({ templateUrl: 'withdraw.component.html' })
export class WithdrawComponent implements OnInit {
    formBankAccount: FormGroup
    id: string;
    isCreditMode: boolean;
    loading = false;
    submitted = false;
    bankAccounts = [];
    BankAccountList: any;
    ChangeBankAccount(e) {
        console.log(e.target.value)
    }

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private alertService: AlertService
    ) {}

    ngOnInit() {
        this.accountService.getAllBankAccountByUserId().subscribe((data:any)=>{
            this.BankAccountList=data;
        })

         this.id = this.route.snapshot.params['id'];

        this.formBankAccount = this.formBuilder.group({
            bankAccountId: [this.BankAccountList, Validators.required],
            //bankAccountId: ['', Validators.required],
            amount: ['', Validators.required],       
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.formBankAccount.controls; }

    onSubmit() {
        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.formBankAccount.invalid) {
            return;
        }

        this.loading = true;
        this.withdrawAccount();
    }

    private withdrawAccount() {
        this.accountService.withdrawAccount(this.formBankAccount.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('Successfull withdraw', { keepAfterRouteChange: true });
                    this.router.navigate(['.', { relativeTo: this.route }]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AccountService, AlertService } from '@app/_services';
import { NumberSymbol } from '@angular/common';
import { BankAccount } from '@app/_models/bankAccount';


@Component({ templateUrl: 'credit.component.html' })
export class CreditComponent implements OnInit {
    formBank: FormGroup;
    //formBankAccount: FormGroup
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
        // //this.id = this.route.snapshot.url.join('withdraw');
        this.isCreditMode = !this.id;
        
        // password not required in edit mode
        // const passwordValidators = [Validators.minLength(6)];
        // if (this.isCreditMode) {
        //     passwordValidators.push(Validators.required);
        // }

        this.formBank = this.formBuilder.group({
            bankAccountId: [this.BankAccountList, Validators.required],
            //bankAccountId: ['', Validators.required],
            amount: ['', Validators.required],       
        });


    }

    // convenience getter for easy access to form fields
    get f() { return this.formBank.controls; }

    onSubmit() {
        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.formBank.invalid) {
            return;
        }

        this.loading = true;
        if (this.isCreditMode) {
            this.creditAccount();
        } else {
            this.updateUser();
        }
    }

    private addBankAccount() {
        this.accountService.addBankAccount(this.formBank.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('Bank Account added successfully', { keepAfterRouteChange: true });
                    this.router.navigate(['.', { relativeTo: this.route }]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }

    private creditAccount() {
        this.accountService.creditAccount(this.formBank.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('Wallet credited successfully', { keepAfterRouteChange: true });
                    this.router.navigate(['.', { relativeTo: this.route }]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }

    // private createUser() {
    //     this.accountService.register(this.formBank.value)
    //         .pipe(first())
    //         .subscribe(
    //             data => {
    //                 this.alertService.success('User added successfully', { keepAfterRouteChange: true });
    //                 this.router.navigate(['.', { relativeTo: this.route }]);
    //             },
    //             error => {
    //                 this.alertService.error(error);
    //                 this.loading = false;
    //             });
    // }

    private updateUser() {
        this.accountService.update(this.id, this.formBank.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('Update successful', { keepAfterRouteChange: true });
                    this.router.navigate(['..', { relativeTo: this.route }]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
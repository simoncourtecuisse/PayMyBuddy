import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService, AlertService } from '@app/_services';

@Component({ templateUrl: 'add-transfers.component.html' })
export class AddTransfersComponent implements OnInit {
    form: FormGroup;
    id: string;
    isAddMode: boolean;
    loading = false;
    submitted = false;
    TransferList: any;
    contacts = [];
    ContactList: any;
    ChangeCreditor(e) {
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
        
        this.accountService.getAllFriendsByUserId().subscribe((data:any)=>{
            this.ContactList=data;
        })

        this.id = this.route.snapshot.params['id'];

        this.form = this.formBuilder.group({
            // creditorId: [this.TransferList, Validators.required],
            creditorId: [this.ContactList, Validators.required],
            amount: ['', Validators.required],
            description: ['', Validators.required],
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.form.controls; }

    onSubmit() {
        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.form.invalid) {
            return;
        }

        this.loading = true;
        this.payment();
        
    }

    private payment() {
        this.accountService.payment(this.form.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('Transaction created successfully', { keepAfterRouteChange: true });
                    this.router.navigate(['.', { relativeTo: this.route }]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }

}
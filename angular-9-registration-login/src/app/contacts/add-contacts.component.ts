import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService, AlertService } from '@app/_services';

@Component({ templateUrl: 'add-contacts.component.html' })
export class AddContactsComponent implements OnInit {
    form: FormGroup;
    id: string;
    isAdded: boolean;
    loading = false;
    submitted = false;
    users = null;
    contacts = null;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private alertService: AlertService
    ) {}

//     ngOnInit() {
//         //const user = this.users.find(x => x.userId === user.userId);
//         this.accountService.getAllUsersForContactSearch()
        
//             .pipe(first())
//             .subscribe(users => this.users = users);
//             //.subscribe(contacts => this.contacts = contacts);
//     }

//     deleteFriend(friendUserId: string) {
//         console.log(friendUserId);
//         const contact = this.contacts.find(x => x.userId === friendUserId);
//         contact.isDeleting = true;
//         console.log(this.contacts);
//         console.log(contact.userId);
//         this.accountService.deleteFriend(friendUserId)
//             .pipe(first())
//             .subscribe(() => {
//                 this.contacts = this.contacts.filter(x => x.userId !== friendUserId) 
//             });
        
//     }

//      addFriend(friendUserId: string) {
//         const user = this.users.find(x => x.userId === friendUserId);
//         user.isAdded = true;
//         this.accountService.addFriend(this.form.value)
//             .pipe(first())
//             .subscribe(
//                 data => {
//                     this.alertService.success('User added successfully', { keepAfterRouteChange: true });
//                     this.router.navigate(['.', { relativeTo: this.route }]);
//                 },
//                 error => {
//                     this.alertService.error(error);
//                     this.loading = false;
//                 });
//     }

// }


    ngOnInit() {
        this.id = this.route.snapshot.params['id'];
        this.isAdded = !this.id;
        
        // password not required in edit mode
        const passwordValidators = [Validators.minLength(6)];
        if (this.isAdded) {
            passwordValidators.push(Validators.required);
        }

        this.form = this.formBuilder.group({
            // firstName: ['', Validators.required],
            // lastName: ['', Validators.required],
            email: ['', Validators.required]
        });

        // if (!this.isAdded) {
        //     this.accountService.getById(this.id)
        //         .pipe(first())
        //         .subscribe(x => {
        //             this.f.firstName.setValue(x.firstName);
        //             this.f.lastName.setValue(x.lastName);
                    
        //         });
        // }
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
        if (this.isAdded) {
            this.addFriend();
        } else {
            this.addFriend();
        }
    }

    private addFriend() {
        this.accountService.addFriend(this.id, this.form.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('User added successfully', { keepAfterRouteChange: true });
                    this.router.navigate(['.', { relativeTo: this.route }]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
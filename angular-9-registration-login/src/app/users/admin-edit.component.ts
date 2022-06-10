// import { Component, OnInit } from '@angular/core';
// import { Router, ActivatedRoute } from '@angular/router';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { first } from 'rxjs/operators';

// import { AccountService, AlertService } from '@app/_services';

// @Component({ templateUrl: 'admin-edit.component.html' })
// export class AdminEditComponent implements OnInit {
//     formEdit: FormGroup;
//     userId: string;
//     id: string;
//     isEditMode: boolean;
//     loading = false;
//     submitted = false;

//     constructor(
//         private formBuilder: FormBuilder,
//         private route: ActivatedRoute,
//         private router: Router,
//         private accountService: AccountService,
//         private alertService: AlertService
//     ) {}

//     ngOnInit() {
//         this.id = this.route.snapshot.params['userId'];
//         //this.id = 

//         // this.isEditMode = !this.userId;

//         // if (this.isEditMode) {
//         //     this.accountService.getById(this.id)
//         //         .pipe(first())
//         //         .subscribe(x => {
//         //             this.f.firstName.setValue(x.firstName);
//         //             this.f.lastName.setValue(x.lastName);
//         //             this.f.email.setValue(x.email);
//         //         });
//         // }
//         this.formEdit = this.formBuilder.group({
//             firstName: ['', Validators.required],
//             lastName: ['', Validators.required],
//             email: ['', Validators.required]
//         });


//         console.log(this.id);
        
        
//         this.accountService.getById(this.id)
//              .pipe(first())
//                 .subscribe(x => {
//                     this.f.firstName.setValue(x.firstName);
//                     this.f.lastName.setValue(x.lastName);
//                     this.f.email.setValue(x.email);
//                     console.log(x.firstName);
//                 });
                
//     }

//     // convenience getter for easy access to form fields
//     get f() { return this.formEdit.controls; }

//     onSubmit() {
//         this.submitted = true;

//         // reset alerts on submit
//         this.alertService.clear();

//         // stop here if form is invalid
//         if (this.formEdit.invalid) {
//             return;
//         }

//         this.loading = true;
//         this.updateUser;
//         // this.loading = true;
//         // if (this.isEditMode) {
//         //     this.updateUser();
//         // } else {
//         //     this.createUser();
//         // }
//     }

//     private updateUser() {  
//         this.accountService.updateUser(this.id, this.formEdit.value)
//             .pipe(first())
//             .subscribe(
//                 data => {
//                     this.alertService.success('Update successful', { keepAfterRouteChange: true });
//                     this.router.navigate(['.', { relativeTo: this.route }]);
//                 },
//                 error => {
//                     this.alertService.error(error);
//                     this.loading = false;
//                 });
//     }
//     private createUser() {
//         this.accountService.register(this.formEdit.value)
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
// import { Component, OnInit } from '@angular/core';
// import { Router, ActivatedRoute } from '@angular/router';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { first } from 'rxjs/operators';

// import { AccountService, AlertService } from '@app/_services';
// import { AuthService } from '@app/_services/auth.service';
// import { TokenStorageService } from '@app/_services/token-storage.service';

// @Component({ templateUrl: 'login.component.html' })
// export class LoginComponent implements OnInit {
//     form: FormGroup;
//     loading = false;
//     submitted = false;
//     returnUrl: string;
//     isLoggedIn = false;
//     isLoginFailed = false;
//     errorMessage = '';
//     roles: string[] = [];

//     constructor(
//         private formBuilder: FormBuilder,
//         private route: ActivatedRoute,
//         private router: Router,
//         private accountService: AccountService,
//         private alertService: AlertService,
//         private authService: AuthService,
//         private tokenStorage: TokenStorageService
//     ) { }

//     ngOnInit() {
//         if (this.tokenStorage.getToken()) {
//             this.isLoggedIn = true;
//             this.roles = this.tokenStorage.getUser().roles;
//         }
//         this.form = this.formBuilder.group({
//             email: ['', Validators.required],
//             password: ['', Validators.required]
//         });

//         // get return url from route parameters or default to '/'
//         this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
//     }

//     // convenience getter for easy access to form fields
//     get f() { return this.form.controls; }

//     onSubmit() {
//         this.submitted = true;

//         // reset alerts on submit
//         this.alertService.clear();

//         // stop here if form is invalid
//         if (this.form.invalid) {
//             return;
//         }

//         this.loading = true;
//         //this.authService.login(this.f.email.value, this.f.password.value)
//         // this.authService.login(this.form)
//         //     .pipe(first())
//         //     .subscribe(
//         //         data => {
//         //             this.router.navigate([this.returnUrl]);
//         //         },
//         //         error => {
//         //             this.alertService.error(error);
//         //             this.loading = false;
//         //         });

//         this.authService.login(this.form).subscribe(
//             data => {
//                 this.tokenStorage.saveToken(data.accessToken);
//                 this.tokenStorage.saveUser(data);
//                 this.isLoginFailed = false;
//                 this.isLoggedIn = true;
//                 this.roles = this.tokenStorage.getUser().roles;
//                 this.reloadPage();
//               },
//               err => {
//                 this.errorMessage = err.error.message;
//                 this.isLoginFailed = true;
//               }
//             );
//     }
//     reloadPage() {
//         window.location.reload();
//       }
// }

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
@Component({
//   selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  returnUrl: string;
  constructor(
      private authService: AuthService, 
      private tokenStorage: TokenStorageService,
      private route: ActivatedRoute,
      private router: Router) { }
  
  ngOnInit() {
    // if (this.tokenStorage.getToken()) {
    //   this.isLoggedIn = true;
    //   this.roles = this.tokenStorage.getUser().roles;
    
    // };

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }
  onSubmit() {
    this.authService.login(this.form).subscribe(
      data => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        // this.reloadPage();
        this.router.navigate([this.returnUrl]);
        // this.router.navigate(['/']);
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }
  reloadPage() {
    window.location.reload();
  }
}
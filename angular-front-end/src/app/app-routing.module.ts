import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home';
import { AuthGuard } from './_helpers';

const accountModule = () => import('./account/account.module').then(x => x.AccountModule);
const profileModule = () => import('./users/profile.module').then(x => x.ProfileModule);
const contactsModule = () => import('./contacts/contacts.module').then(x => x.ContactsModule);
const transfersModule = () => import('./transfers/transfers.module').then(x => x.TransfersModule);

const routes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'profile', loadChildren: profileModule, canActivate: [AuthGuard] },
    { path: 'contacts', loadChildren: contactsModule, canActivate: [AuthGuard] },
    { path: 'transfers', loadChildren: transfersModule, canActivate: [AuthGuard] },
    { path: 'account', loadChildren: accountModule },
   

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
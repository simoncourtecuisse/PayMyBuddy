import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home';

// import { AuthGuard } from './_helpers';

const homeModule = () => import('./home/home.module').then(x => x.HomeModule);
const accountModule = () => import('./account/account.module').then(x => x.AccountModule);
const profileModule = () => import('./users/profile.module').then(x => x.ProfileModule);
const contactsModule = () => import('./contacts/contacts.module').then(x => x.ContactsModule);
const transfersModule = () => import('./transfers/transfers.module').then(x => x.TransfersModule);

const routes: Routes = [
    { path: '', component: HomeComponent },
    // { path: 'home', loadChildren: homeModule },
    { path: 'profile', loadChildren: profileModule },
    { path: 'contacts', loadChildren: contactsModule },
    { path: 'transfers', loadChildren: transfersModule },
    { path: 'account', loadChildren: accountModule },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
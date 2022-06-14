import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutComponent } from './layout.component';
import { ListComponent } from './list.component';
import { AddEditComponent } from './add-edit.component';
import { CreditComponent } from './credit.component';
import { BankAccountComponent } from './bankAccount.component';
import { WithdrawComponent } from './withdraw.component';
import { BoardAdminComponent } from './board-admin.component';


const routes: Routes = [
    {
        path: '', component: LayoutComponent,
        children: [
            { path: '', component: ListComponent },
            { path: 'credit', component: CreditComponent },
            { path: 'withdraw', component: WithdrawComponent },
            { path: 'bankAccounts', component: BankAccountComponent },
            { path: 'bankAccounts/add', component: AddEditComponent },
            { path: 'admin', component: BoardAdminComponent }
            // { path: 'admin/edit/:id', component: AdminEditComponent }
        ]
    },
    
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProfileRoutingModule { }
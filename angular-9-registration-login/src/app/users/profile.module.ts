import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { LayoutComponent } from './layout.component';
import { ListComponent } from './list.component';
import { AddEditComponent } from './add-edit.component';
import { CreditComponent } from './credit.component';
import { BankAccountComponent } from './bankAccount.component';
import { WithdrawComponent } from './withdraw.component';



@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        ProfileRoutingModule
    ],
    declarations: [
        LayoutComponent,
        ListComponent,
        AddEditComponent,
        CreditComponent,
        WithdrawComponent,
        BankAccountComponent
    ]
})
export class ProfileModule { }
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { TransfersRoutingModule } from './transfers-routing.module';
import { LayoutTransfersComponent } from './layout-transfers.component';
import { ListTransfersComponent } from './list-transfers.component';
import { AddEditTransfersComponent } from './add-edit-transfers.component';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        TransfersRoutingModule
    ],
    declarations: [
        LayoutTransfersComponent,
        ListTransfersComponent,
        AddEditTransfersComponent
    ]
})
export class TransfersModule { }
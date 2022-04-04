import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutTransfersComponent } from './layout-transfers.component';
import { ListTransfersComponent } from './list-transfers.component';
import { AddEditTransfersComponent } from './add-edit-transfers.component';

const routes: Routes = [
    {
        path: '', component: LayoutTransfersComponent,
        children: [
            { path: '', component: ListTransfersComponent },
            { path: 'add', component: AddEditTransfersComponent },
            { path: 'edit/:id', component: AddEditTransfersComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TransfersRoutingModule { }
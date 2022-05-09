import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutTransfersComponent } from './layout-transfers.component';
import { ListTransfersComponent } from './list-transfers.component';
import { AddTransfersComponent } from './add-transfers.component';

const routes: Routes = [
    {
        path: '', component: LayoutTransfersComponent,
        children: [
            { path: '', component: ListTransfersComponent },
            { path: 'payment', component: AddTransfersComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TransfersRoutingModule { }